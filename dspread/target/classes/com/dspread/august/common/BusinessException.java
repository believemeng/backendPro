package com.dspread.august.common;

public class BusinessException extends RuntimeException {

    public BusinessException(String msg) {
        this.message = msg;
        this.code = 0;
    }

    public BusinessException(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    private String message;
    private int code;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
