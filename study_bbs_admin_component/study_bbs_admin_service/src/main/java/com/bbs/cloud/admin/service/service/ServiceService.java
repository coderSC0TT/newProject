package com.bbs.cloud.admin.service.service;

import com.bbs.cloud.admin.common.contant.RabbitContant;
import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.common.util.JsonUtils;
import com.bbs.cloud.admin.service.param.OrderMessageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName ServiceService
 * @Description 添加描述
 * @Author Suguo
 * @LastChangeDate 2024/10/19 下午1:43
 */
@Service
public class ServiceService {

    final static Logger logger = LoggerFactory.getLogger(ServiceService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public HttpResult sendMessage(OrderMessageParam param){
        logger.info("进入接收订单消息接口，请求参数:{}", JsonUtils.objectToJson(param));
        param.setDate(new Date());
        rabbitTemplate.convertAndSend(RabbitContant.SERVICE_EXCHANGE_NAME, RabbitContant.SERVICE_ROUTING_KEY, JsonUtils.objectToJson(param));
        return HttpResult.ok();
    }

}

