package top.imuster.message.provider.service.impl;


import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.message.pojo.NewsInfo;
import top.imuster.message.provider.dao.NewsInfoDao;
import top.imuster.message.provider.service.NewsInfoService;

import javax.annotation.Resource;

/**
 * NewsInfoService 实现类
 * @author 黄明人
 * @since 2020-01-17 17:13:09
 */
@Service("newsInfoService")
public class NewsInfoServiceImpl extends BaseServiceImpl<NewsInfo, Long> implements NewsInfoService {

    @Resource
    private NewsInfoDao newsInfoDao;

    @Override
    public BaseDao<NewsInfo, Long> getDao() {
        return this.newsInfoDao;
    }

    @Override
    public void writeFromMq(@ApiParam SendMessageDto sendMessageDto) {
        NewsInfo newsInfo = new NewsInfo();
        newsInfo.setContent(sendMessageDto.getBody());
        newsInfo.setTitle(sendMessageDto.getTopic());
        newsInfo.setSenderId(sendMessageDto.getSourceId());
        newsInfo.setReceiverId(Long.parseLong(sendMessageDto.getSendTo()));
        newsInfo.setCreateTime(sendMessageDto.getSendDate());
        newsInfoDao.insertEntry(newsInfo);
    }
}