
package top.imuster.goods.dao.impl;


import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.goods.api.pojo.ProductMessage;
import top.imuster.goods.dao.ProductMessageDao;

/**
 * ProductMessageDao 实现类
 * @author 黄明人
 * @since 2019-11-24 16:31:58
 */
@Repository("productMessageDao")
public class ProductMessageDaoImpl extends BaseDaoImpl<ProductMessage, Long> implements ProductMessageDao {
	private final static String NAMESPACE = "top.imuster.goods.dao.ProductMessageDao.";
	private final static String INSERT_RETURN_ID = "insertReturnId";
	private final static String SELECT_PRODUCT_EMAIL_BY_MESSAGE_PARENT_ID = "selectProductEmailByMessageParentId";
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}

	@Override
	public Long insertReturnId(ProductMessage productMessage) {
		return Long.parseLong(String.valueOf(this.insert(getNameSpace(INSERT_RETURN_ID), productMessage)));
	}

	@Override
	public String selectProductEmailByMessageParentId(Long parentId) {
		return this.select(getNameSpace(SELECT_PRODUCT_EMAIL_BY_MESSAGE_PARENT_ID), parentId);
	}
}