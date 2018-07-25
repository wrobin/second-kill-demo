package com.wrobin;

import com.wrobin.config.RabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * created by robin.wu on 2018/7/24
 **/
@Component
public class MqSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String userId) {
        this.rabbitTemplate.convertAndSend(RabbitConfig.DEFAULT_QUEUE, userId);
    }
}
