package com.liwenwen.sell.service;

import com.liwenwen.sell.pojo.ProductCategory;

import java.util.List;

public interface CategoryService {
    ProductCategory findOne(Integer categoryId);
    List<ProductCategory> findAll();
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    ProductCategory save(ProductCategory productCategory);
    ProductCategory update(ProductCategory productCategory);




}
