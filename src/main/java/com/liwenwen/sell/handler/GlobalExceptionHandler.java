package com.liwenwen.sell.handler;

import com.liwenwen.sell.VO.ErrorResultVo;

import com.liwenwen.sell.config.ProjectUrlConfig;
import com.liwenwen.sell.exception.SellException;
import com.liwenwen.sell.exception.SellerAuthorizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * 全局处理异常
 * 异常统一处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    private ProjectUrlConfig projectUrlConfig;


    /**
     * SellException
     * @return Result
     */

    @ExceptionHandler(value = SellException.class)
    public ErrorResultVo exceptionHandler(SellException e) {
        //log.error("【错误】sellException={}",e);
       String exception= String.valueOf(e.getClass());

        return new ErrorResultVo(e.getCode(),e.getMessage(),HttpStatus.BAD_REQUEST,exception);
    }

    /**
     * Exception
     * @param e
     * @return
     */

    @ExceptionHandler(value = Exception.class)
    public ErrorResultVo exceptionHandler(Exception e) {
        String exception= String.valueOf(e.getClass());
        return new ErrorResultVo(e.getMessage(),HttpStatus.BAD_REQUEST,exception);

    }
    //拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        return new ModelAndView("redirect:".concat(projectUrlConfig.getWechatOpenAuthorize())
        .concat("sell/wechat/qrAuthorize")
        .concat("?returnUrl=")
        .concat(projectUrlConfig.getSell())
        .concat("sell/seller/login"));

    }



}
