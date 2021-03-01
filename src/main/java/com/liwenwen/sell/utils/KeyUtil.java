package com.liwenwen.sell.utils;

import java.util.Random;

public class KeyUtil {
    public static  synchronized String genUniqueKey(){
        /**
         * 生成唯一的主键
         * 格式：时间+随机数
         */
        Random r = new Random();
        Integer num = r.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(num);
    }
}
