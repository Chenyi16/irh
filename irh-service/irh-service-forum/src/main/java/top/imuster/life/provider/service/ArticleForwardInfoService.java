package top.imuster.life.provider.service;


import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleForwardInfo;

/**
 * ArticleForwardInfoService接口
 * @author 黄明人
 * @since 2020-02-21 17:23:45
 */
public interface ArticleForwardInfoService extends BaseService<ArticleForwardInfo, Long> {

    /**
     * @Author hmr
     * @Description 查看当前用户的转发记录
     * @Date: 2020/2/21 17:30
     * @param currentUserIdFromCookie
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.life.api.pojo.ArticleForwardInfo>>
     **/
    Message<Page<ArticleForwardInfo>> getPageByUserId(Long currentUserIdFromCookie, Integer currentPage);

    /**
     * @Author hmr
     * @Description 用户转发
     * @Date: 2020/2/21 17:36
     * @param userId 当前用户id
     * @param id 文章id
     * @param content
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> forward(ArticleForwardInfo articleForwardInfo);
}