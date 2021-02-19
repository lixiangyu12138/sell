package com.liwenwen.sell.service;

import com.liwenwen.sell.dto.OrderDto;
import com.liwenwen.sell.pojo.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    //创建订单
    OrderDto create(OrderDto orderDto);
    //查询
    OrderDto findOne(String OrderId);
    Page<OrderDto> findList(String buyerOpenid, Pageable pageable);

    //取消订单
    OrderDto delete(OrderDto orderDto);

    //完结订单
    OrderDto finish(OrderDto orderDto);

    //支付订单;
    OrderDto pay(OrderDto orderDto);
}
