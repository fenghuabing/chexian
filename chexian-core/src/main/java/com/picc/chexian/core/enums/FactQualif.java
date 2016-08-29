package com.picc.chexian.core.enums;

public enum FactQualif {
	
	FIRST("一类维修资质"),
	SECOND("二类维修资质"),
	THIRD("三类维修资质"),
	;
    private final String key;

    private FactQualif(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
