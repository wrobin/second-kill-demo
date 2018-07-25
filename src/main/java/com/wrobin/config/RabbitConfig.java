package com.wrobin.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * created by robin.wu on 2018/7/24
 **/
@Configuration
@PropertySource("rabbitmq.properties")
public class RabbitConfig {
    public static final String DEFAULT_QUEUE = "default_seckill";

    @Bean
    public Queue seckillQueue(){
        return new Queue(DEFAULT_QUEUE);
    }

    @Bean
    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        container.setQueues(seckillQueue());
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            channel.basicQos(1);
            byte[] body = message.getBody();
            System.out.printf("处理用户【%s】抢购订单\r\n",new String(body));
        });
        return container;
    }
}
