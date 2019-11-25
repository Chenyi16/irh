package top.imuster.mall.dao.impl;

import org.springframework.stereotype.Repository;
import top.imuster.domain.base.BaseDaoImpl;
import top.imuster.mall.dao.ProductInfoDao;
import top.imuster.mall.domain.ProductInfo;

/**
 * ProductInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:58
 */
@Repository("productInfoDao")
public class ProductInfoDaoImpl extends BaseDaoImpl<ProductInfo, Long> implements ProductInfoDao {
	private final static String NAMESPACE = "top.imuster.mall.dao.ProductInfoDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}