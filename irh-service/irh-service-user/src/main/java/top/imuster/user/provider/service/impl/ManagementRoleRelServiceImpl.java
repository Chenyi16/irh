package top.imuster.user.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.user.api.pojo.ManagementRoleRel;
import top.imuster.user.provider.dao.ManagementRoleRelDao;
import top.imuster.user.provider.service.ManagementRoleRelService;

import javax.annotation.Resource;

/**
 * ManagementRoleRelService 实现类
 * @author 黄明人
 * @since 2019-12-01 19:29:14
 */
@Service("managementRoleRelService")
public class ManagementRoleRelServiceImpl extends BaseServiceImpl<ManagementRoleRel, Long> implements ManagementRoleRelService {

    @Resource
    private ManagementRoleRelDao managementRoleRelDao;

    @Override
    public BaseDao<ManagementRoleRel, Long> getDao() {
        return this.managementRoleRelDao;
    }
}