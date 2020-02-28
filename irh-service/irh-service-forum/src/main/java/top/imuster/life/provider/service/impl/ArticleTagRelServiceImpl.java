package top.imuster.life.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.life.api.pojo.ArticleTagRel;
import top.imuster.life.provider.dao.ArticleTagRelDao;
import top.imuster.life.provider.service.ArticleTagRelService;

import javax.annotation.Resource;
import java.util.List;

/**
 * ArticleTagRelService 实现类
 * @author 黄明人
 * @since 2020-02-16 16:19:34
 */
@Service("articleTagRelService")
public class ArticleTagRelServiceImpl extends BaseServiceImpl<ArticleTagRel, Long> implements ArticleTagRelService {

    @Resource
    private ArticleTagRelDao articleTagRelDao;

    @Override
    public BaseDao<ArticleTagRel, Long> getDao() {
        return this.articleTagRelDao;
    }

    @Override
    public String getArticleTagsById(Long id) {
        List<String> names = articleTagRelDao.selectTagNameByArticleId(id);
        StringBuilder res = new StringBuilder();
        for (String name : names) {
            res.append(name);
        }
        return res.toString();
    }
}