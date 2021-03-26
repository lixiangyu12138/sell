package com.liwenwen.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    //微信公众平台Id
    private String mpAppId;
    //微信公众平台密钥
    private String mpAppSecret;
    //商户号
    private String mchId;
    //商户密钥
    private String mchKey;
    private String keyPath;
    private String notifyUrl;
    //开放平台Id
    private String openAppId;
    //开放平台密钥
    private String openAppSecret;
    /**
     * 微信通知模板Id
     */
    private Map<String,String> templateId;



}
