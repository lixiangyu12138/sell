package com.liwenwen.sell.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lly835.bestpay.model.PayResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "pay_info")
@Data
@Getter
@Setter

public class PayInfo {
    @Id
    private String prePayParams;
    private String signType;
    private Double orderAmount;
    private String orderId;

    /**
     * payResponse={
     *   "prePayParams": "en0qbqxhNwt3BHQ-lkHlY",
     *   "signType": "微信订单",
     *   "orderAmount": 12.9,
     *   "orderId": "1615449067202528005"
     * }
     */


    public PayInfo() {
    }

    public PayInfo(PayResponse payResponse) {
        this.prePayParams = payResponse.getPrePayParams();

        this.signType = payResponse.getSignType();

        this.orderAmount = payResponse.getOrderAmount();
        this.orderId = payResponse.getOrderId();
    }

    public PayInfo(String prePayParams, String signType, Double orderAmount, String orderId) {
        this.prePayParams = prePayParams;
        this.signType = signType;
        this.orderAmount = orderAmount;
        this.orderId = orderId;
    }
}

