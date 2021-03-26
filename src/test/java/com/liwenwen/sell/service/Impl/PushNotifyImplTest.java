package com.liwenwen.sell.service.Impl;

import com.liwenwen.sell.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class PushNotifyImplTest {
    @Autowired
    private PushNotifyImpl pushNotify;
    @Autowired
    private OrderServiceImpl orderService;

    @Test
    void orderStatus() {
        OrderDto orderDto = orderService.findOne("1615824209798518911");
        pushNotify .orderStatus(orderDto);
    }
}