package top.imuster.goods.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.cache.RedisCachePrefix;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.dao.ErrandInfoDao;
import top.imuster.goods.service.ErrandInfoService;
import top.imuster.life.api.pojo.ErrandInfo;
import top.imuster.user.api.service.UserServiceFeignApi;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ErrandInfoService 实现类
 * @author 黄明人
 * @since 2020-02-11 17:49:35
 */
@Service("errandInfoService")
public class ErrandInfoServiceImpl extends BaseServiceImpl<ErrandInfo, Long> implements ErrandInfoService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserServiceFeignApi userServiceFeignApi;

    @Resource
    private ErrandInfoDao errandInfoDao;

    @Override
    public BaseDao<ErrandInfo, Long> getDao() {
        return this.errandInfoDao;
    }

    @Override
    @Cacheable(value = RedisCachePrefix.IRH_ERRAND_LIST, key = "#p2 + 'errandList::' + #p1")
    public Message<Page<ErrandInfo>> getListByCondition(Integer pageSize, Integer currentPage, Long userId, Integer state) {
        Page<ErrandInfo> page = new Page<>();
        ErrandInfo condition = new ErrandInfo();
        condition.setPublisherId(userId);
        condition.setStartIndex((currentPage < 1 ? 1 : currentPage - 1) * pageSize);
        condition.setEndIndex(pageSize);
        condition.setOrderField("create_time");
        condition.setOrderFieldType("DESC");
        condition.setState(state);
        List<ErrandInfo> errandInfos = errandInfoDao.selectList(condition);
//        Integer count = errandInfoDao.selectListCountByUserId(userId);
        Integer count = errandInfoDao.selectEntryListCount(condition);
        page.setTotalCount(count);
        page.setData(errandInfos);
        return Message.createBySuccess(page);
    }

    @Override
    public boolean isAvailable(Long id, Integer errandVersion) {
        ErrandInfo errandInfo = new ErrandInfo();
        errandInfo.setId(id);
        errandInfo.setVersion(errandVersion);
        Integer state = errandInfoDao.selectStateByIdAndVersion(errandInfo);
        if(state == null) return false;
        return state == 2;
    }

    @Override
    public Message<String> deleteErrandById(Long id, Long userId, Integer version) {
        List<ErrandInfo> errandInfos = errandInfoDao.selectEntryList(id);
        if(errandInfos == null || errandInfos.isEmpty()) return Message.createByError("未找到相关信息,请刷新后重试");
        ErrandInfo errandInfo = errandInfos.get(0);
        Integer originalVersion = errandInfo.getVersion();
        Integer state = errandInfo.getState();
        if(!originalVersion.equals(version)) return Message.createByError("删除失败,信息已经更改,请刷新后重试");
        if(state == 3) return Message.createByError("删除失败,该任务已被领取");
        if(!errandInfo.getPublisherId().equals(userId)){
            log.error("{}的用户恶意删除他人的跑腿信息", userId);
            return Message.createByError("不能删除其他人发布的跑腿,如果多次违规操作,将会冻结账号");
        }
        errandInfo.setState(1);
        errandInfoDao.updateByKey(errandInfo);
        return Message.createBySuccess("删除失败,请刷新后重试");
    }

    @Override
    public boolean updateStateByIdAndVersion(Long id, Integer errandVersion, Integer state) {
        ErrandInfo errandInfo = new ErrandInfo();
        errandInfo.setVersion(errandVersion);
        errandInfo.setId(id);
        errandInfo.setState(state);
        Integer temp = errandInfoDao.updateStateByIdAndVersion(errandInfo);
        return temp != 0;
    }

    @Override
    public Message<String> release(ErrandInfo errandInfo, Long userId) {
        String address = errandInfo.getAddress();
        String phoneNum = errandInfo.getPhoneNum();
        Map<String, String> res = null;
        if(StringUtils.isEmpty(address) || StringUtils.isEmpty(phoneNum)){
            res = userServiceFeignApi.getUserAddressAndPhoneById(userId);
            if(res == null){
                log.error("编号为{}的用户无法根据id获得自己的phone和address信息",userId);
                return Message.createByError("服务器繁忙,请稍后重试");
            }
        }
        if(StringUtils.isEmpty(address)){
            String dbAddress = res.get("address");
            if(StringUtils.isEmpty(dbAddress)) return Message.createByError("您没有默认的地址,请在个人中心中完善,或在提交时填写地址");
            errandInfo.setAddress(dbAddress);
        }
        if(StringUtils.isEmpty(phoneNum)){
            String dbPhoneNum = res.get("phoneNum");
            if(StringUtils.isEmpty(dbPhoneNum)) return Message.createByError("您没有默认的电话号码,请在个人中心中完善,或在提交时填写电话");
            errandInfo.setPhoneNum(dbPhoneNum);
        }
        errandInfo.setPublisherId(userId);
        errandInfoDao.insertEntry(errandInfo);
        return Message.createBySuccess();
    }

    @Override
    public ErrandInfo getAddAndPhoneById(Long errandId) {
        List<ErrandInfo> errandInfos = this.selectEntryList(errandId);
        if(errandInfos == null || errandInfos.isEmpty()) return null;
        return errandInfos.get(0);
    }

    @Override
    public Message<Page<ErrandInfo>> listByType(Integer pageSize, Integer currentPage, Integer type) {
        ErrandInfo condition = new ErrandInfo();

        if(type == 1) condition.setOrderField("create_time");
        else condition.setOrderField("money");

        condition.setOrderFieldType("DESC");
        condition.setState(2);
        condition.setStartIndex((currentPage- 1) * pageSize);
        condition.setEndIndex(pageSize);

        List<ErrandInfo> list = errandInfoDao.selectErrandBrief(condition);
        Integer count = errandInfoDao.selectEntryListCount(condition);
        Page<ErrandInfo> page = new Page<>();
        page.setData(list);
        page.setTotalCount(count);

        return Message.createBySuccess(page);
    }

    @Override
    public Integer getErrandVersionById(Long errandId) {
        List<ErrandInfo> errandInfos = this.selectEntryList(errandId);
        if(errandInfos == null || errandInfos.isEmpty()) return null;
        return errandInfos.get(0).getVersion();
    }

}