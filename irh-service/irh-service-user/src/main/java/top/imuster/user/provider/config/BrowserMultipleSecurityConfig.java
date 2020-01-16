package top.imuster.user.provider.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.imuster.auth.component.JwtAuthenticationTokenFilter;
import top.imuster.auth.component.LoginSuccessHandle;
import top.imuster.auth.component.UrlAccessDecisionManager;
import top.imuster.auth.config.BrowserSecurityConfig;
import top.imuster.user.provider.service.ConsumerInfoService;
import top.imuster.user.provider.service.ManagementInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: BrowserMultipleSecurityConfig
 * @Description: 浏览器多用户安全配置类
 * @author: hmr
 * @date: 2019/12/26 15:16
 */
@EnableWebSecurity
@Configuration
public class BrowserMultipleSecurityConfig {

    /**
     * @Description: 管理员安全配置类
     * @Author: hmr
     * @Date: 2019/12/26 15:18
     * @reture:
     **/
    @Configuration
    @Order(1)
    public class AdminSecurityConfig extends BrowserSecurityConfig {

        /*@Bean("adminJwtFilter")
        public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(@Qualifier("adminDetailsService") UserDetailsService userDetailsService){
            return new JwtAuthenticationTokenFilter(userDetailsService);
        }*/

        @Resource
        ManagementInfoService managementInfoService;

        @Bean
        public UserDetailsService adminDetailsService(){
            return (username) -> managementInfoService.loadManagementByName(username);
        }

        @Bean
        UrlAccessDecisionManager urlAccessDecisionManager() {
            return new UrlAccessDecisionManager();
        }

        @Bean
        UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() {
            return new UrlFilterInvocationSecurityMetadataSource();
        }

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .authorizeRequests()
                    .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                        @Override
                        public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                            o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
                            o.setAccessDecisionManager(urlAccessDecisionManager());
                            return o;
                        }
                    });

            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity
                    .authorizeRequests();
            for (String url : ignoreUrlsConfig().getUrls()) {
                registry.antMatchers(url).permitAll();
            }

            httpSecurity.csrf()// 由于使用的是JWT，我们这里不需要csrf
                    .disable()
                    .sessionManagement()// 基于token，所以不需要session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
                            "/",
                            "/*.html",
                            "/favicon.ico",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js",
                            "/swagger-resources/**"
                    )
                    .permitAll()
                    .antMatchers("/admin/login", "/admin/logout", "/admin/register")// 对登录注册要允许匿名访问
                    .permitAll()
                    .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
                    .permitAll()
                    .antMatchers("/**")//测试时全部运行访问
                    .permitAll()
                    .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                    .authenticated();
            // 禁用缓存
            httpSecurity.headers().cacheControl();
            // 添加JWT filter
//            httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(adminDetailsService()), UsernamePasswordAuthenticationFilter.class);
            //添加自定义未授权和未登录结果返回
            httpSecurity.exceptionHandling()
                    .accessDeniedHandler(restfulAccessDeniedHandler())
                    .authenticationEntryPoint(restAuthenticationEntryPoint());
        }
    }

    /**
     * @Description: 普通用户安全配置类
     * @Author: hmr
     * @Date: 2019/12/26 15:19
     * @reture:
     **/
    @Configuration
    @Order(2)
    public class ConsumerSecurityConfig extends BrowserSecurityConfig{

        @Resource
        ConsumerInfoService consumerInfoService;

        @Bean
        public UserDetailsService consumerDetailsService(){
            return (username) -> consumerInfoService.loadConsumerDetailsByName(username);
        }

        @Bean
        LoginSuccessHandle loginSuccessHandle(){
            return new LoginSuccessHandle();
        }

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.csrf()// 由于使用的是JWT，我们这里不需要csrf
                    .disable()
                    .sessionManagement()// 基于token，所以不需要session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
                            "/",
                            "/*.html",
                            "/favicon.ico",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js",
                            "/swagger-resources/**"
                    )
                    .permitAll()
                    .antMatchers("/login", "/logout", "/register")// 对登录注册要允许匿名访问
                    .permitAll()
                    .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
                    .permitAll()
                    .antMatchers("/**")//测试时全部运行访问
                    .permitAll()
                    .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                    .authenticated();
            // 禁用缓存
            httpSecurity.headers().cacheControl();
            //添加自定义未授权和未登录结果返回
            httpSecurity.exceptionHandling()
                    .accessDeniedHandler(restfulAccessDeniedHandler())
                    .authenticationEntryPoint(restAuthenticationEntryPoint());

        }
    }
}
