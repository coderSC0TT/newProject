package com.bbs.cloud.admin.service.message.dto;

import java.util.Date;

/**
 * @ClassName OrderMessageParam
 * @Description 添加描述
 * @Author Suguo
 * @LastChangeDate 2024/10/19 下午1:31
 */

public class OrderMessageDTO {
    private String id;

    /**
     * {@link com.bbs.cloud.admin.service.enums.ServiceTypeEnum}
     */
    private Integer serviceType;

    private String ServiceName;

    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

