package top.imuster.life.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.life.api.pojo.ArticleTagRel;

import java.util.List;

/**
 * ArticleTagRelService接口
 * @author 黄明人
 * @since 2020-02-16 16:19:34
 */
public interface ArticleTagRelService extends BaseService<ArticleTagRel, Long> {

    /**
     * @Author hmr
     * @Description 根据文章id获得文章的标签名称
     * @Date: 2020/2/28 10:03
     * @param id 文章id
     * @reture: java.lang.String 文章的
     **/
    List<String> getArticleTagsById(Long id);

    /**
     * @Author hmr
     * @Description 根据标签集合查找文章
     * @Date: 2020/3/25 14:33
     * @param tagIds
     * @param pageSize
     * @param currentPage
     * @reture: java.util.List<java.lang.Long>
     **/
    List<Long> selectArticleIdsByIds(List<Long> tagIds, Long pageSize, Long currentPage);
}