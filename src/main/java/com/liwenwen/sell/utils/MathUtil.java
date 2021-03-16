package com.liwenwen.sell.utils;

import java.math.BigDecimal;

public class MathUtil {
    public static final Double MONEY_RANGE=0.01;
    public static Boolean equals(Double d1, BigDecimal d2){
        Double result = Math.abs(d1-d2.doubleValue());
        if(result< MONEY_RANGE){
            return true;
        }else{
            return false;
        }
    }

}
