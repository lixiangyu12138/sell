package com.liwenwen.sell.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 订单详情表
 */

@Entity
@Data
public class OrderDetail {
    @Id
    //订单id
    private String detailId;
    //商品id
    private String orderId;
    //商品id
    private String productId;
    //商品名称
    private String productName;
    //商品单价
    private BigDecimal productPrice;
    //商品数量
    private Integer productQuantity;
    //商品图片
    private String  productIcon;
//    //创建时间
//    private Date cresteTime;
//    //更新时间
//    private Date updateTime;
}
