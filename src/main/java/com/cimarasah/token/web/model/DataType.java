package com.cimarasah.token.web.model;

import org.springframework.util.StringUtils;

public enum DataType {
    STAMP("stamp");

    public final String code;

    DataType(String code) {
        this.code = code;
    }

    public static DataType fromString(String type) {
        if(!StringUtils.isEmpty(type)) {
            for (DataType dataType : values()) {
                if(dataType.code.equalsIgnoreCase(type)) {
                    return dataType;
                }
            }
        }
        return null;
    }
}
