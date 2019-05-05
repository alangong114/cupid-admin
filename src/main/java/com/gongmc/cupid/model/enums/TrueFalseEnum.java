package com.gongmc.cupid.model.enums;

/**
 * <pre>
 *     true or false enum
 * </pre>
 *
 *
 * @date : 2018/7/16
 */
public enum TrueFalseEnum {

    /**
     * 真
     */
    TRUE("true"),

    /**
     * 假
     */
    FALSE("false");

    private String desc;

    TrueFalseEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
