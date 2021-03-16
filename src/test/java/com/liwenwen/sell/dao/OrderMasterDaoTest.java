package com.liwenwen.sell.dao;

import com.liwenwen.sell.pojo.OrderMaster;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class OrderMasterDaoTest {
    @Autowired
    private  OrderMasterDao orderMasterDao;
    private  final String OPENID="10010";

    @Test
    void save(){
        OrderMaster master= new OrderMaster();
        master.setOrderId("123457");
        master.setBuyerName("李文文");
        master.setBuyerPhone("17114120208");
        master.setBuyerAddress("慕课网");
        master.setBuyerOpenid("10010");
        master.setOrderAmount(new BigDecimal(2.5));
        OrderMaster save = orderMasterDao.save(master);
        Assert.assertNotNull(save);
    }
    @Test
    void findByBuyerOpenid() throws Exception {
        //size 一页显示的条数  page第几页
        PageRequest request =PageRequest.of(1,1);
        Page<OrderMaster> byBuyerOpenid = orderMasterDao.findByBuyerOpenid(OPENID, request);
        Assert.assertNotEquals(0,byBuyerOpenid.getTotalElements());
       // System.out.println(byBuyerOpenid.getTotalElements());
    }
}