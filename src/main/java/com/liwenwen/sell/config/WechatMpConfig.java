package com.liwenwen.sell.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WechatMpConfig {
    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService=new WxMpServiceImpl();
        wxMpService .setWxMpConfigStorage(configStorage());

        return wxMpService;
    }
//    @Bean
    public WxMpConfigStorage configStorage(){
        WxMpDefaultConfigImpl configStorage=new WxMpDefaultConfigImpl();
        configStorage.setAppId(wechatAccountConfig.getMpAppId());
        configStorage.setSecret(wechatAccountConfig.getMpAppSecret());
        return configStorage;
    }
}
