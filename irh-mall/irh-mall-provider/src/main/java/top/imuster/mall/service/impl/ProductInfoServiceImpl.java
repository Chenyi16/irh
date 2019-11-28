package top.imuster.mall.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.domain.base.BaseDao;
import top.imuster.mall.dao.ProductInfoDao;
import top.imuster.mall.domain.ProductInfo;
import top.imuster.mall.service.ProductInfoService;
import top.imuster.service.base.BaseServiceImpl;

import javax.annotation.Resource;

/**
 * ProductInfoService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("productInfoService")
public class ProductInfoServiceImpl extends BaseServiceImpl<ProductInfo, Long> implements ProductInfoService {

    @Resource
    private ProductInfoDao productInfoDao;

    @Override
    public BaseDao<ProductInfo, Long> getDao() {
        return this.productInfoDao;
    }
}