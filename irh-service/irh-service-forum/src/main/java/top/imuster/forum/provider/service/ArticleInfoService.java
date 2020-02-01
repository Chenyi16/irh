package top.imuster.forum.provider.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.core.dto.UserDto;
import top.imuster.forum.api.pojo.ArticleInfo;

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
     * @Description 用户查看自己发布的帖子
     * @Date: 2020/2/1 19:44
     * @param page
     * @reture: java.util.List<top.imuster.forum.api.pojo.ArticleInfo>
     **/
    List<ArticleInfo> list(Page<ArticleInfo> page);
}