package top.imuster.auth.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import top.imuster.auth.exception.CustomSecurityException;
import top.imuster.auth.service.UserLoginService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.rabbitMq.SendEmailDto;
import top.imuster.common.core.enums.TemplateEnum;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.security.api.bo.AuthToken;
import top.imuster.security.api.bo.SecurityUserDto;
import top.imuster.user.api.service.UserServiceFeignApi;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: UserLoginService
 * @Description: UserLoginService
 * @author: hmr
 * @date: 2020/1/27 16:47
 */
@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {

    protected  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceFeignApi userServiceFeignApi;

    @Autowired
    GenerateSendMessageService generateSendMessageService;

    @Autowired
    DiscoveryClient discoveryClient;


    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;

    @Value("${security.applyUrl}")
    private String securityApplyUri;

    /**
     * @Author hmr
     * @Description 用户登录
     * @Date: 2020/1/29 14:58
     * @param
     * @reture: top.imuster.security.api.bo.SecurityUserDto
     **/
    public SecurityUserDto login(String loginName, String password, String code) throws JsonProcessingException {
        checkCode(loginName, code);

        AuthToken authToken = applyToken(loginName, password, clientId, clientSecret);
        String accessToken = authToken.getAccessToken();
        String content = new ObjectMapper().writeValueAsString(authToken);
        saveTokenToRedis(accessToken, content, tokenValiditySeconds);
        return new SecurityUserDto(authToken);
    }

    private boolean checkCode(String email, String code){
        String redisCode = (String)redisTemplate.opsForValue().get(RedisUtil.getWebCodeByEmail(email));
        if(redisCode == null || !redisCode.equalsIgnoreCase(code)) throw new CustomSecurityException("验证码错误或验证码已经过期,请重新获取");
        return true;
    }

    /**
     * @Author hmr
     * @Description 将申请到的令牌保存到redis中，其中以accessToken作为key，AuthToken作为内容
     * @Date: 2020/1/29 15:23
     * @param accessToken
     * @param content
     * @param ttl
     * @reture: boolean 查看是否存储成功  true标识存储成功  false标识存储失败
     **/
    private void saveTokenToRedis(String accessToken, String content, long ttl){
        String key = RedisUtil.getAccessToken(accessToken);
        redisTemplate.boundValueOps(key).set(content, ttl, TimeUnit.SECONDS);
        Long expire = redisTemplate.getExpire(key);
        if(expire < 0){
            log.error("用户登录的时候存储令牌到redis中失败");
            throw new CustomSecurityException("令牌存储失败,请稍后重试");
        }
    }

    public void getCode(String email, Integer type){
        SendEmailDto emailDto = new SendEmailDto();
        String code = UUID.randomUUID().toString().substring(0, 4);
        emailDto.setEmail(email);
        emailDto.setContent(code);
        log.info("验证码为:{}", code);
        if(type == 1){
            emailDto.setRedisKey(RedisUtil.getConsumerRegisterByEmailToken(email));
            emailDto.setSubject("irh注册");
            emailDto.setTemplateEnum(TemplateEnum.USER_REGISTER);
        }else if(type == 2){
            emailDto.setRedisKey(RedisUtil.getConsumerLoginByEmail(email));
            emailDto.setSubject("irh验证码登录");
            emailDto.setTemplateEnum(TemplateEnum.USER_LOGIN);
        }else if (type == 3){
            emailDto.setRedisKey(RedisUtil.getConsumerResetPwdKey(email));
            emailDto.setTemplateEnum(TemplateEnum.USER_RESETPWD);
            emailDto.setSubject("irh重置密码");
        }
        emailDto.setExpiration(20L);
        emailDto.setUnit(TimeUnit.MINUTES);
        generateSendMessageService.sendRegistEmail(emailDto);
    }

    /**
     * @Author hmr
     * @Description 从redis中获得存储的AuthToken对象
     * @Date: 2020/1/29 17:54
     * @param accessToken
     * @reture: top.imuster.security.api.bo.AuthToken
     **/
    public AuthToken getUserAuthTokenByAccessToken(String accessToken) throws IOException {
        String key = RedisUtil.getAccessToken(accessToken);
        String obj = (String) redisTemplate.opsForValue().get(key);
        AuthToken authToken = new ObjectMapper().readValue(obj, AuthToken.class);
        if(authToken == null){
            throw new CustomSecurityException("登录超时,请重新登录");
        }
        return authToken;
    }

    /**
     * @Author hmr
     * @Description 向security申请令牌
     * @Date: 2020/1/29 15:04
     * @param loginName
     * @param password
     * @param clientId
     * @param clientSecret
     * @reture: top.imuster.security.api.bo.AuthToken
     **/
    private AuthToken applyToken(String loginName, String password, String clientId, String clientSecret){
        //List<String> services = discoveryClient.getServices();
        /*URI uri = null;
        for (String service : services) {
            if(service.equalsIgnoreCase("irh-gateway")){
                ServiceInstance serviceInstance1 = discoveryClient.getInstances(service).get(0);
                uri = serviceInstance1.getUri();
            }
        }
        if(StringUtils.isBlank(String.valueOf(uri))){
            throw new CustomSecurityException("认证服务器已经停止工作,请稍后重试或联系管理员");
        }*/
        String url = new StringBuilder().append(securityApplyUri).append("/api/security/oauth/token").toString();
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        String httpBasic = getHttpBasic(clientId, clientSecret);
        header.add("Authorization",httpBasic);

        //定义body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username",loginName);
        body.add("password",password);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, header);

        //设置restTemplate远程调用时候，对400和401不让报错，正确返回数据
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if(response.getRawStatusCode()!=400 && response.getRawStatusCode()!=401){
                    super.handleError(response);
                }
            }
        });

        ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);

        //申请令牌信息
        Map resultMap = exchange.getBody();

        if(resultMap == null || resultMap.get("access_token") == null || resultMap.get("refresh_token") == null || resultMap.get("jti") == null){
            //解析spring security返回的错误信息
            if(resultMap!=null && resultMap.get("error_description")!=null){
                String error_description = (String) resultMap.get("error_description");
                throw new CustomSecurityException(error_description);
            }
            throw new CustomSecurityException("用户名或密码错误");
        }
        AuthToken authToken = new AuthToken();
        authToken.setAccessToken((String) resultMap.get("jti"));//用户身份令牌
        authToken.setRefreshToken((String) resultMap.get("refresh_token"));//刷新令牌
        authToken.setJwtToken((String) resultMap.get("access_token"));//jwt令牌
        return authToken;
    }

    public void deleteAccessTokenFromRedis(String accessToken){
        redisTemplate.delete(RedisUtil.getAccessToken(accessToken));
    }

    @Override
    public Message<String> getWebCodeByEmail(String email) {
        String code = cn.hutool.core.lang.UUID.randomUUID().toString().substring(0, 4);
        redisTemplate.opsForValue().set(RedisUtil.getWebCodeByEmail(email), code, 5, TimeUnit.MINUTES);
        return Message.createBySuccess(code);
    }


    /**
     * @Author hmr
     * @Description 获得Http Basic串
     * @Date: 2020/1/29 15:18
     * @param clientId
     * @param clientSecret
     * @reture: java.lang.String
     **/
    private static String getHttpBasic(String clientId,String clientSecret){
        String string = clientId+":"+clientSecret;
        //将串进行base64编码
        byte[] encode = Base64Utils.encode(string.getBytes());
        return "Basic "+new String(encode);
    }


}
