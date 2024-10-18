package com.bbs.cloud.admin.controller;

import com.bbs.cloud.admin.common.contant.RabbitContant;
import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.common.util.JedisUtil;
import com.bbs.cloud.admin.common.util.RedisOperator;
import com.bbs.cloud.admin.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description 添加描述
 * @Author Suguo
 * @LastChangeDate 2024/10/18 下午1:21
 */
@RestController
@RequestMapping("test")
public class TestController {
    final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private JedisUtil jedisUtil;
    @Autowired
    private TestService testService;

    @GetMapping("hello")
    public HttpResult hello() {
        logger.info("进入hello接口");
        return new HttpResult("hello");
    }
    @GetMapping("/redis")
    public HttpResult redis() {
        logger.info("进入redis测试接口");
        jedisUtil.set("test-jedis", "test-jedis-value");
        logger.info("jedis输出 {}", jedisUtil.get("test-jedis"));
        redisOperator.set("test-redis-operator", "test-redis-operator-value");
        logger.info("redisOperator输出 {}", redisOperator.get("test-redis-operator"));
        return HttpResult.ok();
    }
    @GetMapping("/mq")
    public HttpResult mq() {
        logger.info("进入mq测试接口");
        rabbitTemplate.convertAndSend(RabbitContant.TEST_EXCHANGE_NAME, RabbitContant.TEST_ROUTING_KEY,"Hello word, 欢迎每个同学都来报名！");
        return new HttpResult("hello")  ;
    }
}

