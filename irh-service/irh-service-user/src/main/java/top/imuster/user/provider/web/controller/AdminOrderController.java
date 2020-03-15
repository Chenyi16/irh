package top.imuster.user.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.order.api.dto.OrderTrendDto;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;

/**
 * @ClassName: AdminOrderController
 * @Description: 管理员操作订单的控制器
 * @author: hmr
 * @date: 2020/1/9 11:13
 */
@RestController
@RequestMapping("/admin/order")
@Api("后台管理订单")
public class AdminOrderController extends BaseController {

    @Autowired
    OrderServiceFeignApi orderServiceFeignApi;

    /**
     * @Description: 管理员分页条件查询订单
     * @Author: hmr
     * @Date: 2020/1/11 10:41
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping
    @ApiOperation(value = "管理员分页条件查询订单", httpMethod = "POST")
    public Message<Page<OrderInfo>> orderList(@ApiParam @RequestBody Page<OrderInfo> page){
        Message<Page<OrderInfo>> pageMessage = orderServiceFeignApi.orderList(page);
        if(null == pageMessage){
            logger.error("分页条件查询订单失败");
            return Message.createByError("查询失败");
        }
        return pageMessage;
    }

    @ApiOperation(value = "根据id查询订单", httpMethod = "GET")
    @GetMapping("/{id}")
    public Message<OrderInfo> getOrderById(@ApiParam("订单id") @PathVariable("id") Long id){
        OrderInfo order = orderServiceFeignApi.getOrderById(id);
        return Message.createBySuccess(order);
    }

    @ApiOperation("订单总金额趋势图")
    @GetMapping("/amount/trend/{type}")
    public Message<OrderTrendDto> orderAmountTrend(@PathVariable("type") Integer type){
        return orderServiceFeignApi.getOrderAmountTrend(type);
    }

    @ApiOperation("订单数量趋势图")
    @GetMapping("/total/trend/{type}")
    public Message<OrderTrendDto> orderTotalTrend(@PathVariable("type") Integer type){
        return orderServiceFeignApi.getOrderTotalTrend(type);
    }

}

