package com.liwenwen.sell.pojo;

import com.liwenwen.sell.enums.OrderStatusEnum;
import com.liwenwen.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    @Id
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
    //创建时间
    private Date cresteTime;
    //更新时间
    private Date updateTime;
}
