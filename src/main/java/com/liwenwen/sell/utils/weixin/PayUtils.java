package com.liwenwen.sell.utils.weixin;

import com.liwenwen.sell.dao.PayInfoDao;
import com.liwenwen.sell.pojo.PayInfo;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;


public class PayUtils {


    @Autowired
    private PayInfoDao payInfoDao;

    private PayResponse payResponse = new PayResponse();
    public  PayResponse getPayResponse(PayInfo  byId){
        PayResponse payResponse = new PayResponse();
        payResponse.setSignType(byId.getSignType());
        payResponse.setPrePayParams(byId.getPrePayParams());
        payResponse.setOrderId(byId.getOrderId());
        payResponse.setOrderAmount(byId.getOrderAmount());

        return payResponse;
    }
    public  PayResponse getPayResponse(PayRequest payRequest){

        String prePayParams = RandomStringUtils.randomAlphanumeric(15) + "-" + RandomStringUtils.randomAlphanumeric(5);
        payResponse.setSignType(payRequest.getOrderName());
        payResponse.setPrePayParams(prePayParams);
        payResponse.setOrderId(payRequest.getOrderId());
        payResponse.setOrderAmount(payRequest.getOrderAmount());
        return payResponse;
    }

}
