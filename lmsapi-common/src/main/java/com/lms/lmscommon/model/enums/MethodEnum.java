package com.lms.lmscommon.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MethodEnum {

    GET("GET", 0),
    POST("POST", 1),
    PUT("PUT",2),
    DELETE("DELETE",3);
    private final String text;

    private final Integer value;
    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }
    MethodEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
