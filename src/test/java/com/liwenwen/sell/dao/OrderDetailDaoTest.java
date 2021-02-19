package com.liwenwen.sell.dao;

import com.liwenwen.sell.pojo.OrderDetail;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class OrderDetailDaoTest {
@Autowired
private OrderDetailDao orderDetailDao;
    @Test
    void save() {
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDetailId("123457");
        orderDetail.setProductName("南瓜粥");
        orderDetail.setOrderId("123456");
        orderDetail.setProductId("123456");
        orderDetail.setProductPrice(new BigDecimal(1.2));
        orderDetail.setProductQuantity(5);
        orderDetail.setProductIcon("xxx.jpg");
        OrderDetail save = orderDetailDao.save(orderDetail);
        Assert.assertNotNull(save);
    }
    @Test
    void findByOrderId() {
        List<OrderDetail> byOrderId = orderDetailDao.findByOrderId("123456");
        Assert.assertNotEquals(0,byOrderId.size());
    }
}