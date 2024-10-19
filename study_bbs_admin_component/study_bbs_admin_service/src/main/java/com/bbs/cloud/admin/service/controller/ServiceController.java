package com.bbs.cloud.admin.service.controller;

import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.service.param.OrderMessageParam;
import com.bbs.cloud.admin.service.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ServiceController
 * @Description 添加描述
 * @Author Suguo
 * @LastChangeDate 2024/10/19 下午1:29
 */
@RestController
@RequestMapping("service")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @PostMapping("/send/message")
    public HttpResult sendMessage(@RequestBody OrderMessageParam param){
        return serviceService.sendMessage(param);
    }

    @PostMapping("/query")
    public HttpResult queryService(){
        return null;
    }
}

