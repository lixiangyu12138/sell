package com.liwenwen.sell.dao;

import com.liwenwen.sell.pojo.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoDao extends JpaRepository<SellerInfo, String> {
    SellerInfo findByOpenId(String openid);
}
