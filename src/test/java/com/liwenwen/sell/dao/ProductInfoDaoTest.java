package com.liwenwen.sell.dao;

import com.liwenwen.sell.pojo.ProductInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class ProductInfoDaoTest {
    @Autowired
    private ProductInfoDao productInfoDao;
    @Test
    void saveTest() {
        ProductInfo productInfo= new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("666");
        productInfo.setProductIcon("xxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        System.out.println(productInfo);
        ProductInfo save = productInfoDao.save(productInfo);
        Assert.assertNotNull(save);
    }


    @Test
    void findByProductStatus() {
        List<ProductInfo> productInfos = productInfoDao.findByProductStatus(0);
        Assert.assertNotEquals(0,productInfos.size());


    }
}