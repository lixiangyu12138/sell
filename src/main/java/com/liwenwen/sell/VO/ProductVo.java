package com.liwenwen.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品（包含类目）
 */
@Data
public class ProductVo {
    @JsonProperty("name")  //以name属性返回给前端
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoVoList;

    @Override
    public String toString() {
        return "ProductVo{" +
                "categoryName='" + categoryName + '\'' +
                ", categoryType=" + categoryType +
                ", productInfoVoList=" + productInfoVoList +
                '}';
    }
}
