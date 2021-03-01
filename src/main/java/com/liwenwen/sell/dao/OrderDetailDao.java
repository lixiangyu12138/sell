package com.liwenwen.sell.dao;

import com.liwenwen.sell.pojo.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;


import java.util.List;

public interface OrderDetailDao extends JpaRepository<OrderDetail,String> {
    @Nullable
    List<OrderDetail> findByOrderId(String orderId);
}
