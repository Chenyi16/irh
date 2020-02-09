package top.imuster.forum.provider.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.forum.provider.service.ArticleCollectionService;

import javax.annotation.Resource;

/**
 * @ClassName: ArticleCollectController
 * @Description: ArticleCollectController
 * @author: hmr
 * @date: 2020/2/9 10:57
 */
@RestController
@RequestMapping("/forum/collect")
public class ArticleCollectController extends BaseController {

    @Resource
    ArticleCollectionService articleCollectionService;

    /**
     * @Author hmr
     * @Description 用户收藏，type可取(1-取消收藏 2-收藏)
     * @Date: 2020/2/9 10:47
     * @param id
     * @param type
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("用户收藏")
    @GetMapping("/{id}")
    public Message<String> collection(@PathVariable("id") Long id){
        Long userId = getCurrentUserIdFromCookie();
        return articleCollectionService.collect(userId, id);
    }

    @ApiOperation("取消收藏")
    @DeleteMapping("/{id}")
    public Message<String> unCollect(@PathVariable("id") Long id){
        return articleCollectionService.unCollect(id);
    }
}
