package com.liwenwen.sell.pojo;




import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.liwenwen.sell.enums.ProductStatusEnum;

import com.liwenwen.sell.utils.DateFormatUtil;
import com.liwenwen.sell.utils.serializer.DateFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Proxy(lazy = false)
@Data
@DynamicUpdate
@DynamicInsert
public class ProductInfo {
    @Id
    private String productId;
    /*名字*/
    private String productName;
    /*单价*/
    private BigDecimal productPrice;
    /*库存*/
    private Integer productStock;
    /*描述*/
    private String productDescription;
    /*图片*/
    private String productIcon;
    /*状态 0正常,1下架*/
    private Integer productStatus = ProductStatusEnum.UP.getCode();
    /*类目编号*/
    private Integer categoryType;
    @JsonSerialize(using =DateFormat.class )
    private Date createTime;
    private Date updateTime;
    public static String getOrderStatusEnum(Integer n){
        switch (n){
            case 0: return "下架";
            case 1:return "上架";
            default:
                return null;
        }

    }

    /*构造函数*/
    public ProductInfo() {
    }

    public ProductInfo(String productId, String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Integer productStatus, Integer categoryType) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.productStatus = productStatus;
        this.categoryType = categoryType;
    }
}
