package com.liwenwen.sell.conterver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liwenwen.sell.dto.OrderDto;
import com.liwenwen.sell.enums.ResultEnum;
import com.liwenwen.sell.exception.SellException;
import com.liwenwen.sell.form.OrderForm;
import com.liwenwen.sell.pojo.OrderDetail;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class OrderFormTOOrderDtoConverter {
    public static OrderDto convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDto orderDto = new OrderDto();

        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerOpenid(orderForm.getOpenid());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerAddress(orderForm.getAddress());
        List<OrderDetail> orderDetails = new ArrayList<>();
        try {
           orderDetails= gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        }catch (Exception e){
            log.error("【对象转换】错误 String={}", orderForm.getItems() );
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDto.setOrderDetails(orderDetails);


        return orderDto;


    }
}
