package com.liwenwen.sell.controller;

import com.google.gson.Gson;
import com.liwenwen.sell.dto.OrderDto;
import com.liwenwen.sell.enums.ResultEnum;
import com.liwenwen.sell.exception.SellException;
import com.liwenwen.sell.service.OrderService;
import com.liwenwen.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;


    @GetMapping("/create")
    public String pay(@RequestParam("orderId") String orderId,
                            @RequestParam("returnUrl") String returnUrl,
                            Map<String,Object> map){
        //查询订单
        OrderDto orderDto = orderService.findOne(orderId);
        log.info("【微信支付】returnUrl={}",returnUrl);
        if(orderDto==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //发起支付
        //log.info("【微信支付】returnUrl={}",returnUrl);
        map.put("Response",payService.create(orderDto));
        map.put("returnUrl",returnUrl);
        return "pay/create";
    }


    @GetMapping("/notify")
    @ResponseBody
    public String  notifyData(String notifyData){
        log.info("【微信支付】#################异步通知#######################################");
        Gson gson = new Gson();
        String data = gson.fromJson(notifyData,String.class);
        log.info("【微信支付controller】notifyData={}",notifyData);
        payService.notify(data);
        log.info("【微信支付】#################成功#######################################");
        return "success";
    }

}
