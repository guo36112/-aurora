package com.example.orderserver.common;

import lombok.Data;

@Data
public class CommonReturnType<T>  {

    private String code;

    private String msg;

    private T data;

    public static CommonReturnType creat(Object result){
        return CommonReturnType.creat(result,"200","success");
    }

    public static CommonReturnType creat(Object result, String code, String msg) {
        CommonReturnType type = new CommonReturnType();
        type.setCode(code);
        type.setData(result);
        type.setMsg(msg);
        return type;
    }

}
