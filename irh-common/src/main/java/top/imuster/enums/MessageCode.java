package top.imuster.enums;

/**
 * @ClassName: MessageCode
 * @Description: Message类的返回枚举
 * @author: hmr
 * @date: 2019/12/1 9:21
 */
public enum MessageCode {
    SUCCESS(200, "响应成功"),
    ERROR(500, "服务器错误"),
    FORBID(401, "禁止访问"),
    ILLEGAL_ARGUMENT_CODE(100, "非法参数"),
    UNAUTHORIZED(401, "暂未登录或token已经过期,请重新登录");
    private Integer code;
    private String text;

    MessageCode(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * @Description: 根据状态码获得text
     * @Author: hmr
     * @Date: 2019/12/1 9:29
     * @param code
     * @reture: java.lang.String
     **/
    public String getText(Integer code){
        for (MessageCode c : MessageCode.values()){
            if(code == c.getCode()){
                return c.getText();
            }
        }
        return null;
    }
}
