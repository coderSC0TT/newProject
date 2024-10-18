package com.bbs.cloud.admin.controller;

import com.bbs.cloud.admin.common.result.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @GetMapping("hello")
    public HttpResult hello() {
        logger.info("进入hello接口");
        return new HttpResult("hello");
    }
}

