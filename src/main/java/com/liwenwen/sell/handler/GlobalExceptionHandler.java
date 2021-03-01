package com.liwenwen.sell.handler;

import com.liwenwen.sell.VO.ErrorResultVo;

import com.liwenwen.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * 全局处理异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


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



}
