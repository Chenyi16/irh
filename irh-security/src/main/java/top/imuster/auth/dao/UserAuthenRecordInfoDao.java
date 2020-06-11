package top.imuster.auth.dao;


import top.imuster.common.base.dao.BaseDao;
import top.imuster.security.api.pojo.UserAuthenRecordInfo;

/**
 * UserAuthenRecordInfoDao 接口
 * @author 黄明人
 * @since 2020-03-27 15:53:30
 */
public interface UserAuthenRecordInfoDao extends BaseDao<UserAuthenRecordInfo, Long> {
    //自定义扩展
    /**
     * @Author hmr
     * @Description 根据用户id更新所有的认证记录
     * @Date: 2020/6/11 20:02
     * @param userAuthenRecordInfo
     * @reture: void
     **/
    Integer updateStateByUserId(UserAuthenRecordInfo userAuthenRecordInfo);

}