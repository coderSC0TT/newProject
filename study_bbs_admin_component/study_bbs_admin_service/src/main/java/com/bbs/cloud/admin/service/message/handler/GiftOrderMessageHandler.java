package com.bbs.cloud.admin.service.message.handler;

import com.bbs.cloud.admin.common.enums.gift.GiftEnum;
import com.bbs.cloud.admin.common.util.CommonUtil;
import com.bbs.cloud.admin.common.util.JsonUtils;
import com.bbs.cloud.admin.service.contant.ServiceContant;
import com.bbs.cloud.admin.service.dto.ServiceGiftDTO;
import com.bbs.cloud.admin.service.enums.ServiceTypeEnum;
import com.bbs.cloud.admin.service.mapper.ServiceGiftMapper;
import com.bbs.cloud.admin.service.message.MessageHandler;
import com.bbs.cloud.admin.service.message.dto.OrderMessageDTO;
import com.bbs.cloud.admin.service.service.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName GiftOrderMessageHandler
 * @Description 添加描述
 * @Author Suguo
 * @LastChangeDate 2024/10/19 下午6:20
 */

public class GiftOrderMessageHandler implements MessageHandler{

    final static Logger logger = LoggerFactory.getLogger(ServiceService.class);

    @Resource
    private ServiceGiftMapper serviceGiftMapper;

    @Override
    public void handler(OrderMessageDTO orderMessageDTO) {
        logger.info("开始处理礼物服务处理订单消息：{}", JsonUtils.objectToJson(orderMessageDTO));
        try{
            Map<Integer, GiftEnum> giftEnumMap = GiftEnum.getGiftsMap();
            for(GiftEnum giftEnum : giftEnumMap.values()) {
                Integer giftType = giftEnum.getGiftType(); //拿到礼物类型
                ServiceGiftDTO serviceGiftDTO =serviceGiftMapper.queryGiftDTO(giftType);
                //如果是初次访问 初始化
                if(serviceGiftDTO == null) {
                    serviceGiftDTO =new ServiceGiftDTO();
                    serviceGiftDTO.setId(CommonUtil.createUUID());
                    serviceGiftDTO.setGiftType(giftType);
                    serviceGiftDTO.setAmount(ServiceContant.DEFAULT_SERVICE_GIFT_AMOUNT);
                    serviceGiftDTO.setUnusedAmount(ServiceContant.DEFAULT_SERVICE_GIFT_AMOUNT);
                    serviceGiftDTO.setUsedAmount(ServiceContant.DEFAULT_SERVICE_USED_GIFT_AMOUNT);
                    serviceGiftMapper.insertGiftDTO(serviceGiftDTO);
                }else{
                    //TODO 查询活动使用礼物情况 进行库存更新
                }
            }
        }catch (Exception e) {
            logger.error("开始处理礼物服务订单, 发生异常, message:{}", JsonUtils.objectToJson(orderMessageDTO));
            e.printStackTrace();
        }
    }

    @Override
    public Integer getServiceType() {
        return ServiceTypeEnum.GIFT_MESSAGE.getType();
    }
}



