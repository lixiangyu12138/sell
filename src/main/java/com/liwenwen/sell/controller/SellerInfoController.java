package com.liwenwen.sell.controller;

import ch.qos.logback.core.util.TimeUtil;
import com.liwenwen.sell.pojo.SellerInfo;
import com.liwenwen.sell.service.SellerService;
import com.liwenwen.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerInfoController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    int i = 0;
    //private String token = UUID.randomUUID().toString();
    private String status = "off";
    Cookie cookie= null;
    @GetMapping("/login")
    public String login(@RequestParam("openId") String openId,
                        Map<Object,Object> map){

        try{
            //openId和数据库中的openId做对比
            SellerInfo  sellerInfoByOpenId = sellerService.findSellerInfoByOpenId(openId);
             map.put("msg","登录成功");
             map.put("url","/sell/seller/order/list");
        }catch (Exception e){
            log.info("【卖家端登录】 登录失败 e = {}",e);
            map.put("msg",e.getMessage());
            map.put("url","/sell/wechat/qrAuthorize");
            return "common/error";

        }
        //设置 token 至 redis
        String token = UUID.randomUUID().toString();
        Integer expire= 7200;
        redisTemplate.opsForValue().set("token_"+token,openId,expire, TimeUnit.SECONDS);
        String s = redisTemplate.opsForValue().get("token_" + token);
        //System.out.println(s);

        return "redirect:/seller/success?token="+token;

    }
    @GetMapping("/success")
    @ResponseBody
    public String ajax(String token, HttpServletResponse response){
        if(token!=null&&"off".equals(status)){
        String s = "token_" + token;
        System.out.println(s);
        cookie = CookieUtil.set("token", s, 7200);
            status="on";
        }else if(token!=null&&"off".equals(status)){
            status ="refresh";
        }
        response.addCookie(cookie);
        System.out.println(status);
        return status;


    }
    @GetMapping("/logout")
    public String logout (HttpServletRequest request,HttpServletResponse response,Map<String,Object> map){
        //从cookie里查询
        Cookie cookie = CookieUtil.get(request, "token");
        if(cookie != null){
            //清除redis
            System.out.println("token_"+cookie.getValue());
            redisTemplate.delete(cookie.getValue());
            //redisTemplate.expire("token_"+cookie.getValue(),0,TimeUnit.SECONDS);
            //redisTemplate.opsForValue().set
            //清除cookie
            Cookie cookie1 = CookieUtil.set("token", null, 0);
            response.addCookie(cookie1);
        }
        map.put("url","/sell/seller/order/list");
        map.put("msg","登出成功");
        return "common/success";
    }
}
