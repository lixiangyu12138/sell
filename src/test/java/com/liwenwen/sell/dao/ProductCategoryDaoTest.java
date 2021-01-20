package com.liwenwen.sell.dao;

import com.liwenwen.sell.pojo.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Test
    public void findOneTest(){
        ProductCategory productcategory = productCategoryDao.getOne(1);
        System.out.println(productcategory);

    }
    @Test
    @Transactional  //测试回滚测试数据不会污染数据库
    public void saveTest(){
        ProductCategory productCategory1 = new ProductCategory("饮品榜",5);
        //productCategory1.setCategoryId(2);

       ProductCategory result= productCategoryDao.save(productCategory1);
        Assert.assertNotNull(result); //断言判断返回值是否为空

    }
    @Test
    public void updateTest(){
        ProductCategory productCategory1 = productCategoryDao.getOne(2);
        productCategory1.setCategoryType(3);
        productCategoryDao.save(productCategory1);

    }
    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> result=productCategoryDao.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());

    }

}