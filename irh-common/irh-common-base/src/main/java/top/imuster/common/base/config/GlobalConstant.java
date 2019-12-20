package top.imuster.common.base.config;

/**
 * @ClassName: GlobalConstant
 * @Description: 用来记录一些在很多类中都需要共同使用的常量；不放在配置文件中，不然很难找
 * @author: hmr
 * @date: 2019/12/16 11:28
 */
public class GlobalConstant {

    public static final String USER_TOKEN_DTO = "userTokenDto";         //在本地线程map中的key

    //redis相关
    public static final Long REDIS_JWT_EXPIRATION = 1800L;              //redis中jwt的过期时间


    //jwt相关
    public static final String JWT_TOKEN_HEADER = "Authorization";      //请求头中设置的字段，存放jwt
    public static final String JWT_SECRET = "irh-admin-secret";        //jwt加密解密的密钥
    public static final Long JWT_EXPIRATION = 1800L;               //jwt的超时(失效)时间，单位为秒
    public static final String JWT_TOKEN_HEAD = "Bearer ";               //Authorization头字段中的首部

    //日志相关
    public static String getErrorLog(String operation){
        return operation + "失败,错误信息为{},实体信息为{}";
    }



}
