package top.imuster.order.provider.web.rpc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;
import top.imuster.order.provider.exception.OrderException;
import top.imuster.order.provider.service.OrderInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: OrderServuceFeignClient
 * @Description:
 * @author: hmr
 * @date: 2019/12/27 15:33
 */
@RestController
@RequestMapping("/")
@Slf4j
public class OrderServiceFeignClient implements OrderServiceFeignApi {

    @Resource
    OrderInfoService orderInfoService;

    @Override
    @GetMapping("/feign/order/{orderId}")
    public OrderInfo getOrderById(@PathVariable("orderId") Long orderId) {
        OrderInfo orderInfo = orderInfoService.selectEntryList(orderId).get(0);
        return orderInfo;
    }
}
