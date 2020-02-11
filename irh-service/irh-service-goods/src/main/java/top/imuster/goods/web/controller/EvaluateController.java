package top.imuster.goods.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.goods.api.pojo.ProductEvaluateInfo;
import top.imuster.goods.service.ProductEvaluateInfoService;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;

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

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Resource
    ProductEvaluateInfoService productEvaluateInfoService;

    @Autowired
    OrderServiceFeignApi orderServiceFeignApi;

    /**
     * @Description: 用户的订单只有在收货状态的时候才能进行评价
     * @Author: hmr
     * @Date: 2020/1/9 11:51
     * @param productEvaluateInfo
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("根据订单的id评价(用户的订单只有在收货状态的时候才能进行评论,前台可以先进行一次逻辑判断)")
    @PostMapping("/{orderId}")
    public Message evaluateByOrderId(@PathVariable("orderId") Long orderId,@RequestBody ProductEvaluateInfo productEvaluateInfo){
        OrderInfo order = orderServiceFeignApi.getOrderById(orderId);
        if(order == null){
            return Message.createByError("未找到相应的订单,请刷新后重试");
        }
        if(order.getState() != 50){
            return Message.createByError("该订单还没有完成交易，完成该订单之后才能进行评价");
        }
        productEvaluateInfoService.evaluateByOrder(order, productEvaluateInfo);
        SendMessageDto sendMessageDto = new SendMessageDto();
        sendMessageDto.setTargetId(order.getSalerId());
        sendMessageDto.setSourceId(order.getBuyerId());
        productEvaluateInfoService.generateSendMessage(sendMessageDto);
        return Message.createBySuccess("评论成功");
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
        ProductEvaluateInfo productEvaluateInfo = productEvaluateInfoService.selectEntryList(id).get(0);
        return Message.createBySuccess(productEvaluateInfo);
    }

    /**
     * @Description: type -1表示查询买家的所有有效评价(用来查询自己评价记录)    -2表示卖家被评论的评论(查询别人对自己的评价)
     * @Author: hmr
     * @Date: 2020/1/9 15:10
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("根据用户的id查询对该用户的所有评价(1:查询自己 2:查询卖家被评论的记录)")
    @GetMapping("/{type}/{userId}")
    public Message listById(@PathVariable("userId") Long id, @PathVariable("type") Integer type){
        ProductEvaluateInfo productEvaluateInfo = new ProductEvaluateInfo();
        if(type == 1) productEvaluateInfo.setBuyerId(id);
        if(type == 2) productEvaluateInfo.setSalerId(id);
        productEvaluateInfo.setState(2);
        List<ProductEvaluateInfo> productEvaluateInfos = productEvaluateInfoService.selectEntryList(productEvaluateInfo);
        return Message.createBySuccess(productEvaluateInfos);
    }
}
