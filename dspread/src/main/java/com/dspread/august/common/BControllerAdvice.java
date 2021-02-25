package com.dspread.august.common;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BControllerAdvice {

    //如果需要返回值的是view，则方法的返回值是ModelAndView
    //如果需要返回值是String或者Json数据，则需要在方法上添加@ResponseBody
    /*
    @ExceptionHandler(value=RuntimeException.class)
    @ResponseBody
    public JsonResult defaultExceptionHandler(HttpServletRequest request){
       return new JsonResult(-1,"对不起，服务器正忙，请稍后再试",null);
    }
    */
    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResult errorHandler(Exception ex){
        return new JsonResult(false, ex.getMessage(), null);
    }

    /**
     * 拦截捕捉自定义异常
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public JsonResult myErrorHandler(BusinessException ex) {
        JsonResult result = new JsonResult(false,ex.getMessage(),null);
        return result;
    }
}
