package top.imuster.life.api.service.hystrix;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleCategoryInfo;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.service.ForumServiceFeignApi;

/**
 * @ClassName: ForumServiceFeignHystrix
 * @Description: ForumServiceFeignHystrix
 * @author: hmr
 * @date: 2020/2/1 12:10
 */
@Component
public class ForumServiceFeignHystrix implements FallbackFactory<ForumServiceFeignApi>{

    private static final Logger log = LoggerFactory.getLogger(ForumServiceFeignHystrix.class);

    @Override
    public ForumServiceFeignApi create(Throwable throwable) {
        throwable.printStackTrace();
        return new ForumServiceFeignApi() {
            @Override
            public Message<String> adminDeleteArticle(Long id) {
                log.error("管理员根据id删除帖子失败,帖子id为{}", id);
                return null;
            }

            @Override
            public boolean adminDeleteArticleCategory(Long id) {
                log.error("管理员根据id删除帖子分类失败,帖子分类id为{}", id);
                return false;
            }

            @Override
            public Message<Page<ArticleCategoryInfo>> adminCategoryList(Page<ArticleCategoryInfo> page) {
                log.error("管理员分页条件查询帖子分类失败,page对象为{}",page);
                return null;
            }

            @Override
            public ArticleCategoryInfo getCategoryInfoById(Long id) {
                log.error("管理员根据id获得帖子分类信息失败,帖子分类id为{}", id);
                return null;
            }

            @Override
            public boolean addArticleCategory(ArticleCategoryInfo category) {
                log.error("管理员新增帖子分类失败,新增帖子信息为{}", category);
                return false;
            }

            @Override
            public boolean editArticleCategory(ArticleCategoryInfo category) {
                log.error("管理员根据id提交修改帖子分类信息失败,帖子分类信息为{}", category);
                return false;
            }

            @Override
            public boolean adminDeleteArticleReview(Long id) {
                log.error("管理员根据留言id删除帖子留言失败,留言id为{}", id);
                return false;
            }

            @Override
            public Message<Page<ArticleInfo>> adminGetArticleList(Page<ArticleInfo> page) {
                log.error("管理员分页条件查询帖子信息失败,page对象为{}", page);
                return null;
            }

            @Override
            public Long getUserIdByType(Long targetId, Integer type) {
                log.error("管理员根据类型查看用户id失败");
                return null;
            }

            @Override
            public boolean updateErrandInfoById(Long id, Integer errandVersion) {
                log.error("更新ErrandInfo失败,id为{}", id);
                return false;
            }

            @Override
            public boolean errandIsAvailable(Long errandId, Integer errandVersion) {
                return false;
            }
        };
    }
}
