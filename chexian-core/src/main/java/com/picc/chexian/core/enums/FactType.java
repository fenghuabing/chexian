package com.picc.chexian.core.enums;

public enum FactType {
	
	LTD("有限责任公司"),
	PRI("私营企业");
    private final String key;

    private FactType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
