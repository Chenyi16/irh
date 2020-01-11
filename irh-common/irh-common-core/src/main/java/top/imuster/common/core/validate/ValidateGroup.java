package top.imuster.common.core.validate;

/**
 * @ClassName: ValidateGroup
 * @Description: 校验数据的分组
 * @author: hmr
 * @date: 2019/12/22 15:08
 */
public class ValidateGroup {
    //修改分组
    public interface editGroup{}

    //添加分组
    public interface addGroup{}

    //登录分组
    public interface loginGroup{}

    //下单分组
    public interface prePayment{}

    //注册分组
    public interface register{}

    //查询分组
    public interface queryGroup{}

    //评价商品分组
    public interface evaluateGroup{}

    //发布商品分组
    public interface releaseGroup{}

    //处理举报反馈分组
    public interface processGroup{}
}
