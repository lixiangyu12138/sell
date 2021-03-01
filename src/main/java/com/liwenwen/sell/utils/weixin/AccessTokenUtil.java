package com.liwenwen.sell.utils.weixin;

import org.apache.commons.lang.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 生成假的access_token
 */
public class AccessTokenUtil {
    public static Map<String,String> getAccessToken(String ip){
        String openid="mNPCEShQzVRZl8v-21lCn";
        if (!ip.equals("192.168.1.14")) {
            openid = RandomStringUtils.randomAlphanumeric(15) + "-" + RandomStringUtils.randomAlphanumeric(5);
        }
        String access_toke="42_Rh6jAkMNR6QjfCLukAiRwVJVe5lUfLqdATjRSCvce3lrRi9EVdXgI4V-06FAHkJlCdN_GKoTy2RseaBEWOaFbRxebgOk36pU02d4cuUtPPXgzI0cRtyuPx5N2u2NZ_wzg94xLz5tKrk0rQhHUFXjADAJUM";
        String expires_in="7200";
        String refresh_token="42_Rh6jAkMNR6QjfCLukAiRwVJVe5lUfLqdATjRSCvce3lrRi9EVdXgI4V-06FAHkJlCdN_GKoTy2RseaBEWOaFbRxebgOk36pU02d4cuUtPPXgzI0cRtyuPx5N2u2NZ_wzg94xLz5tKrk0rQhHUFXjADAJUM";
        String scope="snsapi_base";

        Map<String,String> access_token = new HashMap<>();
        access_token.put("access_token",access_toke);
        access_token.put("expires_in",expires_in);
        access_token.put("refresh_token",refresh_token);
        access_token.put("scope",scope);
        access_token.put("openid",openid);


        return access_token;


    }
}
