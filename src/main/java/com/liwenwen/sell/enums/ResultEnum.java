package com.liwenwen.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PARAM_ERROR(1,"参数不正确"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不足"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(13,"详情订单不存在"),
    ORDER_DELETE_ERROR(14,"订单状态不正确"),
    ORDER_DELETE_ERROR2(15,"更新失败"),
    ORDER_DELETE_ERROR3(16,"订单中无商品详情"),
    ORDER_PAY_STATUS_ERROR(17,"订单支付状态不正确"),
    CAR_ISEMPTY(18,"购物车为空"),
    ORDER_FIND_ERROR(19,"订单openid与传入参数不同"),
    WECHAT_MP_ERROR(20,"微信公众号相关错误"),
    WECHAT_PAY_ERROR(21,"微信支付异步通知金额校验不通过"),
    WECHAT_OPEN_LOGIN_ERROR(22,"用户不存在"),
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code=code;
        this.msg=msg;
    }
}
