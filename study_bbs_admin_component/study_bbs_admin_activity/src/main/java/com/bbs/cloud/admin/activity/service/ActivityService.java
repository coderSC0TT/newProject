package com.bbs.cloud.admin.activity.service;

import com.bbs.cloud.admin.activity.dto.ActivityDTO;
import com.bbs.cloud.admin.activity.exception.ActivityException;
import com.bbs.cloud.admin.activity.mapper.ActivityMapper;
import com.bbs.cloud.admin.activity.params.CreateActivityParam;
import com.bbs.cloud.admin.activity.params.OperatorActivityParam;
import com.bbs.cloud.admin.common.enums.activity.ActivityStatusEnum;
import com.bbs.cloud.admin.common.enums.activity.ActivityTypeEnum;
import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.common.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName ActivityService
 * @Description 添加描述
 * @Author Suguo
 * @LastChangeDate 2024/10/20 下午12:08
 */
@Service
public class ActivityService {
    final static Logger logger = LoggerFactory.getLogger(ActivityService.class);
    @Autowired
    private List<ActivityManage> activityManages;

    @Autowired
    private ActivityMapper activityMapper;
    public HttpResult createActivity(CreateActivityParam param) {
        logger.info("开始创建活动,请求参数:{}", JsonUtils.objectToJson(param));
        String name = param.getName();//所有活动公有的
        if(StringUtils.isEmpty(name)) {
            logger.info("开始创建活动, 活动名称为空, 请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_NAME_IS_NOT_EMTRY);
        }

        String content = param.getContent();
        if(StringUtils.isEmpty(content)) {
            logger.info("开始创建活动, 活动内容为空, 请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_CONTENT_IS_NOT_EMTRY);
        }
        Integer activityType = param.getActivityType();
        if(ActivityTypeEnum.getActivityTypeEnumMap().getOrDefault(activityType,  null) == null) {
            logger.info("开始创建活动, 活动类型不存在, 请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_TYPE_IS_NOT_EXIST);
        }
        ActivityDTO activityDTO=activityMapper.queryActivityByType(activityType, Arrays.asList(
                ActivityStatusEnum.INITIAL.getStatus(),
                ActivityStatusEnum.RUNNING.getStatus()
        ));
        if(activityDTO != null) {  //一个活动类型只有一个活动
            logger.info("开始创建活动, 该类型活动已存在, 请求参数:{}", JsonUtils.objectToJson(param));
            return HttpResult.generateHttpResult(ActivityException.ACTIVITY_TYPE_ENTITY_IS_EXIST);
        }

        return activityManages.stream()
                .filter(item->item.getActivityType().equals(activityType))
                .findFirst().get()
                .createActivity(param);
    }

    public HttpResult startActivity(OperatorActivityParam param) {

        return null;
    }

    public HttpResult endActivity(OperatorActivityParam param) {

        return null;
    }
}

