package top.imuster.message.provider.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.imuster.common.core.dto.rabbitMq.SendUserCenterDto;
import top.imuster.message.provider.service.NewsInfoService;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName: CenterQueueListener
 * @Description: 消息中心队列监听器
 * @author: hmr
 * @date: 2020/1/17 18:07
 */
@Component
public class CenterQueueListener {
    private static final String QUEUE_NAME = "queue_inform_center";

    @Autowired
    private ObjectMapper objectMapper;

    @Resource
    NewsInfoService newsInfoService;

    @RabbitListener(queues = QUEUE_NAME)
    private void listener(String msg) throws IOException {
        SendUserCenterDto sendMessageDto = objectMapper.readValue(msg, SendUserCenterDto.class);
        newsInfoService.writeFromMq(sendMessageDto);
    }

}
