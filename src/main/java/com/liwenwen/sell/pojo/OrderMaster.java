package com.liwenwen.sell.pojo;

import com.liwenwen.sell.enums.OrderStatusEnum;
import com.liwenwen.sell.enums.PayStatusEnum;
import com.mysql.cj.Constants;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表
 */

@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    @Id
    //订单id
    //@OneToOne(optional=true )
   // @JoinColumn(name="order_id ",insertable=false, updatable=false)

    @NotFound(action= NotFoundAction.IGNORE)
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
    private Integer orderStatus= OrderStatusEnum.NEW.getCode();
    //支付状态  未支付
    private Integer payStatus= PayStatusEnum.WAIT.getCode();
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
