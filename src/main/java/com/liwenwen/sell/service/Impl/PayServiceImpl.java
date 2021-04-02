package com.liwenwen.sell.service.Impl;

import com.liwenwen.sell.dao.PayInfoDao;
import com.liwenwen.sell.dto.OrderDto;
import com.liwenwen.sell.enums.ResultEnum;
import com.liwenwen.sell.exception.SellException;
import com.liwenwen.sell.pojo.PayInfo;
import com.liwenwen.sell.service.OrderService;
import com.liwenwen.sell.service.PayService;
import com.liwenwen.sell.utils.JsonUtil;
import com.liwenwen.sell.utils.MathUtil;
import com.liwenwen.sell.utils.weixin.PayUtils;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private final static String ORDER_NAME= "微信订单";
//    @Autowired
//    private BestPayServiceImpl bestPayService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayInfoDao payInfoDao;
    @Override
    public PayResponse create(OrderDto orderDto) {

        //log.info("【微信支付】 orderDto={}", JsonUtil.toJson(orderDto));
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDto.getBuyerOpenid());
        payRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDto.getOrderId());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        payRequest.setOrderName(ORDER_NAME);

        log.info("【微信支付】 payRequest={}", JsonUtil.toJson(payRequest));

        //PayResponse payResponse = bestPayService.pay(payRequest);
        //假的
        PayResponse payResponse =new PayUtils().getPayResponse(payRequest);
        payInfoDao.save(new PayInfo(payResponse));
        log.info("【微信统一支付】payResponse={}",JsonUtil.toJson(payResponse));

        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //验证签名 （bestPay验证）
        //支付状态 支付是否成功微信是否出了bug （bestPay验证）
        //支付金额 微信支付金额和订单金额相同
        //支付人（下单人== 支付人）

        PayInfo  daoOne = payInfoDao.getOne(notifyData);
        PayResponse payResponse=new PayUtils().getPayResponse(daoOne);
        log.info("【发起支付】 异步通知，payResponse={}",JsonUtil.toJson(payResponse));
        //log.info("【发起支付】 异步通知，notifyData={}",JsonUtil.toJson(notifyData));
        //查询订单
        OrderDto one = orderService.findOne(payResponse.getOrderId());
        //判断订单是否存在
        if(one ==null){
            log.error("【微信支付】 异步通知，订单不存在 orderId={}" ,payResponse.getOrderId());
            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断金额是否一致(0.1 .0.10)
        if(!MathUtil.equals(payResponse.getOrderAmount(),one.getOrderAmount())){
            log.error("【微信支付】 异步通知，订单金额不一致，orderId={},微信金额={}，系统金额={}",payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    one.getOrderAmount());
            throw new SellException(ResultEnum.WECHAT_PAY_ERROR);
        }
        log.info("【发起支付】 ++++校验通过+++");
        orderService.pay(one);
        log.info("【发起支付】 异步通知，payResponse={}",JsonUtil.toJson(payResponse));
        return payResponse;
    }
}
