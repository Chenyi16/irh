package top.imuster.goods.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.common.core.dto.SendDetailPageDto;
import top.imuster.common.core.enums.TemplateEnum;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.dao.ProductInfoDao;
import top.imuster.goods.service.ProductInfoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
        Long id = productInfoDao.insertInfoReturnId(productInfo);
        SendDetailPageDto sendMessage = new SendDetailPageDto();
        productInfo.setId(id);
        sendMessage.setObject(productInfo);
        sendMessage.setTemplateEnum(TemplateEnum.PRODUCT_TEMPLATE);
        generateSendMessageService.sendToMq(sendMessage);
        return Message.createBySuccess();
    }

    @Override
    public Message<Page<ProductInfo>> list(Long userId, Integer pageSize, Integer currentPage) {
        Page<ProductInfo> infoPage = new Page<>();
        ProductInfo productInfo = new ProductInfo();
        productInfo.setConsumerId(userId);
        productInfo.setState(2);
        infoPage = this.selectPage(productInfo, infoPage);
        return Message.createBySuccess(infoPage);
    }

    @Override
    public void transBrowserTimesFromRedis2DB(List<BrowserTimesDto> browserTimesDtos) {
        if(browserTimesDtos == null || browserTimesDtos.isEmpty()) return;
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
            Map<Long, Long> result = productInfoDao.selectBrowserTimesByIds(ids);
            for (int z = 0; z < selectIds.length; z++){
                if(selectIds[z] == null || selectIds[z] == 0) break;
                Long selectId = selectIds[z];
                Long total = totals[i * batchSize + z];
                Long original = result.get(selectId);
                original = original + total;
                ProductInfo condition = new ProductInfo();
                condition.setId(selectId);
                condition.setBrowserTimes(original);
                update.add(condition);
            }
            productInfoDao.updateBrowserTimesByCondition(update);
        }

    }
}