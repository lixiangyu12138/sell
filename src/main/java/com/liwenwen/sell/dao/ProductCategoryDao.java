package com.liwenwen.sell.dao;

import com.liwenwen.sell.pojo.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer> {
        List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
