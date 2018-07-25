package com.wrobin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * created by robin.wu on 2018/7/24
 **/
@Component
public class SecondKill {
    private static final String STORE_KEY = "store_num";
    private static final String MEMBERS_FILTER_KEY = "filter_members_set";
    private static final int storeNum = 100;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MqSender mqSender;
    /**
     * 库存初始化
     */
    public void init(){
        //测试，删除已有的key
        stringRedisTemplate.delete(MEMBERS_FILTER_KEY);
        //初始化库存
        stringRedisTemplate.boundValueOps(STORE_KEY).set(String.valueOf(storeNum));
    }

    /**
     * 模仿下单
     *
     * 1. zset过滤重复用户
     * 2. 判断库存总量
     * 3. 发异步消息
     */
    public void play(String userId) {
        //入队，去重
        Long addRs = stringRedisTemplate.boundSetOps(MEMBERS_FILTER_KEY).add(userId);
        if(addRs <= 0 ){
            System.out.printf("【%s】已在排队中\r\n",userId);
            return;
        }

        //库存判断
        Long incrRs = stringRedisTemplate.boundValueOps(STORE_KEY).increment(-1L);
        if(incrRs < 0 ){
            System.out.printf("【%s】库存已售完\r\n",userId);
            return;
        }

        //发异步消息
        System.out.printf("【%s】抢购成功，发异步消息\r\n",userId);
        mqSender.send(userId);
    }
}
