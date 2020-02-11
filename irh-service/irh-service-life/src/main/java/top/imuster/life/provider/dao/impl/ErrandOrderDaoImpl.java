package top.imuster.life.provider.dao.impl;


import org.springframework.stereotype.Repository;
import top.imuster.common.base.dao.BaseDaoImpl;
import top.imuster.life.api.pojo.ErrandOrder;
import top.imuster.life.provider.dao.ErrandOrderDao;

/**
 * ErrandOrderDao 实现类
 * @author 黄明人
 * @since 2020-02-11 17:49:36
 */
@Repository("errandOrderDao")
public class ErrandOrderDaoImpl extends BaseDaoImpl<ErrandOrder, Long> implements ErrandOrderDao {
	private final static String NAMESPACE = "top.imuster.life.provider.dao.ErrandOrderDao.";
	
	//返回本DAO命名空间,并添加statement
	public String getNameSpace(String statement) {
		return NAMESPACE + statement;
	}
}