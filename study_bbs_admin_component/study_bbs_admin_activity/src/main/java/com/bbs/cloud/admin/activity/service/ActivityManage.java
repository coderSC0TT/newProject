package com.bbs.cloud.admin.activity.service;

import com.bbs.cloud.admin.activity.params.CreateActivityParam;
import com.bbs.cloud.admin.activity.params.OperatorActivityParam;
import com.bbs.cloud.admin.common.result.HttpResult;

public interface ActivityManage {

    HttpResult createActivity(CreateActivityParam param);

    HttpResult startActivity(OperatorActivityParam param);

    HttpResult endActivity(OperatorActivityParam param);

    Integer getActivityType();
}
