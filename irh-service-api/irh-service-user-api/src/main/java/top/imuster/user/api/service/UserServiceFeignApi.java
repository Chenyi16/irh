package top.imuster.user.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.RoleInfo;
import top.imuster.user.api.pojo.UserInfo;
import top.imuster.user.api.service.hystrix.UserServiceFeignHystrix;

import java.util.List;

/**
 * @ClassName: UserServiceFeignApi
 * @Description: UserServiceFeignApi
 * @author: hmr
 * @date: 2020/1/27 16:33
 */
@FeignClient(name = "user-service", path = "/user/feign", fallback = UserServiceFeignHystrix.class)
public interface UserServiceFeignApi {

    @GetMapping("/login/{email}")
    UserInfo loadUserInfoByEmail(@PathVariable("email") String email);

    @GetMapping("/roleAndAuth")
    List<RoleInfo> getAllRoleAndAuth();

    /**
     * @Author hmr
     * @Description 通过用户登录名获得用户的角色
     * @Date: 2020/1/30 17:52
     * @param name
     * @reture: java.util.List<java.lang.String>
     **/
    @GetMapping("/userRole/{loginName}")
    List<String> getRoleByUserName(@PathVariable("loginName") String loginName);

    /**
     * @Author hmr
     * @Description 用户注册
     * @Date: 2020/1/31 15:06
     * @param userInfo 用户信息
     * @param code 发送到邮箱的验证码
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> register(UserInfo userInfo, String code);
}
