package com.liwenwen.sell.controller;

import com.liwenwen.sell.VO.ResultVo;
import com.liwenwen.sell.conterver.OrderFormTOOrderDtoConverter;
import com.liwenwen.sell.dto.OrderDto;
import com.liwenwen.sell.enums.ResultEnum;
import com.liwenwen.sell.exception.SellException;
import com.liwenwen.sell.form.OrderForm;
import com.liwenwen.sell.service.OrderService;
import com.liwenwen.sell.utils.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    //创建订单
    @PostMapping("create")
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确 ,orderFrom={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDto orderDto= OrderFormTOOrderDtoConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDto.getOrderDetails())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CAR_ISEMPTY);
        }
        OrderDto orderDto1 = orderService.create(orderDto);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderDto1.getOrderId());
        return ResultVoUtil.success(map);

    }

    //订单列表
    @GetMapping("list")
    public ResultVo<List<OrderDto>> findList(@RequestParam("openid") String openid,
                                             @RequestParam(value = "page",defaultValue = "0") Integer page,
                                             @RequestParam(value = "size",defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)) {
            log.error("【查询列表】 openid 为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request= PageRequest.of(page,size);
        Page<OrderDto> list = orderService.findList(openid, request);
        //System.out.println("========"+list);
        return ResultVoUtil.success(list.getContent());

    }

    //查询单个订单详情
    @GetMapping("/detail")
    public ResultVo<OrderDto> findOne(@RequestParam("openid") String openid,
                                      @RequestParam("orderId") String orderId){
        //TODO 不安全做法，改进
        OrderDto orderDto=orderService.findOne(orderId);
        if(!orderDto.getBuyerOpenid().equals(openid)){
            throw new SellException(ResultEnum.ORDER_FIND_ERROR);
        }
        return ResultVoUtil.success(orderDto);
    }
    @PostMapping("/delete")
    public ResultVo delete(@RequestParam("openid") String openid,
                                      @RequestParam("orderId") String orderId){
        //TODO 不安全做法，改进
        OrderDto one = orderService.findOne(orderId);
        System.out.println(one.getBuyerOpenid());
        System.out.println(openid);
        if(!one.getBuyerOpenid().equals(openid)){
            throw new SellException(ResultEnum.ORDER_FIND_ERROR);
        }
        orderService.delete(one);
        return ResultVoUtil.success();
    }


    //取消订单
}
