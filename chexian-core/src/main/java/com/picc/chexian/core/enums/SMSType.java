package com.picc.chexian.core.enums;

public enum SMSType{

    /**
     * 用户账号绑定手机
     */
    CONFIRM_AUTHENTICATE_MOBILE("confirm.picc.authenticate.mobile");


    private final String key;

    private SMSType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}