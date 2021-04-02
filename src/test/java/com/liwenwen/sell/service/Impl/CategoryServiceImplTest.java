package com.liwenwen.sell.service.Impl;

import com.liwenwen.sell.pojo.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceImplTest {
@Autowired
private CategoryServiceImpl categoryService;
    @Test
    void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId() );
    }

    @Test
    void findAll() {
        List<ProductCategory> productCategories= categoryService.findAll();
        Assert.assertNotEquals(0,productCategories.size() );

    }

    @Test
    void findByCatedoryTypeIn() {
        List<ProductCategory> findByCategoryTypeIn = categoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4));
        Assert.assertNotEquals(0,findByCategoryTypeIn.size());
    }

    @Test
    void save() {
        ProductCategory productCategory = new ProductCategory("男生专享", 5);
        ProductCategory save = categoryService.save(productCategory);
        Assert.assertNotNull(save);
    }
}