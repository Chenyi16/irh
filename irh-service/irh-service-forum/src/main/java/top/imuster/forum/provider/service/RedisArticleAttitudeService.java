package top.imuster.forum.provider.service;

import top.imuster.forum.api.dto.UpCountDto;
import top.imuster.forum.api.dto.UpDto;

import java.util.List;

/**
 * @ClassName: RedisArticleAttitudeService
 * @Description: 直接操作redis存储点赞记录
 * @author: hmr
 * @date: 2020/2/8 17:26
 */
public interface RedisArticleAttitudeService {

    /**
     * @Author hmr
     * @Description 存储点赞记录到redis
     * @Date: 2020/2/8 17:27
     * @param targetId 被点赞的对象
     * @param type 1-文章  2-评论
     * @param userId 点赞人
     * @reture: void
     **/
    void saveUp2Redis(Long targetId, Integer type, Long userId);

    /**
     * @Author hmr
     * @Description 取消点赞
     * @Date: 2020/2/8 17:29
     * @param targetId
     * @param type
     * @param userId
     * @reture: void
     **/
    void downFromRedis(Long targetId, Integer type, Long userId);

    /**
     * @Author hmr
     * @Description 给被点赞的对象在数据库中加1
     * @Date: 2020/2/8 17:30
     * @param targetId
     * @param type
     * @reture: void
     **/
    void incrementUpCount(Long targetId, Integer type);

    /**
     * @Author hmr
     * @Description 目标对象减少1
     * @Date: 2020/2/8 17:31
     * @param targetId
     * @param type
     * @reture: void
     **/
    void decrementUpCount(Long targetId, Integer type);

    /**
     * @Author hmr
     * @Description 从redis中获得所有的点赞记录
     * @Date: 2020/2/8 17:43
     * @param
     * @reture: java.util.List<top.imuster.forum.api.dto.UpDto>
     **/
    List<UpDto> getAllUpFromRedis();


    /**
     * @Author hmr
     * @Description 从redis中获得点赞总数
     * @Date: 2020/2/8 19:50
     * @param
     * @reture: java.util.List<top.imuster.forum.api.dto.UpCountDto>
     **/
    List<UpCountDto> getAllUpCountFromRedis();



}
