package top.imuster.goods.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.goods.api.pojo.ProductEvaluateInfo;
import top.imuster.goods.service.ProductEvaluateInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: EvaluateController
 * @Description: 商品评价控制器
 * @author: hmr
 * @date: 2020/1/9 14:24
 */
@Api("商品评价,指的是购买商品之后对商品的评价")
@RestController
@RequestMapping("/goods/evaluate")
public class EvaluateController extends BaseController {

    @Resource
    ProductEvaluateInfoService productEvaluateInfoService;

    /**
     * @Description: 用户的订单只有在收货状态的时候才能进行评价
     * @Author: hmr
     * @Date: 2020/1/9 11:51
     * @param productEvaluateInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("根据订单的id评价(用户的订单只有在收货状态的时候才能进行评论,前台可以先进行一次逻辑判断)")
    @NeedLogin
    @PostMapping("/{orderId}")
    public Message evaluateByOrderId(@PathVariable("orderId") Long orderId,@RequestBody ProductEvaluateInfo productEvaluateInfo){
        productEvaluateInfo.setBuyerId(getCurrentUserIdFromCookie());
        return productEvaluateInfoService.writeEvaluateByOrderId(orderId, productEvaluateInfo);
    }

    /**
     * @Author hmr
     * @Description type 1-表示查询买家的所有有效评价(用来查询自己评价记录)    2-表示卖家被评论的评论(查询别人对自己的评价)
     * @Date: 2020/5/6 9:39
     * @param type
     * @param pageSize
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductEvaluateInfo>>
     **/
    @GetMapping("/list/{pageSize}/{currentPage}/{type}")
    public Message<Page<ProductEvaluateInfo>> getList(@PathVariable("type") Integer type, @PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage){
        Long userId = getCurrentUserIdFromCookie();
        return productEvaluateInfoService.getListByUserId(pageSize, currentPage, userId, type);
    }

    @GetMapping("/{pageSize}/{currentPage}/{userId}")
    public Message<Page<ProductEvaluateInfo>> listByUserId(@PathVariable("type") Integer type,
                                                           @PathVariable("pageSize") Integer pageSize,
                                                           @PathVariable("userId") Long userId,
                                                           @PathVariable("currentPage") Integer currentPage){
        return productEvaluateInfoService.getListByUserId(pageSize, currentPage, userId, 2);
    }

    @ApiOperation("根据评论id删除自己的评价")
    @DeleteMapping("/{id}")
    public Message deleteEvaluateById(@PathVariable("id") Long id){
        Long userId = getCurrentUserIdFromCookie();
        ProductEvaluateInfo productEvaluateInfo = new ProductEvaluateInfo();
        productEvaluateInfo.setId(id);
        productEvaluateInfo.setBuyerId(userId);
        productEvaluateInfo.setState(1);
        productEvaluateInfoService.updateByKey(productEvaluateInfo);
        return Message.createBySuccess();
    }

    @ApiOperation("根据id查询该评价的内容")
    @GetMapping("/{id}")
    public Message getEvaluateById(@PathVariable("id") Long id){
        List<ProductEvaluateInfo> lists = productEvaluateInfoService.selectEntryList(id);
        if(lists == null || lists.isEmpty()) return Message.createBySuccess();
        return Message.createBySuccess(lists.get(0));
    }

    @GetMapping("/byOrderId/{orderId}")
    public Message<ProductEvaluateInfo> getInfoByOrderId(@PathVariable("orderId") Long orderId){
        ProductEvaluateInfo condition = new ProductEvaluateInfo();
        condition.setOrderId(orderId);
        List<ProductEvaluateInfo> infos = productEvaluateInfoService.selectEntryList(condition);
        if(infos == null || infos.isEmpty()) return Message.createBySuccess();
        return Message.createBySuccess(infos.get(0));
    }

}
