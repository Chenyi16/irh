package top.imuster.common.core.enums;

/**
 * @ClassName: BrowserType
 * @Description: 和BrowserTimes注解配合使用
 * @author: hmr
 * @date: 2020/1/23 17:26
 */
public enum BrowserType {
    ES_SELL_PRODUCT(1, "es::product::browser::times::map"),
    ES_DEMAND_PRODUCT(2, "demand::product::browser::times::map"),
    FORUM(3, "forum::article::browser::times::map"),
    PROPAGATE(4, "propagate::browser::times::map");
    public Integer type;
    private String redisKeyHeader;

    BrowserType(Integer type, String redisKeyHeader) {
        this.type = type;
        this.redisKeyHeader = redisKeyHeader;
    }

    public Integer getType() {
        return type;
    }

    public String getRedisKeyHeader() {
        return redisKeyHeader;
    }
}
