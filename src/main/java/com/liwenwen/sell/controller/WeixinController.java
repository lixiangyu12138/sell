package com.liwenwen.sell.controller;


import com.liwenwen.sell.service.Impl.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName WxConfigController
 * @Author liming
 * @Description //TODO
 * @Date 2020/6/7 10:54
 **/
@RestController
@RequestMapping("/wx")
@Slf4j
public class WeixinController {
    private final static Logger LOG = LoggerFactory.getLogger(WeixinController.class);
    /**
     * 接入微信服务
     * @param request
     * @param response
     */
    @Autowired
    private WeChatService wechatservice;

    @RequestMapping(value="/urlR",method= RequestMethod.GET)
    public void index(HttpServletRequest request, HttpServletResponse response){
        LOG.info("微信接入服务器");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String token = "111111";
        String echostr = request.getParameter("echostr");
        if (wechatservice.verifyInfo(signature, timestamp, nonce, token)) {
            LOG.info("echostr为:{}", echostr);
            if (echostr != null) {
                try {
                    response.getWriter().write(echostr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            LOG.info("signature为:{}", signature);
            LOG.info("timestamp为:{}", timestamp);
            LOG.info("nonce为:{}", nonce);
            LOG.info("token为:{}", token);
        }
    }
    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
        log.info("进入微信授权");
        log.info("code={}",code);
       // return redirect_uri;
      String url= "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxa765302b7924279e&secret=18ea55b358597865f5accdcb493bc928&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate=new RestTemplate();
        String response=restTemplate.getForObject(url,String.class);
        log.info("response={}",response);
    }

}