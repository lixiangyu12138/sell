package com.liwenwen.sell.controller;

import com.google.gson.Gson;
import com.liwenwen.sell.dto.OrderDto;
import com.liwenwen.sell.enums.ResultEnum;
import com.liwenwen.sell.exception.SellException;
import com.liwenwen.sell.service.OrderService;
import com.liwenwen.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.rest.type.Post;
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

    /**
     * 用的Thymeleaf
     * @param orderId
     * @param returnUrl
     * @param map
     * @return
     */
    @GetMapping("/create")
    public String pay(@RequestParam("orderId") String orderId,
                            @RequestParam("returnUrl") String returnUrl,
                            Map<String,Object> map){
        //查询订单
        OrderDto orderDto = orderService.findOne(orderId);
        //log.info("【微信支付】orderDto={}",orderDto);
        if(orderDto==null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //发起支付
        //log.info("【微信支付】returnUrl={}",returnUrl);
        map.put("Response",payService.create(orderDto));
        map.put("returnUrl",returnUrl);
        return "pay/create";

    }
    @PostMapping("/notify")
    @ResponseBody
    public String  notifyData(@RequestBody String notifyData){
        //System.out.println(notifyData);
        Gson gson = new Gson();
        String data = gson.fromJson(notifyData,String.class);
        PayResponse payResponse = payService.notify(data);
        //log.info("【微信支付】payResponse={}",payResponse);
        return "<xml>" +
                "<return_code>><![CDATA[SUCCESS]]></return_code>" +
                "<return_msg><![CDATA[OK]]></return_msg>" +
                "</xml>";

    }

}
