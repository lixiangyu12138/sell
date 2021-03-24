package com.liwenwen.sell.service.Impl;

import com.liwenwen.sell.dto.OrderDto;
import com.liwenwen.sell.enums.OrderStatusEnum;
import com.liwenwen.sell.enums.PayStatusEnum;
import com.liwenwen.sell.pojo.OrderDetail;
import com.liwenwen.sell.pojo.OrderMaster;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;
    private final String BUYER_OPENID= "1101110";
    private final  String ORDER_ID= "1614696238824706722";

    @Test
    void create() {

        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName("李文文");

        orderDto.setBuyerAddress("666");
        orderDto.setBuyerOpenid(BUYER_OPENID);
        orderDto.setBuyerPhone("123456789");
        List<OrderDetail> orderDetails= new ArrayList<>();
        OrderDetail o1= new OrderDetail();
        o1.setProductId("123456");
        o1.setProductQuantity(1);
        orderDetails.add(o1);
        OrderDetail o2= new OrderDetail();
        o2.setProductId("123457");
        o2.setProductQuantity(2);
        orderDetails.add(o2);
        orderDto.setOrderDetails(orderDetails);
        OrderDto result = orderService.create(orderDto);
        log.info("【创建订单】result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    void findOne() {
        OrderDto result = orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】 result={}",result);
        Assert.assertEquals(ORDER_ID,result.getOrderId());
    }

    @Test
    void findList() {
        PageRequest request =PageRequest.of(0,2);
        Page<OrderDto> orderDtos =orderService.findList(BUYER_OPENID,request);
        log.info("【查询订单列表】 orderDtos={}",orderDtos);
        Assert.assertNotEquals(0,orderDtos.getTotalElements());

    }

    @Test
    void delete() {
        OrderDto orderDto = orderService.findOne(ORDER_ID);
        OrderDto delete = orderService.delete(orderDto);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),delete.getOrderStatus());
    }

    @Test
    void finish() {
        OrderDto orderDto = orderService.findOne(ORDER_ID);
        OrderDto delete = orderService.finish(orderDto);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),delete.getOrderStatus());
    }

    @Test
    void pay() {
        OrderDto orderDto = orderService.findOne(ORDER_ID);
        OrderDto pay = orderService.pay(orderDto);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),pay.getPayStatus());
    }
    @Test
    void findAll(){
        PageRequest request =PageRequest.of(0,10);
        Page<OrderDto> all = orderService.findAll(request);
        Assert.assertNotEquals(0,all.getTotalElements());

    }
}