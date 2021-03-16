package com.liwenwen.sell.dao;

import com.liwenwen.sell.pojo.PayInfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class PayInfoDaoTest {
    @Autowired
    private PayInfoDao payInfoDao;

    @Test
    void findByPrePayParams() {
        String notifyDate = "9OMZFcYYi9BvgWH-UuRmx";
        PayInfo byPrePayParams = payInfoDao.findByPrePayParams(notifyDate);
        System.out.println(byPrePayParams);

    }
    @Test
    void getOne(){
        String notifyDate = "9OMZFcYYi9BvgWH-UuRmx";
        PayInfo one = payInfoDao.getOne(notifyDate);
        System.out.println(one);
    }
}