# second-kill-demo

此demo利用redis和rabbitmq来简单处理秒杀的业务场景。 在springboot的基础上可以更容易的使用redis和rabbitmq

整体思路：
1. 库存预存进redis
2. 利用redis的set来对用户请求排队，同时对重复userId去重
3. 库存判断，利用decr操作（spring-data-redis封装后可以使用boundValueOps(KEY).increment(-1L)的形式
4. decr成功，发送mq消息，成功既可以返回客户端抢购成功
5. mq消费端处理下单、支付等具体流程（这里落db,支付等场景都未实现）

