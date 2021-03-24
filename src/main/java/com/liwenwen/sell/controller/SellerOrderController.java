package com.liwenwen.sell.controller;

import com.liwenwen.sell.dto.OrderDto;
import com.liwenwen.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/list")
    public String findAll(@RequestParam(value="page",defaultValue="1") Integer page,
                          @RequestParam(value="size",defaultValue="10")Integer size,
                          Map<String,Object> map){
        PageRequest pageRequest = PageRequest.of(page-1,size);
        Page<OrderDto> all = orderService.findAll(pageRequest);
        map.put("all",all);
        map.put("page",page);
        return "order/list";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("orderId") String orderId,
                         Map<String,Object> map)  {
        try{
            map.put("url","/sell/seller/order/list");
            OrderDto one = orderService.findOne(orderId);
             orderService.delete(one);
            map.put("msg","取消成功!");

            return "common/success";
        }catch (Exception e){
            log.info("【订单删除】 查询不到订单");
            map.put("msg",e.getMessage());

            return "common/error";
        }
    }
    @GetMapping("/detail")
     public String detail(@RequestParam("orderId")  String orderId,
                          Map<String,Object> map){
        OrderDto one = orderService.findOne(orderId);
        map.put("one",one);
        return "order/detail";
     }
    @GetMapping("/finished")
    public String finished(@RequestParam("orderId") String orderId,
                         Map<String,Object> map)  {

        try{
            map.put("url","/sell/seller/order/list");
            OrderDto one = orderService.findOne(orderId);
            orderService.finish(one);
            map.put("msg","完结成功!");

            return "common/success";
        }catch (Exception e){
            log.info("【订单完结】 查询不到订单");
            map.put("msg",e.getMessage());

            return "common/error";
        }
    }

}
