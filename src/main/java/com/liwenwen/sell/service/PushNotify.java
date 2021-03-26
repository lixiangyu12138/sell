package com.liwenwen.sell.service;

import com.liwenwen.sell.dto.OrderDto;

/**
 * 消息托送服务
 */
public interface  PushNotify {
    //订单状态变更通知
     void orderStatus(OrderDto orderDto);
}
