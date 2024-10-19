package com.bbs.cloud.admin.service.message;

import com.bbs.cloud.admin.common.contant.RabbitContant;
import com.bbs.cloud.admin.common.util.JsonUtils;
import com.bbs.cloud.admin.service.message.dto.OrderMessageDTO;
import com.bbs.cloud.admin.service.service.ServiceService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName MessageReceiver
 * @Description 添加描述
 * @Author Suguo
 * @LastChangeDate 2024/10/19 下午2:01
 */
@Component
public class MessageReceiver {
    final static Logger logger = LoggerFactory.getLogger(ServiceService.class);
    //监听有公司方运维中心没有消息
    @RabbitListener(queues = RabbitContant.SERVICE_QUEUE_NAME)
    public void receiver(String message) {
        logger.info("接收到订单成功消息：{}" + message);
        if(StringUtils.isEmpty(message)){
            logger.info("接收到订单成功消息 消息为空：{}" + message);
            return; // 消息为空
        }
        OrderMessageDTO orderMessageDTO;
        try {
            orderMessageDTO= JsonUtils.jsonToPojo(message, OrderMessageDTO.class);
            if(orderMessageDTO!=null){
                logger.info("接收到订单成功消息 消息转换为空：{}" + message);
                return;
            }
        }catch (Exception e){
            logger.info("接收到订单成功消息 消息转换异常：{}" + message);
            e.printStackTrace();
            return; // 消息转换异常
        }
        Integer serviceType = orderMessageDTO.getServiceType();
        //TODO 消息处理
    }
}

