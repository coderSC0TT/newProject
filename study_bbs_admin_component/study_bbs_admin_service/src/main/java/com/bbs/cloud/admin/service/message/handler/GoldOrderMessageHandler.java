package com.bbs.cloud.admin.service.message.handler;

import com.bbs.cloud.admin.common.enums.gift.GiftEnum;
import com.bbs.cloud.admin.common.util.CommonUtil;
import com.bbs.cloud.admin.common.util.JsonUtils;
import com.bbs.cloud.admin.service.contant.ServiceContant;
import com.bbs.cloud.admin.service.dto.ServiceGoldDTO;
import com.bbs.cloud.admin.service.enums.ServiceTypeEnum;
import com.bbs.cloud.admin.service.mapper.ServiceGiftMapper;
import com.bbs.cloud.admin.service.mapper.ServiceGoldMapper;
import com.bbs.cloud.admin.service.message.MessageHandler;
import com.bbs.cloud.admin.service.message.dto.OrderMessageDTO;
import com.bbs.cloud.admin.service.service.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName GoldOrderMessageHandler
 * @Description 添加描述
 * @Author Suguo
 * @LastChangeDate 2024/10/19 下午6:24
 */

public class GoldOrderMessageHandler implements MessageHandler {

    @Resource
    private ServiceGoldMapper serviceGoldMapper;

    final static Logger logger = LoggerFactory.getLogger(ServiceService.class);
    @Override
    public void handler(OrderMessageDTO orderMessageDTO) {
        logger.info("开始处理充值服务处理订单消息：{}", JsonUtils.objectToJson(orderMessageDTO));
        try{
            ServiceGoldDTO serviceGoldDTO = serviceGoldMapper.queryServiceGoldDTO(ServiceContant.SERVICE_GOLD_NAME);
            if(serviceGoldDTO == null) {
                serviceGoldDTO = new ServiceGoldDTO();
                serviceGoldDTO.setId(CommonUtil.createUUID());
                serviceGoldDTO.setName(ServiceContant.SERVICE_GOLD_NAME);
                serviceGoldDTO.setGold(ServiceContant.DEFAULT_SERVICE_GOLD);
                serviceGoldDTO.setUnusedGold(ServiceContant.DEFAULT_SERVICE_UNUSED_GOLD);
                serviceGoldDTO.setUsedGold(ServiceContant.DEFAULT_SERVICE_USED_GOLD);
                serviceGoldMapper.insertServiceGold(serviceGoldDTO);
            } else {
                //TODO 查询过去活动金币的使用情况 库存更新
            }

        }catch (Exception e) {
            //不需要重试 如果是正常下单失败了 此处 需要给运维提供一个接口 帮助最后更新 而不是此处让订单消息进入死循环重试
            logger.error("开始处理充值服务订单, 发生异常, message:{}", JsonUtils.objectToJson(orderMessageDTO));
            e.printStackTrace();
        }

    }

    @Override
    public Integer getServiceType() {
        return ServiceTypeEnum.RECHARGE_MESSAGE.getType();//充值服务
    }
}

