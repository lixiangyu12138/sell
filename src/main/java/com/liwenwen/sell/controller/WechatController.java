package com.liwenwen.sell.controller;

import com.liwenwen.sell.enums.ResultEnum;
import com.liwenwen.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.http.HttpType;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.BaseWxMpServiceImpl;
import me.chanjar.weixin.mp.api.impl.WxMpOAuth2ServiceImpl;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;


/**
 * 微信网页授权
 */

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {
    @Autowired
    private  WxMpService wxMpService;
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        log.info("进入authorize方法");

        //1.配置
        //2.调用方法
        String url="http://li.666600000.xyz/sell/wechat/userInfo";
        String redirectUrl= wxMpService.getOAuth2Service().buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        return  "redirect:"+redirectUrl;
    }
    @GetMapping("/userInfo")
    public String userinfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl){
        log.info("进入userInfo方法");
        WxOAuth2AccessToken wxOAuth2AccessToken = new WxOAuth2AccessToken();
        try {
            wxOAuth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(code);
            log.info(" wxOAuth2AccessToken{}",wxOAuth2AccessToken);
        } catch (WxErrorException e) {
           log.error("【微信授权】{}",e);
           throw new SellException(ResultEnum.WECHAT_MP_ERROR);
        }
        String openId = wxOAuth2AccessToken.getOpenId();
        log.info(openId);
        return "redirect:"+returnUrl+"?openid="+openId;

    }

}
