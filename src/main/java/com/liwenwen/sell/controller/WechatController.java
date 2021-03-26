package com.liwenwen.sell.controller;

import com.liwenwen.sell.config.ProjectUrlConfig;
import com.liwenwen.sell.enums.ResultEnum;
import com.liwenwen.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.util.Map;


/**
 * 微信网页授权
 */

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {
    @Autowired
    private  WxMpService wxMpService;
    @Autowired
    private WxMpService wxOpenService;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        //1.配置  在config与yaml 文件中

        //2.调用方法 调用微信接口
        String url=projectUrlConfig.getWechatMpAuthorize()+"sell/wechat/userInfo";
        String redirectUrl= wxMpService.getOAuth2Service().buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        return  "redirect:"+redirectUrl;
    }
    @GetMapping("/userInfo")
    public String userinfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl){
        WxOAuth2AccessToken wxOAuth2AccessToken = new WxOAuth2AccessToken();
        try {
            wxOAuth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(code);
        } catch (WxErrorException e) {
           log.error("【微信授权】e={}",e);
           throw new SellException(ResultEnum.WECHAT_MP_ERROR);
        }
        String openId = wxOAuth2AccessToken.getOpenId();
        log.info("【用户登录】openId获取成功 openId={}",openId);
        return "redirect:"+returnUrl+"?openid="+openId;

    }

    /**
     * 将获取用户openid的连接转化为二维码
     * @param map
     * @return
     */
    @GetMapping("/qrAuthorize")
    public String qrAuthorize(Map<String,Object> map){
        String returnUrl = projectUrlConfig.getWechatOpenAuthorize()+"sell/seller/login";
        String url = projectUrlConfig.getWechatOpenAuthorize()+"sell/wechat/qrUserInfo";

        //自己写的登录
        String redirectUrl= wxMpService.getOAuth2Service().buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        log.info("【卖家端登录】redirectUrl={}",redirectUrl);
        map.put("redirectUrl",redirectUrl);
        //利用开放平台接口登录
       // String  redirectUrl = wxOpenService.buildQrConnectUrl(url,WxConsts.QrConnectScope.SNSAPI_LOGIN,URLEncoder.encode(returnUrl));
        return  "seller/login";
    }

    /**
     * 这里其实就是写了一个和userinfo一样的方法
     * @param code
     * @param returnUrl
     * @return
     */
    @GetMapping("/qrUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                             @RequestParam("state") String returnUrl){
        WxOAuth2AccessToken wxOAuth2AccessToken = new WxOAuth2AccessToken();
        try {
            wxOAuth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(code);
            log.info(" wxOAuth2AccessToken{}",wxOAuth2AccessToken);
        } catch (WxErrorException e) {
            log.error("【微信授权】{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR);
        }
        String openId = wxOAuth2AccessToken.getOpenId();
        log.info("【客户端授权登录】 获取openid 成功 openId={}",openId);
        return "redirect:"+returnUrl+"?openId="+openId;
    }

}
