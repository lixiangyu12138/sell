package com.liwenwen.sell.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 卖家信息
 */
@Entity

@Data
@DynamicUpdate
@DynamicInsert
public class SellerInfo {
    @Id
    private String sellerId;
    private  String username;
    private String password;
    private String openId;
}
