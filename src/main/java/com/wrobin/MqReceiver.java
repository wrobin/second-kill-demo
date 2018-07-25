package com.wrobin;

import com.wrobin.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * created by robin.wu on 2018/7/24
 **/
@Component
//@RabbitListener(queues = RabbitConfig.DEFAULT_QUEUE)
public class MqReceiver {

    @RabbitHandler
    public void process(String userId) {
        System.out.println(userId + "的订单数据写DB成功");
    }
}
