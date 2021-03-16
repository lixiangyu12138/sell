package com.liwenwen.sell.service;

import com.liwenwen.sell.dto.CarDto;
import com.liwenwen.sell.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    //创建订单
    OrderDto create(OrderDto orderDto) throws Exception;
    //查询
    OrderDto findOne(String OrderId);
    //查询订单列表
    Page<OrderDto> findList(String buyerOpenid, Pageable pageable);

    //取消订单
    OrderDto delete(OrderDto orderDto);

    //完结订单
    OrderDto finish(OrderDto orderDto);

    //支付订单;
    OrderDto pay(OrderDto orderDto);
    //查询订单所有
    Page<OrderDto> findAll( Pageable pageable);


}
