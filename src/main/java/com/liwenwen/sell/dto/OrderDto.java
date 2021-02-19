package com.liwenwen.sell.dto;

import com.liwenwen.sell.enums.OrderStatusEnum;
import com.liwenwen.sell.enums.PayStatusEnum;
import com.liwenwen.sell.pojo.OrderDetail;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
public class OrderDto {

    //订单id
    private String orderId;
    //买家名字
    private String buyerName;
    //买家手机号
    private String buyerPhone;
    //买家地址
    private String buyerAddress;
    //买家微信id
    private String buyerOpenid;
    //订单总金额
    private BigDecimal buyerAmount;
    //订单状态  已下单
    private Integer orderStatus= OrderStatusEnum.NEW.getCode();
    //支付状态  未支付
    private Integer payStatus= PayStatusEnum.WAIT.getCode();
    //
    private List<OrderDetail> orderDetails;
}
