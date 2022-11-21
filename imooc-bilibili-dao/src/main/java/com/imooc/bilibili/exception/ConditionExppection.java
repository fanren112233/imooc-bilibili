package com.imooc.bilibili.exception;

public class ConditionExppection extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String code;

    public ConditionExppection(String code,String name){
        super(name);
        this.code = code;
    }

    public ConditionExppection(String name){
        super(name);
        code = "500";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
