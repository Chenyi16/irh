package top.imuster.life.provider.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.common.core.dto.UserDto;
import top.imuster.life.api.dto.ForwardDto;
import top.imuster.life.api.dto.UserBriefDto;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.pojo.ForumHotTopic;

import java.util.List;

/**
 * ArticleInfoService接口
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
public interface ArticleInfoService extends BaseService<ArticleInfo, Long> {

    /**
     * @Author hmr
     * @Description 用户发布帖子
     * @Date: 2020/2/1 19:23
     * @param currentUser
     * @param articleInfo
     * @reture: void
     **/
    void release(UserDto currentUser, ArticleInfo articleInfo);

    /**
     * @Author hmr
     * @Description 用户查看自己发布的帖子，不包括帖子的内容
     * @Date: 2020/2/1 19:44
     * @param page
     * @reture: java.util.List<ArticleInfo>
     **/
    List<ArticleInfo> list(Page<ArticleInfo> page);

    /**
     * @Author hmr
     * @Description 根据帖子id获得帖子的详细信息
     * @Date: 2020/2/2 10:57
     * @param id
     * @param userId 当前用户
     * @reture: ArticleInfo
     **/
    ArticleInfo getArticleDetailById(Long id);

    /**
     * @Author hmr
     * @Description 根据帖子的id获得用户id
     * @Date: 2020/2/5 10:39
     * @param targetId
     * @reture: java.lang.Long
     **/
    Long getUserIdByArticleId(Long targetId);

    /**
     * @Author hmr
     * @Description 根据id数组查询点赞数和id
     * @Date: 2020/2/8 20:35
     * @param ids
     * @reture: java.util.List<ArticleInfo>
     **/
    List<ArticleInfo> getUpTotalByIds(Long[] ids);

    /**
     * @Author hmr
     * @Description 根据id获得点赞总数
     * @Date: 2020/2/9 10:37
     * @param id
     * @reture: java.lang.Long
     **/
    Long getUpTotal(Long id);

    /**
     * @Author hmr
     * @Description 根据id获得帖子的简略信息，带缓存的
     * @Date: 2020/2/11 16:23
     * @param id
     * @reture: ArticleInfo
     **/
    ArticleInfo getBriefById(Long id);

    /**
     *
     * @param id
     * @return
     */
    List<ArticleInfo> hotTopicListByCategory(Long id);

    /**
     * @Author hmr
     * @Description 根据id一部分信息
     * @Date: 2020/2/14 12:33
     * @param longs
     * @reture: java.util.List<ForumHotTopic>
     **/
    List<ArticleInfo> selectInfoByTargetIds(Long[] longs);

    /**
     * @Author hmr
     * @Description 根据id查看简略信息    提供给热搜榜的
     * @Date: 2020/2/14 15:16
     * @param aLong
     * @reture: ArticleInfo
     **/
    ForumHotTopic getBriefByHotTopicId(Long aLong);

    /**
     * @Author hmr
     * @Description 根据用户id获得用户的文章总数，获赞总数、收藏总数
     * @Date: 2020/2/15 15:34
     * @param userId
     * @reture: UserBriefDto
     **/
    UserBriefDto getUserBriefByUserId(Long userId);

    /**
     * @Author hmr
     * @Description 更新浏览次数
     * @Date: 2020/2/15 17:35
     * @param res
     * @reture: void
     **/
    void updateBrowserTimesFromRedis2Redis(List<BrowserTimesDto> res);

    /**
     * @Author hmr
     * @Description 将redis中储存的转发记录转存到db
     * @Date: 2020/2/23 19:35
     * @param res
     * @reture: void
     **/
    void updateForwardTimesFromRedis2DB(List<ForwardDto> res);

}