package top.imuster.user.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.PropagateInfo;

/**
 * PropagateInfoService接口
 * @author 黄明人
 * @since 2020-05-16 10:05:59
 */
public interface PropagateInfoService extends BaseService<PropagateInfo, Long> {

    /**
     * @Author hmr
     * @Description 发布广告或者通知
     * @Date: 2020/5/16 10:16
     * @param propagateInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> release(PropagateInfo propagateInfo);
}