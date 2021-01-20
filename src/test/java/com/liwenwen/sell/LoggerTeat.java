package com.liwenwen.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTeat {
    //选择org.slf4j.Logger
    //private Logger logger= LoggerFactory.getLogger(LoggerTeat.class);
    String name = "liwenwen";
    String password= "123456";
    @Test
    public void test(){
        log.debug("debug...");
        log.info("name：{}, password：{}",name,password);
        log.info("name：{}, password：{}",name,password);
        log.error("error.....");

    }
}
