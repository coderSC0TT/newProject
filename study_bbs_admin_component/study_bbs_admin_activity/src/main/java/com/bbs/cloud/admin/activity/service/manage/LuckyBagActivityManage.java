package com.bbs.cloud.admin.activity.service.manage;

import com.bbs.cloud.admin.activity.contant.ActivityContant;
import com.bbs.cloud.admin.activity.dto.ActivityDTO;
import com.bbs.cloud.admin.activity.dto.LuckyBagDTO;
import com.bbs.cloud.admin.activity.exception.ActivityException;
import com.bbs.cloud.admin.activity.mapper.ActivityMapper;
import com.bbs.cloud.admin.activity.params.CreateActivityParam;
import com.bbs.cloud.admin.activity.params.OperatorActivityParam;
import com.bbs.cloud.admin.activity.service.ActivityManage;
import com.bbs.cloud.admin.activity.service.ActivityService;
import com.bbs.cloud.admin.common.enums.activity.ActivityStatusEnum;
import com.bbs.cloud.admin.common.enums.activity.LuckyBagStatusEnum;
import com.bbs.cloud.admin.common.error.CommonExceptionEnum;
import com.bbs.cloud.admin.common.feigh.client.ServiceFeighClient;
import com.bbs.cloud.admin.common.feigh.fallback.ServiceFeighClientFallback;
import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.common.util.CommonUtil;
import com.bbs.cloud.admin.common.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName LuckyBagActivityManage
 * @Description 添加描述
 * @Author Suguo
 * @LastChangeDate 2024/10/21 下午8:08
 */

public class LuckyBagActivityManage implements ActivityManage {
    final static Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private ServiceFeighClient serviceFeighClient;

    @Override
    public HttpResult createActivity(CreateActivityParam param) {
        logger.info("开始创建福袋活动, 请求参数:{}", JsonUtils.objectToJson(param));
        //福袋的数量
        Integer amount = param.getAmount();
        if(ObjectUtils.isEmpty(amount)) {
            logger.info("开始创建福袋活动, 福袋数量为空, 请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.LUCKY_BAG_ACTIVITY_AMOUNT_IS_NOT_NULL);
        }

        if(amount < ActivityContant.DEFAULT_LUCKY_BAG_ACTIVITY_MIN_AMOUNT) {
            logger.info("开始创建福袋活动, 福袋数量小于1, 请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.LUCKY_BAG_ACTIVITY_AMOUNT_LESS_THAN_ONE);
        }
        //查礼物
        HttpResult<Integer> result = serviceFeighClient.queryServiceGiftTotal();
        if(result == null || !CommonExceptionEnum.SUCCESS.getCode().equals(result.getCode()) || result.getData() == null) {
            logger.info("开始创建福袋活动, 远程调用, 获取服务组件礼物总数量失败, 请求参数:{}, result:{}", JsonUtils.objectToJson(param), JsonUtils.objectToJson(result));
            return HttpResult.generateHttpResult(ActivityException.LUCKY_BAG_ACTIVITY_SERVICE_GIFT_AMOUNT_FAIL);
        }
        Integer total = result.getData();
        if(total < amount) {
            logger.info("开始创建福袋活动, 远程调用, 获取服务组件礼物总数量不足, 请求参数:{}, result:{}", JsonUtils.objectToJson(param), JsonUtils.objectToJson(result));
            return HttpResult.generateHttpResult(ActivityException.LUCKY_BAG_ACTIVITY_SERVICE_GIFT_AMOUNT_NOT_MEET);
        }
        ActivityDTO activityDTO =new ActivityDTO();
        activityDTO.setId(CommonUtil.createUUID());
        activityDTO.setName(param.getName());
        activityDTO.setContent(param.getContent());
        activityDTO.setActivityType(param.getActivityType());
        activityDTO.setAmount(amount);
        activityDTO.setStatus(ActivityStatusEnum.INITIAL.getStatus());
        activityDTO.setCreateDate(new Date());
        activityDTO.setUpdateDate(new Date());
        activityMapper.insertActivityDTO(activityDTO);
        packLuckyBag(amount);
        return null;
    }
    /**
     * 生成1-10之间的随机数
     * @return
     */
    private static Integer randomGiftType() {
        int min = 1;
        int max = 10;
        int randomNum = (int)(Math.random() * (max - min + 1)) + min;
        return Integer.valueOf(randomNum);
    }

    private void packLuckyBag(Integer amount) {
        List<LuckyBagDTO> luckyBagDTOList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            //包装福袋
            LuckyBagDTO luckyBagDTO = new LuckyBagDTO();
            luckyBagDTO.setId(CommonUtil.createUUID());
            //luckyBagDTO.setActivityId(activityId);
            luckyBagDTO.setStatus(LuckyBagStatusEnum.NORMAL.getStatus());
            luckyBagDTO.setGiftType(randomGiftType());
            luckyBagDTOList.add(luckyBagDTO);
        }
    }

    @Override
    public HttpResult startActivity(OperatorActivityParam param) {
        return null;
    }

    @Override
    public HttpResult endActivity(OperatorActivityParam param) {
        return null;
    }

    @Override
    public Integer getActivityType() {
        return 0;
    }
}

