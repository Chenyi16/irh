package top.imuster.life.provider.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ForumHotTopic;
import top.imuster.life.provider.dao.ForumHotTopicDao;
import top.imuster.life.provider.service.ArticleInfoService;
import top.imuster.life.provider.service.ForumHotTopicService;
import top.imuster.life.provider.service.RedisArticleAttitudeService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * ForumHotTopicService 实现类
 * @author 黄明人
 * @since 2020-02-13 21:12:30
 */
@Service("forumHotTopicService")
public class ForumHotTopicServiceImpl extends BaseServiceImpl<ForumHotTopic, Long> implements ForumHotTopicService {
    @Resource
    private ForumHotTopicDao forumHotTopicDao;

    @Resource
    ArticleInfoService articleInfoService;

    @Resource
    RedisArticleAttitudeService redisArticleAttitudeService;

    @Override
    public BaseDao<ForumHotTopic, Long> getDao() {
        return this.forumHotTopicDao;
    }

    @Override
    public void updateHotTopicFromReids2Redis(List<HashSet<Long>> res) {
        Long[] targetIds = res.get(0).toArray(new Long[res.get(0).size()]);
        Long[] scores = res.get(1).toArray(new Long[res.get(1).size()]);
        ForumHotTopic condition = new ForumHotTopic();
        for (int i = 0; i < targetIds.length; i++) {
            Long score = forumHotTopicDao.selectScoreByTargetId(targetIds[i]);
            condition.setTargetId(targetIds[i]);
            if(score == null || score == 0){
                condition.setScore(scores[i]);
                forumHotTopicDao.insertEntry(condition);
            }else{
                score = score + scores[i];
                condition.setScore(score);
                forumHotTopicDao.updateScoreByTargetId(condition);
            }
        }
    }

    @Override
    public Message<List<ForumHotTopic>> totalHotTopicList(int topic) {
        List<ForumHotTopic> list =forumHotTopicDao.selectMaxScoreTop(topic);
        list.stream().forEach(forumHotTopic -> {
            Long targetId = forumHotTopic.getTargetId();
            ForumHotTopic brief = articleInfoService.getBriefByHotTopicId(targetId);
            forumHotTopic.setTargetTitle(brief.getTargetTitle());
        });
        return Message.createBySuccess(list);
    }

    @Override
    public Message<List<ForumHotTopic>> currentHotTopicList(int topic) {
        List<HashSet<Long>> res = redisArticleAttitudeService.getHotTopicFromRedis((long)topic);
        if(res == null || res.isEmpty()) {
            //当redis中没有的时候，则显示总榜的数据
            return this.totalHotTopicList(topic);
        }
        Long[] longs = res.get(0).toArray(new Long[res.get(0).size()]);
        List<ForumHotTopic> resList = new ArrayList<>();
        for (int i = 0; i < longs.length; i++) {
            ForumHotTopic brief = articleInfoService.getBriefByHotTopicId(longs[i]);
            resList.add(brief);
        }
        return Message.createBySuccess(resList);
    }


}