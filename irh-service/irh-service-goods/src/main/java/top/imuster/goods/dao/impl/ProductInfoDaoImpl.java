package top.imuster.goods.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.goods.api.dto.GoodsForwardDto;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.dao.ProductInfoDao;

import java.util.List;
import java.util.Map;

/**
 * ProductInfoDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:58
 */
@Repository("productInfoDao")
public class ProductInfoDaoImpl extends BaseDaoImpl<ProductInfo, Long> implements ProductInfoDao {
	private final static String NAMESPACE = "top.imuster.goods.dao.ProductInfoDao.";
	private final static String UPDATE_PRODUCT_CATEGORY_BY_CONDITION= "updateProductCategoryByCondition";
	private final static String SELECT_SALER_ID_BY_PRODUCT_ID = "selectSalerIdByProductId";
	private final static String SELECT_PRODUCT_INFO_BY_MESSAGE_ID = "selectProductInfoByMessageId";
	private final static String SELECT_PRODUCT_BRIEF_INFO_BY_ID = "selectProductBriefInfoById";
	private final static String SELECT_BROWSER_TIMES_BY_IDS = "selectBrowserTimesByIds";
	private final static String UPDATE_BROWSER_TIMES_BY_CONDITION =  "updateBrowserTimesByCondition";
	private final static String SELECT_USER_ID_BY_PRODUCT_ID = "selectUserIdByProductId";
	private final static String UPDATE_COLLECT_TOTAL = "updateCollectTotal";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public Integer updateProductCategoryByCondition(ProductInfo productInfo) {
		return this.update(getNameSpace(UPDATE_PRODUCT_CATEGORY_BY_CONDITION), productInfo);
	}

	@Override
	public Long selectSalerIdByProductId(Long id) {
		return this.select(getNameSpace(SELECT_SALER_ID_BY_PRODUCT_ID), id);
	}

	@Override
	public ProductInfo selectProductInfoByMessageId(Long id) {
		return this.select(getNameSpace(SELECT_PRODUCT_INFO_BY_MESSAGE_ID), id);
	}

	@Override
	public ProductInfo selectProductBriefInfoById(Long id) {
		return this.select(getNameSpace(SELECT_PRODUCT_BRIEF_INFO_BY_ID), id);
	}

	@Override
	public Map<Long, Long> selectBrowserTimesByIds(Long[] ids) {
		return this.select(getNameSpace(SELECT_BROWSER_TIMES_BY_IDS), ids);
	}

	@Override
	public void updateBrowserTimesByCondition(List<ProductInfo> update) {
		this.update(getNameSpace(UPDATE_BROWSER_TIMES_BY_CONDITION), update);
	}

	@Override
	public Long selectUserIdByProductId(Long id) {
		return this.select(getNameSpace(SELECT_USER_ID_BY_PRODUCT_ID), id);
	}

	@Override
	public Integer updateCollectTotal(List<GoodsForwardDto> list) {
		return this.update(getNameSpace(UPDATE_COLLECT_TOTAL), list);
	}
}