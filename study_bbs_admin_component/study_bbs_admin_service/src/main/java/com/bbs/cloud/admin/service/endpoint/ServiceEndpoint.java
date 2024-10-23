package com.bbs.cloud.admin.service.endpoint;

import com.bbs.cloud.admin.common.result.HttpResult;
import com.bbs.cloud.admin.service.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ServiceEndpoint
 * @Description 添加描述
 * @Author Suguo
 * @LastChangeDate 2024/10/21 下午8:20
 */

@RestController
@RequestMapping("service/endpoint")
public class ServiceEndpoint {
    @Autowired
    private ServiceService serviceService;

    @GetMapping("/gift/total/query")
    public HttpResult<Integer> queryServiceGiftTotal(){
        return serviceService.queryServiceGiftTotal();
    }

    @GetMapping("/gilt/list/query")
    public HttpResult<String> queryServiceGiftList() {
        return serviceService.queryServiceGiftList();
    }

    @PostMapping("/gilt/list/update")
    public HttpResult updateServiceGiftList(@RequestParam("data") String data) {
        return serviceService.updateServiceGiftList(data);
    }
}

