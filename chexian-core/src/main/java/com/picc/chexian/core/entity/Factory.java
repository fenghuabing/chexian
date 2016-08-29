package com.picc.chexian.core.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.picc.chexian.core.enums.FactQualif;
import com.picc.chexian.core.enums.FactStatus;
import com.picc.chexian.core.enums.FactType;

@Data
public class Factory {
    private Integer id;

    private String name;
    
    private String province;
    private String city;
    private String district;

    private String regAddress;

    private String serviceAddress;

    private String legalPerson;

    private FactQualif qualif;

    private Double lon;

    private Double lat;

    private FactType category;

    private Float regFund;

    private String creditCode;

    private String businessLisenceCode;

    private String orgCode;

    private String businessScope;

    private String expire;

    private String imgBusinessLisence;

    private String imgOrgCode;

    private String imgBusinessCert;

    private Date createTime;

    private Integer creator;

    private FactStatus status;

    private Date approveTime;

    private Integer approver;

    private Date offlineTime;

    private Integer offliner;
    
    private String comment;
    
    private String imgFactory;
    private List<String> imgFactoryList;

}