package top.imuster.user.provider.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.user.api.pojo.UserInterestTagRel;

import java.util.List;

/**
 * UserInterestTagRelDao 接口
 * @author 黄明人
 * @since 2019-11-24 16:31:57
 */
public interface UserInterestTagRelDao extends BaseDao<UserInterestTagRel, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description 根据id获得有多少人使用了该标签
     * @Date: 2020/2/6 14:36
     * @param id
     * @reture: java.lang.Long
     **/
    Long selectTagCountByTagId(Long id);

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/3/28 16:14
     * @param userId
     * @reture: java.util.List<java.lang.Long>
     **/
    List<Long> selectTagByUserId(Long userId);
}