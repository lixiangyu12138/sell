package com.liwenwen.sell.service;

import com.liwenwen.sell.dto.CarDto;
import com.liwenwen.sell.pojo.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);

    Page<ProductInfo> findAll(Pageable pageable);

    List<ProductInfo> findUpAll();

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStoke(List<CarDto> carDtoList);

    //减库存
    void decreaseStoke(List<CarDto> carDtoList);
}
