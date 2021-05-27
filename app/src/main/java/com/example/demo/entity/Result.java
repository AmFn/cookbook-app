package com.example.demo.entity;

public class Result<Object> {
    private int status;
    private String msg;
    private CookBook result;
    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CookBook getResult() {
        return result;
    }

    public void setResult(CookBook result) {
        this.result = result;
    }
}
