package com.liwenwen.sell.service;

import com.liwenwen.sell.dto.OrderDto;
import com.lly835.bestpay.model.PayResponse;

import java.util.Map;

public interface PayService {
    PayResponse create(OrderDto orderDto);
    PayResponse notify(String String);
}
