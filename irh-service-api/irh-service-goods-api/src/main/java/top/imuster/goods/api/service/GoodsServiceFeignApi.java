package top.imuster.goods.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.config.FeignConfig;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.hystrix.GoodsServiceFeignApiHystrix;
import top.imuster.life.api.pojo.ErrandInfo;

/**
 * @ClassName: GoodsServiceFeignApi
 * @Description: goods模块提供给其他模块调用的结构
 * @author: hmr
 * @date: 2019/12/26 20:34
 */
@FeignClient(value = "goods-service", configuration = FeignConfig.class, path = "/goods/feign",fallbackFactory = GoodsServiceFeignApiHystrix.class)
public interface GoodsServiceFeignApi {

    /**
     * @Description: 分页条件查询二手商品
     * @Author: hmr
     * @Date: 2019/12/26 20:38
     * @param page
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping(value = "/es/list")
    Message<Page<ProductInfo>> list(@RequestBody Page<ProductInfo> page);

    /**
     * @Description: 提供给管理员的下架商品接口
     * @Author: hmr
     * @Date: 2019/12/27 15:12
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @DeleteMapping("/es/{id}")
    Message<String> delProduct(@PathVariable("id") Long id);

    /**
     * @Description: 用户在下单的时候校验库存,如果有库存，则锁住库存.如果没有库存或者没有锁住库存，则返回false
     * @Author: hmr
     * @Date: 2019/12/28 10:58
     * @param productId
     * @reture: boolean
     **/
    @GetMapping("/es/lockStock/{productId}")
    ProductInfo lockStock(@PathVariable("productId") Long productId);

    /**
     * @Author hmr
     * @Description 根据id删除商品的留言信息
     * @Date: 2020/1/16 20:34
     * @param id
     * @reture: boolean
     **/
    @DeleteMapping("/es/pm/{id}")
    boolean deleteProductMessageById(@PathVariable("id") Long id);

    /**
     * @Author hmr
     * @Description 1-商品 2-留言 3-评价 4-帖子
     * @Date: 2020/1/17 10:59
     * @param null
     * @reture:
     **/
    @GetMapping("/es/{type}/{id}")
    Long getConsumerIdByType(@PathVariable("id") Long id, @PathVariable("type")Integer type);

    /**
     * @Author hmr
     * @Description 根据商品留言信息查找商品
     * @Date: 2020/1/22 11:32
     * @param targetId
     * @reture: java.lang.String
     **/
    @GetMapping("/es/pi/pm/{psId}")
    ProductInfo getProductInfoByProductMessage(@PathVariable("psId") Long targetId);


    /**
     * @Author hmr
     * @Description 根据更新errandInfo的信息
     * @Date: 2020/3/7 15:37
     * @param id
     * @param errandVersion
     * @reture: void
     **/
    @GetMapping("/errand/{id}/{version}")
    boolean updateErrandInfoById(@PathVariable("id") Long id, @PathVariable("version") Integer errandVersion);

    /**
     * @Author hmr
     * @Description 根据id查看errand是否可以被下单
     * @Date: 2020/3/7 15:42
     * @param errandId
     * @param errandVersion
     * @reture: boolean
     **/
    @GetMapping("/errand/avail/{errandId}/{version}")
    boolean errandIsAvailable(@PathVariable("errandId") Long errandId,@PathVariable("version") Integer errandVersion);

    /**
     * @Author hmr
     * @Description 根据id获得errand的地址和电话
     * @Date: 2020/5/9 16:06
     * @param errandId
     * @reture: top.imuster.life.api.pojo.ErrandInfo
     **/
    @GetMapping("/errand/addAndPhone/{id}")
    ErrandInfo getErrandInfoById(@PathVariable("id") Long errandId);

    /**
     * @Author hmr
     * @Description 获得商品的坚简略信息
     * @Date: 2020/5/11 19:51
     * @param productId
     * @reture: top.imuster.goods.api.pojo.ProductInfo
     **/
    @GetMapping("/es/brief/{id}")
    ProductInfo getProductBriefInfoById(@PathVariable("id") Long productId);

    /**
     * @Author hmr
     * @Description 卖家
     * @Date: 2020/5/12 10:44
     * @param productId
     * @param i
     * @reture: boolean
     **/
    @GetMapping("/es/state/{targetId}/{state}")
    boolean updateProductState(@PathVariable("targetId") Long productId, @PathVariable("state") Integer state);
}
