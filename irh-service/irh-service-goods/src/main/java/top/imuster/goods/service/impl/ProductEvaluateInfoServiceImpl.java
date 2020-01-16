package top.imuster.goods.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.annotation.MqGenerate;
import top.imuster.common.core.config.RabbitMqConfig;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.common.core.enums.MqTypeEnum;
import top.imuster.goods.api.pojo.ProductEvaluateInfo;
import top.imuster.goods.dao.ProductEvaluateInfoDao;
import top.imuster.goods.service.ProductEvaluateInfoService;
import top.imuster.order.api.pojo.OrderInfo;

import javax.annotation.Resource;
import java.util.List;

/**
 * ProductEvaluateInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("productEvaluateInfoService")
public class ProductEvaluateInfoServiceImpl extends BaseServiceImpl<ProductEvaluateInfo, Long> implements ProductEvaluateInfoService {

    @Resource
    private ProductEvaluateInfoDao productEvaluateInfoDao;

    @Override
    public BaseDao<ProductEvaluateInfo, Long> getDao() {
        return this.productEvaluateInfoDao;
    }

    @Override
    public void evaluateByOrder(OrderInfo order, ProductEvaluateInfo productEvaluateInfo) throws Exception {
        ProductEvaluateInfo evaluateInfo = new ProductEvaluateInfo();
        evaluateInfo.setBuyerId(order.getBuyerId());
        evaluateInfo.setProductId(order.getProductId());
        evaluateInfo.setSalerId(order.getSalerId());
        evaluateInfo.setOrderId(order.getId());
        evaluateInfo.setState(2);
        productEvaluateInfoDao.insertEntry(evaluateInfo);
    }

    @Override
    @MqGenerate
    public void generateSendMessage(SendMessageDto sendMessageDto) {
        sendMessageDto.setBody("我对您发布的商品进行了评价,快来看看吧");
        sendMessageDto.setSourceType(10);
        sendMessageDto.setType(MqTypeEnum.EMAIL);
    }
}