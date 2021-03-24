package com.liwenwen.sell.dao;

import com.liwenwen.sell.pojo.SellerInfo;
import com.liwenwen.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class SellerInfoDaoTest {
    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Test
    void findByOpenId() {
        sellerInfoDao.findByOpenId("abc");
    }
    @Test
    void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setPassword("123456");
        sellerInfo.setUsername("admin");
        sellerInfo.setOpenId("abc");
        SellerInfo save = sellerInfoDao.save(sellerInfo);
        Assert.assertNotNull(save);
    }
}