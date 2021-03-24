package com.liwenwen.sell.service;

import com.liwenwen.sell.pojo.SellerInfo;

public interface SellerService {
    SellerInfo findSellerInfoByOpenId(String openid);
}
