package top.imuster.user.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.user.api.pojo.ConsumerInfo;

/**
 * ConsumerInfoService接口
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
public interface ConsumerInfoService extends BaseService<ConsumerInfo, Long> {

    ConsumerInfo loginByName(String name);
}