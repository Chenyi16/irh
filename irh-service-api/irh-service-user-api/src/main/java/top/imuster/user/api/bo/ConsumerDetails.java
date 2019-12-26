package top.imuster.user.api.bo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.imuster.user.api.pojo.AuthInfo;
import top.imuster.user.api.pojo.ConsumerInfo;
import top.imuster.user.api.pojo.RoleInfo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: ConsumerDetails
 * @Description: security中需要
 * @author: hmr
 * @date: 2019/12/7 15:12
 */
public class ConsumerDetails implements UserDetails {
    private ConsumerInfo consumerInfo;

    private List<RoleInfo> roleInfoList;

    public ConsumerDetails(ConsumerInfo consumerInfo) {
        this.consumerInfo = consumerInfo;
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleName("USER");
        roleInfoList.add(roleInfo);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleInfoList.stream()
                .filter(roleInfo -> roleInfo.getRoleName() != null)
                .map(roleInfo -> new SimpleGrantedAuthority(roleInfo.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return consumerInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return consumerInfo.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @Description: 判断用户是否被锁定
     * @Author: hmr
     * @Date: 2019/12/7 15:17
     * @param
     * @reture: boolean 当等于20的时候表示被锁定
     **/
    @Override
    public boolean isAccountNonLocked() {
        return consumerInfo.getState() == 20;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @Description: 判断用户是否被禁用
     * @Author: hmr
     * @Date: 2019/12/7 15:15
     * @param
     * @reture: boolean 大于20表示可用
     **/
    @Override
    public boolean isEnabled() {
        return consumerInfo.getState() > 20;
    }
}
