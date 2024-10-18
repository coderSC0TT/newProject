package com.bbs.cloud.admin.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestEndPoint
 * @Description 添加描述
 * @Author Suguo
 * @LastChangeDate 2024/10/18 下午10:36
 */

@RestController
@RequestMapping("endpoint")
public class TestEndpoint {

    @GetMapping("/feigh1")
    public String testFeigh() {
        System.out.println("进入了这个feigh");
        throw new RuntimeException("异常................");
    }

}

