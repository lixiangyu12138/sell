package com.liwenwen.sell.controller;

import com.liwenwen.sell.pojo.SellerInfo;
import com.liwenwen.sell.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerInfoController {
    @Autowired
    private SellerService sellerService;
    public String login(@RequestParam("openId") String openId,
                        Map<Object,Object> map){
        SellerInfo sellerInfoByOpenId = new SellerInfo();

        try{
             sellerInfoByOpenId = sellerService.findSellerInfoByOpenId(openId);
             map.put("msg","登录成功");
             return "common/success";
        }catch (Exception e){
            log.info("【卖家端登录】 登录错误");
            map.put("msg",e.getMessage());
            return "common/error";

        }
    }
}
