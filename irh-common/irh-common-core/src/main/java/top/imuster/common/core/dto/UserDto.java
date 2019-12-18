package top.imuster.common.core.dto;


import top.imuster.common.base.domain.BaseDomain;

/**
 * @ClassName: UserDto
 * @Description: 所有用户的公共属性的实体类
 * @author: hmr
 * @date: 2019/12/15 15:13
 */
public class UserDto extends BaseDomain {
    private static final long serialVersionUID = 8741880110225481101L;

    //用户的主键id
    private Long userId;

    //登录名
    private String loginName;

    //用户的类型 10:普通用户  20:卖家  30:社团组织  40: 管理员
    private Integer groupType;

    public UserDto(Long userId, String loginName, Integer groupType) {
        this.userId = userId;
        this.loginName = loginName;
        this.groupType = groupType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

}
