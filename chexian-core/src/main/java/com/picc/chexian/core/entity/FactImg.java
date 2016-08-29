package com.picc.chexian.core.entity;

import java.util.Date;

import lombok.Data;

@Data
public class FactImg {
    private Integer id;

    private Integer factId;

    private String path;

    private String type;

    private Date createTime;

    private Integer status;

    private Date approveTime;

    private Integer approver;

    private Date offlineTime;

    private Integer offliner;

}