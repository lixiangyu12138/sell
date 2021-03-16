package com.liwenwen.sell.dao;

import com.liwenwen.sell.pojo.PayInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayInfoDao extends JpaRepository<PayInfo,String> {
    PayInfo findByPrePayParams(String prePayParams);
}
