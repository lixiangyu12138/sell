package com.liwenwen.sell.enums;

import lombok.Getter;

/**
 * 支付状态
 */

@Getter
public enum PayStatusEnum {
    WAIT(0,"待付款"),
    SUCCESS(1,"支付成功"),
    ;
    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
