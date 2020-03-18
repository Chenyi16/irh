package top.imuster.life.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.life.api.pojo.UserForumAttributeInfo;

import java.util.List;

/**
 * UserForumAttributeDao 接口
 * @author 黄明人
 * @since 2020-02-08 15:27:10
 */
public interface UserForumAttributeDao extends BaseDao<UserForumAttributeInfo, Long> {
    //自定义扩展

    /**
     * @Author hmr
     * @Description 根据条件查询点赞记录
     * @Date: 2020/2/11 15:51
     * @param condition
     * @reture: java.util.List<UserForumAttributeInfo>
     **/
    List<UserForumAttributeInfo> selectUpListByCondition(UserForumAttributeInfo condition);
}