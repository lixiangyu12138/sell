package com.liwenwen.sell.dto;

import lombok.Data;

/**
 * 购物车
 */
@Data
public class CarDto {
    //商品id
    private String productId;
    //商品数量
    private Integer productCount;

    public CarDto(String productId, Integer productCount) {
        this.productId = productId;
        this.productCount = productCount;
    }
}
