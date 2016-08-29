package com.picc.chexian.core.enums;

public enum FactStatus {
	
	SUBMIT("待审批"),
	AUDIT("已审批"),
	PENDING("待补充"),
	;
    private final String key;

    private FactStatus(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
