package com.liwenwen.sell.service.Impl;

import com.liwenwen.sell.dao.SellerInfoDao;
import com.liwenwen.sell.enums.ResultEnum;
import com.liwenwen.sell.exception.SellException;
import com.liwenwen.sell.pojo.SellerInfo;
import com.liwenwen.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerInfoDao sellerInfoDao;
    @Override
    public SellerInfo findSellerInfoByOpenId(String openid) {
        SellerInfo byOpenId = sellerInfoDao.findByOpenId(openid);
        if(byOpenId == null){
            throw  new SellException(ResultEnum.WECHAT_OPEN_LOGIN_ERROR);
        }
        return byOpenId;
    }
}
