package com.liwenwen.sell.service.Impl;

import com.liwenwen.sell.config.WechatAccountConfig;
import com.liwenwen.sell.dto.OrderDto;
import com.liwenwen.sell.service.PushNotify;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PushNotifyImpl implements PushNotify {
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Override
    public void orderStatus(OrderDto orderDto) {
        WxMpTemplateMessage var1 = new WxMpTemplateMessage();
        var1.setTemplateId(wechatAccountConfig.getTemplateId().get("orderStatus"));
        var1.setToUser(orderDto.getBuyerOpenid());
        //{{first.DATA}}
        // 商家名称：{{keyword1.DATA}}
        // 商家电话：{{keyword2.DATA}}
        // 订单号：{{keyword3.DATA}}
        // 状态：{{keyword4.DATA}}
        // 总价：{{keyword5.DATA}}
        // {{remark.DATA}}
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","亲记得收货"),
                new WxMpTemplateData("keyword1","微信点餐"),
                new WxMpTemplateData("keyword2","18848956212"),
                new WxMpTemplateData("keyword3",orderDto.getOrderId()),
                new WxMpTemplateData("keyword4",orderDto.getOrderStatusEnum(orderDto.getOrderStatus())),
                new WxMpTemplateData("keyword5","￥"+orderDto.getOrderAmount()),
                new WxMpTemplateData("remark","欢迎再次光临")


        );
        var1.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(var1); //String sendTemplateMsg(WxMpTemplateMessage var1) throws WxErrorException;
        } catch (WxErrorException e) {
            log.info("【微信通知】 通知异常 {}",e);
            e.printStackTrace();
        }

    }
}
