package top.imuster.order.api.service.hystrix;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.order.api.dto.OrderTrendDto;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.service.OrderServiceFeignApi;

/**
 * @ClassName: OrderServiceFeignApiHystrix
 * @Description: 服务降级
 * @author: hmr
 * @date: 2019/12/29 22:02
 */
@Component
public class OrderServiceFeignApiHystrix implements FallbackFactory<OrderServiceFeignApi> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Override
    public OrderServiceFeignApi create(Throwable throwable) {
        log.error("OrderServiceFeignApiHystrix--->错误信息为{}",throwable.getMessage(), throwable);
        return new OrderServiceFeignApi() {
            @Override
            public OrderInfo getOrderById(Long orderId) {
                log.error("OrderServiceFeignApiHystrix--> 根据订单的id条件查询订单信息服务失败");
                return null;
            }

            @Override
            public boolean updateOrderStateById(Long id, Integer state) {
                return false;
            }

            @Override
            public Message<Page<OrderInfo>> orderList(Page<OrderInfo> page) {
                log.error("OrderServiceFeignApiHystrix--> 分页条件查询订单服务失败");
                return null;
            }

            @Override
            public Message<OrderTrendDto> getOrderAmountTrend(Integer type) {
                return null;
            }

            @Override
            public Message<OrderTrendDto> getOrderTotalTrend(Integer type) {
                return null;
            }

            @Override
            public void deleteProductEvaluate(Long targetId) {

            }

            @Override
            public Long getEvaluateWriterIdById(Long targetId) {
                return null;
            }
        };
    }
}
