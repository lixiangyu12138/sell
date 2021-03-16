package com.liwenwen.sell.service.Impl;

import com.liwenwen.sell.dto.OrderDto;
import com.liwenwen.sell.service.OrderService;
import com.liwenwen.sell.service.PayService;
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
class PayServiceImplTest {
    @Autowired
    private PayService payService;
    @Autowired
    private OrderService orderService;

    @Test
    void create() throws Exception {
        OrderDto orderDto= orderService.findOne("1614698053034815337");
        payService.create(orderDto);
    }
}