package com.liwenwen.sell.dao;

import com.liwenwen.sell.pojo.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderMasterDao extends JpaRepository<OrderMaster,String> {
    //分页 按照买家openid查寻订单
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
    //List<OrderMaster> findByBuyerOpenid(String buyerOpenid);

}
