package com.liwenwen.sell.service.Impl;

import com.liwenwen.sell.pojo.SellerInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class SellerServiceImplTest {
    @Autowired
    private SellerServiceImpl sellerService;

    @Test
    void findSellerInfoByOpenId() {
        SellerInfo abc = sellerService.findSellerInfoByOpenId("666");
        Assert.assertNotNull(abc);
    }
}