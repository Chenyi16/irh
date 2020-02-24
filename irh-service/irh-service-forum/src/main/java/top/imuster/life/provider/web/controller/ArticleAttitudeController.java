package top.imuster.life.provider.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.BrowserTimesAnnotation;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.life.api.pojo.UserForumAttribute;
import top.imuster.life.provider.service.RedisArticleAttitudeService;
import top.imuster.life.provider.service.UserForumAttributeService;

import javax.annotation.Resource;

/**
 * @ClassName: ArticleAttitudeController
 * @Description: 用户对于论坛中信息的态度  点赞功能
 * @author: hmr
 * @date: 2020/2/8 15:11
 */
@RestController
@RequestMapping("/forum")
public class ArticleAttitudeController extends BaseController {

    @Resource
    RedisArticleAttitudeService redisArticleAttitudeService;

    @Resource
    UserForumAttributeService userForumAttributeService;

    /**
     * @Author hmr
     * @Description 给文章点赞
     * @Date: 2020/2/14 10:06
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("给文章点赞")
    @BrowserTimesAnnotation(browserType = BrowserType.FORUM, disableBrowserTimes = true, value = "#p0")
    @GetMapping("/up/1/{id}")
    public Message<String> upArticleById(@PathVariable("id") Long id){
        redisArticleAttitudeService.saveUp2Redis(id, 1, getCurrentUserIdFromCookie());
        redisArticleAttitudeService.incrementUpCount(id, 1);
        return Message.createBySuccess();
    }

    /**
     * @Author hmr
     * @Description 给文章的评论点赞
     * @Date: 2020/2/14 10:06
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @ApiOperation("给文章的评论点赞")
    @NeedLogin
    @GetMapping("/up/2/{id}")
    public Message<String> upReviewById(@PathVariable("id") Long id){
        redisArticleAttitudeService.saveUp2Redis(id, 2, getCurrentUserIdFromCookie());
        redisArticleAttitudeService.incrementUpCount(id, 2);
        return Message.createBySuccess();
    }

    /**
     * @Author hmr
     * @Description 取消点赞
     * @Date: 2020/2/9 10:47
     * @param id
     * @param type
     * @reture: void
     **/
    @ApiOperation("取消点赞")
    @NeedLogin
    @GetMapping("/cancel/{id}/{type}")
    public Message<String> cancelUpByType(@PathVariable("id") Long id, @PathVariable("type") Integer type){
        redisArticleAttitudeService.saveUp2Redis(id, type, getCurrentUserIdFromCookie());
        redisArticleAttitudeService.decrementUpCount(id, type);
        return Message.createBySuccess();
    }

    @ApiOperation("查看自己在文章模块点赞的记录")
    @PostMapping("/list")
    public Message<Page<UserForumAttribute>> upList(@RequestBody Page<UserForumAttribute> page){
        return userForumAttributeService.getUpList(page, getCurrentUserIdFromCookie());
    }

    /**
     * @Author hmr
     * @Description 根据id和type获得点赞总数
     * @Date: 2020/2/9 10:47
     * @param id
     * @param type
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.Long>
     **/
    @ApiOperation("根据id和type获得点赞总数")
    @GetMapping("/{id}/{type}")
    public Message<Long> getUpTotalByType(@PathVariable("id") Long id, @PathVariable("type") Integer type){
        Long total = userForumAttributeService.getUpTotalByTypeAndId(id, type, RedisUtil.getUpTotalKey(id, type));
        return Message.createBySuccess(total);
    }

    /**
     * @Author hmr
     * @Description 根据id查看是否点赞了该目标
     * @Date: 2020/2/24 12:03
     * @param type
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.Integer>
     **/
    @ApiOperation("根据id查看是否点赞了该目标   1-未点赞  2-点赞")
    @GetMapping("/state/{type}/{id}")
    public Message<Integer> getUpStateByTargetId(@PathVariable("type") Integer type, @PathVariable("id") Long id){
        Long userId;
        try{
            userId = getCurrentUserIdFromCookie();
        }catch (Exception e){
            return Message.createBySuccess(1);
        }
        return userForumAttributeService.getStateByTargetId(type, id, userId);
    }
}