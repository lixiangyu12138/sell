package com.liwenwen.sell.conterver;

import com.liwenwen.sell.dto.OrderDto;
import com.liwenwen.sell.pojo.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMasterToOrderDTOConverter {
    public static OrderDto convert(OrderMaster orderMaster){
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        return orderDto;
    }
    public static List<OrderDto> convwet(List<OrderMaster> orderMasters){
        return orderMasters.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
