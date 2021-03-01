package com.liwenwen.sell.VO;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class ErrorResultVo extends ResultVo{
    //表示HTTP状态代码。
    private HttpStatus status;
    //访问路径
    //private String path;
    //错误类型
    private String exception;

    public ErrorResultVo(HttpStatus status, String exception) {
        this.status = status;
       // this.path = path;
        this.exception = exception;
    }

    public ErrorResultVo(String msg, HttpStatus status,  String exception) {
        super(msg);
        this.status = status;
        //this.path = path;
        this.exception = exception;
    }

    public ErrorResultVo(Integer code, String msg, HttpStatus status, String exception) {
        super(code, msg);
        this.status = status;
        //this.path = path;
        this.exception = exception;
    }
}
