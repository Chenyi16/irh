package top.imuster.goods.service.impl;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.ReleaseAnnotation;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.common.core.dto.rabbitMq.SendExamineDto;
import top.imuster.common.core.enums.OperationType;
import top.imuster.common.core.enums.ReleaseType;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.goods.api.dto.GoodsForwardDto;
import top.imuster.goods.api.dto.UserGoodsCenterDto;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.dao.ProductInfoDao;
import top.imuster.goods.service.ProductInfoService;

import javax.annotation.Resource;
import java.util.*;

/**
 * ProductInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("productInfoService")
public class ProductInfoServiceImpl extends BaseServiceImpl<ProductInfo, Long> implements ProductInfoService {

    private int batchSize = 100;

    @Resource
    private ProductInfoDao productInfoDao;

    @Resource
    private GenerateSendMessageService generateSendMessageService;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public BaseDao<ProductInfo, Long> getDao() {
        return this.productInfoDao;
    }

    @Override
    public Integer updateProductCategoryByCondition(ProductInfo productInfo) {
        return productInfoDao.updateProductCategoryByCondition(productInfo);
    }

    @Override
    public Long getConsumerIdById(Long id) {
        return productInfoDao.selectSalerIdByProductId(id);
    }

    @Override
    public ProductInfo getProductInfoByMessageId(Long id) {
        return productInfoDao.selectProductInfoByMessageId(id);
    }

    @Override
    public ProductInfo getProductBriefInfoById(Long id) {
        ProductInfo productInfo = productInfoDao.selectProductBriefInfoById(id);
        return productInfo;
    }

    @Override
    public Message<String> releaseProduct(ProductInfo productInfo) {
        productInfoDao.insertEntry(productInfo);

        SendExamineDto sendExamineDto = new SendExamineDto();
        sendExamineDto.setTargetId(productInfo.getId());
        sendExamineDto.setTargetType(1);
        sendExamineDto.setUserId(productInfo.getConsumerId());
        sendExamineDto.setContent(new StringBuffer(productInfo.getTitle()).append(productInfo.getProductDesc()).toString());

        ArrayList<String> strings = new ArrayList<>();
        strings.add(productInfo.getMainPicUrl());

        if(productInfo.getOtherImgUrl() != null){
            String otherImgUrl = productInfo.getOtherImgUrl();
            String[] split = otherImgUrl.split("," );
            List<String> imgs = Arrays.asList(split);
            strings.addAll(imgs);
        }
        sendExamineDto.setImgUrl(strings);
        generateSendMessageService.sendToMq(sendExamineDto);
        return Message.createBySuccess();
    }

    @Override
    public Message<Page<ProductInfo>> list(Long userId, Integer pageSize, Integer currentPage) {
        Page<ProductInfo> infoPage = new Page<>();
        infoPage.setPageSize(pageSize);
        infoPage.setCurrentPage(currentPage);
        ProductInfo productInfo = new ProductInfo();
        productInfo.setConsumerId(userId);
        productInfo.setState(2);
        infoPage = this.selectPage(productInfo, infoPage);
        return Message.createBySuccess(infoPage);
    }

    @Override
    public void transBrowserTimesFromRedis2DB(List<BrowserTimesDto> browserTimesDtos) {
        if(CollectionUtils.isEmpty(browserTimesDtos)) return;
        Integer total = productInfoDao.updateBrowseTimesByDtoList(browserTimesDtos);
        log.info("------------>一共更新了{}条浏览总数记录", total);
        /*if(browserTimesDtos == null || browserTimesDtos.isEmpty()) return;
        HashSet<Long> targetIds = new HashSet<>();
        HashSet<Long> times = new HashSet<>();
        browserTimesDtos.stream().forEach(browserTimesDto -> {
            Long targetId = browserTimesDto.getTargetId();
            Long total = browserTimesDto.getTotal();
            targetIds.add(targetId);
            times.add(total);
        });
        Long[] ids = targetIds.toArray(new Long[targetIds.size()]);
        Long[] totals = times.toArray(new Long[times.size()]);
        for (int i = 0; i <= ids.length / batchSize; i++){
            ArrayList<ProductInfo> update = new ArrayList<>();
            Long[] selectIds = new Long[batchSize];
            for (int j = i * batchSize, x = 0; j < (i + 1) * batchSize; j++, x++) {
                selectIds[x] = ids[j];
                if(j == ids.length - 1) break;
            }
//            Map<Long, Long> result = productInfoDao.selectBrowserTimesByIds(ids);
            for (int z = 0; z < selectIds.length - 1; z++){
                if(selectIds[z] == null || selectIds[z] == 0) break;
                Long selectId = selectIds[z];
                Long total = totals[i * batchSize + z];
//                Long original = result.get(selectId);
//                original = original + total;
                ProductInfo condition = new ProductInfo();
                condition.setId(selectId);
                condition.setBrowserTimes(total);
                update.add(condition);
            }
            productInfoDao.updateBrowserTimesByCondition(update);
        }*/

    }

    @Override
    @ReleaseAnnotation(type = ReleaseType.GOODS, value = "#p0", operationType = OperationType.REMOVE)
    public Message<String> deleteById(Long id, Long userId) {
        Long userIdByProductId = productInfoDao.selectUserIdByProductId(id);
        if(userIdByProductId == null) return Message.createByError("删除失败,请刷新后重试");
        if(!userId.equals(userIdByProductId)){
            log.error("id为{}的用户试图删除id为{}的商品，但是该商品不属于他", userId, id);
            return Message.createByError("非法操作,你当前的操作已经被记录");
        }
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(id);
        productInfo.setState(1);
        productInfoDao.updateByKey(productInfo);
        return Message.createBySuccess();
    }

    @Override
    public void updateProductCollectTotal(List<GoodsForwardDto> list) {
        productInfoDao.updateCollectTotal(list);
    }

    @Override
    public Integer lockProduct(Long productId, Integer version) {
        HashMap<String, String> params = new HashMap<>();
        params.put("productId", String.valueOf(productId));
        params.put("version", String.valueOf(version));
        return productInfoDao.lockProductById(params);
    }

    @Override
    public Message<Page<ProductInfo>> getProductBriefInfoByPage(Integer currentPage, Integer pageSize) {
        Page<ProductInfo> page = new Page<>();
        ProductInfo condition = new ProductInfo();
        condition.setState(2);
        condition.setOrderField("create_time");
        condition.setOrderFieldType("DESC");
        condition.setStartIndex((currentPage - 1) * pageSize);
        condition.setEndIndex(pageSize);
        List<ProductInfo> list = productInfoDao.selectProductBriefInfoList(condition);
        Integer count = productInfoDao.selectEntryListCount(condition);
        page.setTotalCount(count);
        page.setData(list);
        return Message.createBySuccess(page);
    }

    @Override
    public boolean updateProductStateById(Long productId, Integer state) {
        ProductInfo condition = new ProductInfo();
        condition.setId(productId);
        condition.setState(state);
        Integer temp = productInfoDao.updateProductStateById(condition);
        return temp != 0;
    }

    @Override
    public List<ProductInfo> getProductBriefByIds(List<Long> ids) {
        return productInfoDao.selectProductBriefInfoByIds(ids);
    }

    @Override
    public Message<UserGoodsCenterDto> getUserCenterInfoById(Long id) {
        UserGoodsCenterDto userGoodsCenterDto = productInfoDao.selectGoodsBrowseTotalByUserId(id);
        String totalMoney = productInfoDao.selectDonationMoneyByUserId(id);
        Integer saleTotal = productInfoDao.selectSaleTotalByUserId(id);
        userGoodsCenterDto.setDonationTotal(totalMoney);
        userGoodsCenterDto.setSaleTotal(saleTotal);
        return Message.createBySuccess(userGoodsCenterDto);
    }
}