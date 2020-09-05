package com.example.productserver.common;

import lombok.Data;

@Data
public class CommonReturnType {

    private String code;

    private String msg;

    private Object data;

    public static CommonReturnType creat(Object result){
        return CommonReturnType.creat(result,"200","success");
    }
    public static CommonReturnType fail(){
        return CommonReturnType.creat(null,"500","fail");
    }

    public static CommonReturnType creat(Object result, String code, String msg) {
        CommonReturnType type = new CommonReturnType();
        type.setCode(code);
        type.setData(result);
        type.setMsg(msg);
        return type;
    }

}
