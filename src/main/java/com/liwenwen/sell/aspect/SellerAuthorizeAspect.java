package com.liwenwen.sell.aspect;

import com.liwenwen.sell.exception.SellerAuthorizeException;
import com.liwenwen.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Pointcut("execution(public * com.liwenwen.sell.controller.Seller*.*(..))"+
            "&& !execution(public * com.liwenwen.sell.controller.SellerInfoController.*(..))")
    public void verify(){}
    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Cookie cookie= CookieUtil.get(request,"token");
        if(cookie==null){
            log.warn("【登录校验】 cookie 中查不到token");
            throw new SellerAuthorizeException();
        }
        String tokenValue= redisTemplate.opsForValue().get(cookie.getValue());
        if(tokenValue==null){
            log.warn("【登录校验】 cookie 中查不到token");
            throw new SellerAuthorizeException();
        }
    }
}
