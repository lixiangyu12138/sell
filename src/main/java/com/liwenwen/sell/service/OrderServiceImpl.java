package com.liwenwen.sell.service;

import com.liwenwen.sell.dto.OrderDto;
import com.liwenwen.sell.pojo.OrderDetail;
import com.liwenwen.sell.pojo.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private ProductService productService;

    @Override
    public OrderDto create(OrderDto orderDto) {
        //查询商品信息（productInf 表）
      for (OrderDetail orderDetail : orderDto.getOrderDetails()){
          ProductInfo productInfo=productService.findOne(orderDetail.getProductId());
      }

        //计算总价
        //写入数据库
        //扣库存
        return null;
    }

    @Override
    public OrderDto findOne(String OrderId) {
        return null;
    }

    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDto delete(OrderDto orderDto) {
        return null;
    }

    @Override
    public OrderDto finish(OrderDto orderDto) {
        return null;
    }

    @Override
    public OrderDto pay(OrderDto orderDto) {
        return null;
    }
}
