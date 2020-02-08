package top.imuster.goods.web.rpc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.service.GoodsDemandServiceFeignApi;
import top.imuster.goods.service.ProductDemandInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: GoodsDemandServiceFeignClient
 * @Description: GoodsDemandServiceFeignClient
 * @author: hmr
 * @date: 2020/2/7 17:19
 */
@RestController
@RequestMapping("/goods/feign/demand")
@Slf4j
public class GoodsDemandServiceFeignClient implements GoodsDemandServiceFeignApi {

    @Resource
    ProductDemandInfoService productDemandInfoService;

    @Override
    @PostMapping
    public Message<Page<ProductDemandInfo>> list(@RequestBody Page<ProductDemandInfo> page) {
        Page<ProductDemandInfo> selectPage = productDemandInfoService.selectPage(page.getSearchCondition(), page);
        return Message.createBySuccess(selectPage);
    }

    @Override
    public Message<String> deleteDemandById(Long id) {
        ProductDemandInfo condition = new ProductDemandInfo();
        condition.setId(id);
        condition.setState(1);
        int i = productDemandInfoService.updateByKey(condition);
        if(i == 1){
            return Message.createBySuccess();
        }
        log.error("管理员根据id删除用户发布的需求返回值异常");
        return Message.createByError("删除失败,请刷新后重试");
    }

    @Override
    public Message<ProductDemandInfo> getDemandById(Long id) {
        ProductDemandInfo productDemandInfo = productDemandInfoService.selectEntryList(id).get(0);
        return Message.createBySuccess(productDemandInfo);
    }
}
