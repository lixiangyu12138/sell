package com.liwenwen.sell.service.Impl;

import com.liwenwen.sell.conterver.OrderMasterToOrderDTOConverter;
import com.liwenwen.sell.dao.OrderDetailDao;
import com.liwenwen.sell.dao.OrderMasterDao;
import com.liwenwen.sell.dto.CarDto;
import com.liwenwen.sell.dto.OrderDto;
import com.liwenwen.sell.enums.OrderStatusEnum;
import com.liwenwen.sell.enums.PayStatusEnum;
import com.liwenwen.sell.enums.ResultEnum;
import com.liwenwen.sell.exception.SellException;
import com.liwenwen.sell.pojo.OrderDetail;
import com.liwenwen.sell.pojo.OrderMaster;
import com.liwenwen.sell.pojo.ProductInfo;
import com.liwenwen.sell.service.OrderService;
import com.liwenwen.sell.service.ProductService;
import com.liwenwen.sell.service.WebSocket;
import com.liwenwen.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private OrderMasterDao orderMasterDao;
    @Autowired
    private PushNotifyImpl pushNotify;
    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    //创建订单
    public OrderDto create(OrderDto orderDto) {
        //创建orderId
        String orderId = KeyUtil.genUniqueKey();
        //定义个总价变量
        BigDecimal orderSum = new BigDecimal(BigInteger.ZERO);

        //查询商品信息（productInf 表 数量，价格）
      for (OrderDetail orderDetail : orderDto.getOrderDetails()){
          ProductInfo productInfo=productService.findOne(orderDetail.getProductId());
          //已经在商品service层判断过了应该不会出错了
          if (productInfo==null) {
              log.error("【创建订单】商品不存在");
              throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
          }
          //计算订单总价
          orderSum=productInfo.getProductPrice()
                  .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                  .add(orderSum);
          //订单详情写入数据库
          BeanUtils.copyProperties(productInfo,orderDetail); //属性值为null的时候也会被拷贝进去
          orderDetail.setDetailId(KeyUtil.genUniqueKey());
          orderDetail.setOrderId(orderId);
          orderDetailDao.save(orderDetail);
      }
      //订单信息写入数据库
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderAmount(orderSum);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.save(orderMaster);

        //扣库存
        List<CarDto> carDtoList = orderDto.getOrderDetails().stream().map(e ->
                new CarDto(e.getProductId(),e.getProductQuantity())
                ).collect(Collectors.toList());

        //System.out.println("测试-------："+carDtoList);
        productService.decreaseStoke(carDtoList);
        //发送webSocket消息
        webSocket.sendMessage(orderDto.getOrderId());
        System.out.println("测试"+orderDto.getOrderId());
        return orderDto;
    }

    @Override
    //查询单个订单
    public OrderDto findOne(String orderId)   {
        OrderMaster orderMaster = new OrderMaster();
        try {
           orderMaster = orderMasterDao.getOne(orderId);
            System.out.println(orderMaster.getOrderId()==null);
            orderMaster.getOrderStatus();
        }catch (Exception e) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetails =orderDetailDao.findByOrderId(orderId);
        if(orderDetails.isEmpty()){
            log.info("【查询订单】订单详情为空");
            throw  new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDto orderDto= new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        orderDto.setOrderDetails(orderDetails);
        return orderDto;



    }

    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
            Page<OrderMaster> orderMasters = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
            if (orderMasters.getContent().size()==0){
                throw new SellException(ResultEnum.PARAM_ERROR);
            }
            List<OrderDto> orderDtos = OrderMasterToOrderDTOConverter.convwet(orderMasters.getContent());
            return new PageImpl<OrderDto>(orderDtos,pageable,orderMasters.getTotalElements());
    }

    @Override
    @Transactional
    //取消订单
    public OrderDto delete(OrderDto orderDto) {
        //判断订单状态
        OrderMaster orderMaster = new OrderMaster();

        //System.out.println("【订单状态】"+orderDto.getOrderStatus()+"ss"+orderDto.getOrderStatus());
        if(!orderDto.getOrderStatus().equals(orderDto.getOrderStatus())){
            log.error("【取消订单】订单状态不正确,orderId={}, orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_DELETE_ERROR);
        }
        //修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster updateResult=orderMasterDao.save(orderMaster);
        if(updateResult == null) {
            log.error("【取消订单】更新失败 orderMaster={}" ,orderMaster);
            throw new SellException(ResultEnum.ORDER_DELETE_ERROR2);
        }
        //返回库存
        if(CollectionUtils.isEmpty(orderDto.getOrderDetails())){
            log.error("【取消订单】订单中无商品详情 orderDto={}" ,orderDto);
            throw new SellException(ResultEnum.ORDER_DELETE_ERROR3);
        }
        List<CarDto> carDtos = orderDto.getOrderDetails().stream()
                .map(e ->new CarDto(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStoke(carDtos);

        //退款
        if(orderDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO

        }
        //
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto finish(OrderDto orderDto) {
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}", orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_DELETE_ERROR);
        }

        //修改状态
        orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster= new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster save = orderMasterDao.save(orderMaster);
        if(save == null) {
            log.error("【取消订单】更新失败 orderMaster={}" ,orderMaster);
            throw new SellException(ResultEnum.ORDER_DELETE_ERROR2);
        }
        //推送微信通知
        pushNotify.orderStatus(orderDto);
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto pay(OrderDto orderDto) {
        log.info("【支付订单】++++修改订单状态++++}");
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【支付订单】订单状态不正确，orderId={},orderStatus={}", orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_DELETE_ERROR);
        }
        //判断支付状态
        if(!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【支付订单】订单支付状态不正确，orderId={},PayStatus={}", orderDto.getOrderId(), orderDto.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster= new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster save = orderMasterDao.save(orderMaster);
        if(save == null) {
            log.error("【支付订单】更新失败 orderMaster={}" ,orderMaster);
            throw new SellException(ResultEnum.ORDER_DELETE_ERROR2);
        }

        return orderDto;
    }

    @Override
    public Page<OrderDto> findAll(Pageable pageable) {
        Page<OrderMaster> all = orderMasterDao.findAll(pageable);
        List<OrderDto> orderDtoList = OrderMasterToOrderDTOConverter.convwet(all.getContent());
        return new PageImpl<OrderDto>(orderDtoList,pageable,all.getTotalElements());
    }


}
