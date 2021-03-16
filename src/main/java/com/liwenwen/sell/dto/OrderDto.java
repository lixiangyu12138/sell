package com.liwenwen.sell.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liwenwen.sell.pojo.OrderDetail;
import com.liwenwen.sell.utils.serializer.DateFormat;
import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * dto:数据传输对象，专门用来在各个层之间传输
 * 订单信息
 *
 */

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
    private BigDecimal orderAmount;
    //订单状态  已下单
    private Integer orderStatus;
    //支付状态  未支付
    private Integer payStatus;
    //创建时间
    //@JsonSerialize(using = DateFormat.class)
    private Date createTime;
    //更新时间
   // @JsonSerialize(using = DateFormat.class)
    private Date updateTime;
    //订单详情
    private List<OrderDetail> orderDetails;
}
