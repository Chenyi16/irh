package top.imuster.order.provider.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.utils.UuidUtils;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.order.api.dto.ProductOrderDto;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.provider.service.OrderInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: OrderController
 * @Description: 二手商品订单控制器
 * @author: lpf
 * @date: 2019/12/18 18:03
 */
@RestController
@RequestMapping("/order/es")
@Api("订单控制器")
public class GoodsOrderController extends BaseController{

    @Resource
    OrderInfoService orderInfoService;

    /**
     * @Description: 生成订单
     * @Author: hmr
     * @Date: 2019/12/28 14:26
     * @param productOrderDto
     * @param request
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("生成订单")
    @PutMapping("/create")
    @NeedLogin
    public Message<OrderInfo> createOrder(@RequestBody ProductOrderDto productOrderDto) throws Exception {
        Long userId = getCurrentUserIdFromCookie();
        OrderInfo order = orderInfoService.getOrderByProduct(productOrderDto, userId);
        return Message.createBySuccess(order);
    }

    @ApiOperation("进入生成订单页面的时候返回一个订单号,用来防止用户重复提交订单,前端提交时必须带上")
    @GetMapping("/code")
    public Message<String> generateOrderCode(){
        return Message.createBySuccess(String.valueOf(UuidUtils.nextId()));
    }


    /**
     * @Description: 条件查询会员自己的订单
     * @Author: hmr
     * @Date: 2019/12/28 14:33
     * @param page
     * @param bindingResult
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("条件分页查询会员自己的订单，按照时间降序排序")
    @PostMapping
    @NeedLogin
    public Message<Page<OrderInfo>> orderList(@RequestBody @Validated(value = ValidateGroup.queryGroup.class)Page<OrderInfo> page, BindingResult bindingResult){
        validData(bindingResult);
        OrderInfo condition = page.getSearchCondition();
        condition.setOrderField("create_time");
        condition.setOrderFieldType("DESC");
        Page<OrderInfo> orderInfoPage = orderInfoService.selectPage(condition, page);
        return Message.createBySuccess(orderInfoPage);
    }

    /**
     * @Description: 根据id删除订单
     * @Author: hmr
     * @Date: 2019/12/28 14:42
     * @param orderId
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("根据id删除订单")
    @DeleteMapping("/{orderId}")
    @NeedLogin
    public Message editOrder(@PathVariable("orderId") Long orderId){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(orderId);
        orderInfo.setState(30);
        int i = orderInfoService.updateByKey(orderInfo);
        if(i == 0){
            return Message.createByError("删除订单失败,没有找到当前订单,请刷新后重试");
        }
        return Message.createBySuccess();
    }

    @ApiOperation(value = "根据id获得订单详情", httpMethod = "GET")
    @GetMapping("/{id}")
    @NeedLogin
    public Message<OrderInfo> getOrderDetailById(@PathVariable("id") Long id){
        OrderInfo orderInfo = orderInfoService.selectEntryList(id).get(0);
        return Message.createBySuccess(orderInfo);
    }

    @PostMapping("/edit")
    public Message<String> editOrder(@RequestBody OrderInfo orderInfo){
        return orderInfoService.editOrderInfo(orderInfo);
    }
}
