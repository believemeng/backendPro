package com.dspread.august.common;

import com.dspread.august.util.JwtTokenUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class JsonResult<T> {
    //0=成功
    public int code;
    public String msg;
    public String token;
    public T data;

    /**
     * 操作成功
     */
    public JsonResult(){
        this.code = 200;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String header = request.getHeader(Constans.HEADER);
        if (header!=null && !header.isEmpty()){
            String token = header.substring(Constans.BEARER.length());
            String refreshToken = JwtTokenUtil.refreshToken(token);
            this.token = refreshToken;
        }
    }

    /**
     * 操作成功
     * @param data 返回的数据
     */
    public JsonResult(T data){
        this.data = data;
        this.code = 200;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String header = request.getHeader(Constans.HEADER);
        if (header!=null && !header.isEmpty()){
            String token = header.substring(Constans.BEARER.length());
            String refreshToken = JwtTokenUtil.refreshToken(token);
            this.token = refreshToken;
        }
    }

    /**
     * 自定义结果
     * @param success 操作是否成功
     * @param msg 返回的消息
     * @param data 返回的数据
     */
    public JsonResult(boolean success,String msg,T data){
        this.code = success ? 200 : 1;
        this.msg = msg;
        this.data = data;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String header = request.getHeader(Constans.HEADER);
        if (header!=null && !header.isEmpty()){
            String token = header.substring(Constans.BEARER.length());
            String refreshToken = JwtTokenUtil.refreshToken(token);
            this.token = refreshToken;
        }
    }

    /**
     * 自定义结果
     * @param code
     * @param msg 返回的消息
     * @param data 返回的数据
     */
    public JsonResult(int code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String header = request.getHeader(Constans.HEADER);
        if (header!=null && !header.isEmpty()){
            String token = header.substring(Constans.BEARER.length());
            String refreshToken = JwtTokenUtil.refreshToken(token);
            this.token = refreshToken;
        }
    }

    /**
     * 成功
     */
    public static JsonResult SUCCESS = new JsonResult();

    /**
     * 失败
     */
    public static JsonResult Fail = new JsonResult(false,"操作失败",null);

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

