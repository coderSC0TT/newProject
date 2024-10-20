package com.bbs.cloud.admin.activity.controller;

import com.bbs.cloud.admin.activity.params.CreateActivityParam;
import com.bbs.cloud.admin.activity.params.OperatorActivityParam;
import com.bbs.cloud.admin.activity.service.ActivityService;
import com.bbs.cloud.admin.common.result.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ActivityController
 * @Description 添加描述
 * @Author Suguo
 * @LastChangeDate 2024/10/20 下午12:02
 */
@RestController
@RequestMapping("activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping("/create")
    public HttpResult createActivity(@RequestBody CreateActivityParam param){
        return activityService.createActivity(param);
    }

    @PostMapping("/start")
    public HttpResult startActivity(@RequestBody OperatorActivityParam param){
        return activityService.startActivity(param);
    }

    @PostMapping("/end")
    public HttpResult endActivity(@RequestBody OperatorActivityParam param){
        return activityService.endActivity(param);
    }

}

