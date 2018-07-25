package com.wrobin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * created by robin.wu on 2018/7/24
 **/
@SpringBootApplication
public class App implements CommandLineRunner{
    @Autowired
    private SecondKill secondKill;

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("初始化缓存");
        secondKill.init();
        System.out.println("秒杀开始>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        Random random = new Random(1000);
        ExecutorService executorService = Executors.newFixedThreadPool(2000);

        long startTime = System.currentTimeMillis();
        System.out.println("抢购开始");
        for (int i = 0; i < 20000 ;i++) {
            executorService.submit(()->{
                String userId = String.valueOf(random.nextInt(1000));
                secondKill.play(userId);
            });
        }

        executorService.shutdown();
        while (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
            System.out.println("线程池没有关闭");
        }

        long endTime = System.currentTimeMillis();
        System.out.printf("秒杀结束,耗时：%s ms>>>>>>>>>>>>>>>>>>>>>>>>>>>\r\n",(endTime - startTime));

    }
}
