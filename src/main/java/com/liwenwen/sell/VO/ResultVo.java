package com.liwenwen.sell.VO;

import lombok.Data;

/**
 * http请求返回的最外层对象
 */
@Data
public class ResultVo<T> {
    /*错误码*/
    private Integer code;
    /*提示信息*/
    private String msg;
    /*具体内容*/
    private T data;


//
//    @param resultEnum 自定义枚举类，包含 code 和 message
//    public ResultVo(ResultEnum resultEnum) {
//        this.code = resultEnum.getCode();
//        this.msg = resultEnum.getMsg();
//    }


//    /**
//     * SellException 类包含 code 和 message
//     * @param e
//     */
//    public ResultVo(SellException e){
//        this.code=e.getCode();
//        this.msg=e.getMessage();
//    }
//    public static ResultVo<Object> otherError(SellException sellException) {
//        return new ResultVo<>(sellException);
//    }

    public ResultVo(){

    }
    public ResultVo(String msg){
        this.code=0;
        this.msg=msg;
       // this.type=type;
    }
    public ResultVo(Integer code,String msg){
        this.code=code;
        this.msg=msg;
        //this.type=type;

    }

}
