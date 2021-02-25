/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.dspread.august.controller;

import com.dspread.august.common.AESUtil;
import com.dspread.august.common.Constans;
import com.dspread.august.common.ErrorCode;
import com.dspread.august.common.JsonResult;
import com.dspread.august.model.MerchantModel;
import com.dspread.august.model.TerminalInfoModel;
import com.dspread.august.service.MerchantService;
import com.dspread.august.service.UserService;
import com.dspread.august.util.JwtTokenUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value="/api/merchant")
public class MerchantController {
    @Autowired
    MerchantService merchantService;

    /**
     * 测试接口index
     *
     * @return String
     */
//    @RequestMapping(value="/index",method = RequestMethod.GET)
    @PostMapping(value = "/index")
    @ApiOperation(value = "测试接口", notes = "测试连接，返回merchant：I'am a merchant!")
    public String index(){
        return "merchant：I'am a merchant!";
    }

    /**
     * 登录
     *
     * @return JsonResult
     */
//    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @PostMapping(value = "/login")
    @ApiOperation(value = "登录接口", notes = "参数 post 流")
    public JsonResult login(String userModel){
        return JsonResult.SUCCESS;
    }

    /**
     * 注册
     *
     * @param merchantModel 商户信息
     * @return JsonResult
     */
//    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    @PostMapping(value = "/regist")
    @ApiOperation(value = "注册接口")
//    @ApiImplicitParam(name = "MerchantModel", value = "商户信息", paramType = "query", dataType = "MerchantModel", required = true)
    public JsonResult regist(@RequestBody MerchantModel merchantModel){

        MerchantModel registModel = merchantService.regist(merchantModel);
        JsonResult jsonResult = new JsonResult();
        if (registModel == null){
            jsonResult.setCode(ErrorCode.ERROR_USER_HASE_REGISTED.getErrorCode());
            jsonResult.setMsg(merchantModel.getUsername().concat("已注册！"));
        }else {
            jsonResult.setMsg("注册成功");
        }
        return jsonResult;
    }

    /**
     * 退出登录
     *
     * @return JsonResult
     */
//    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @PostMapping(value = "/logout")
    @ApiOperation(value = "登出接口")
    public JsonResult logout(){
        return new JsonResult(true,"注销成功",null);
    }

    /**
     * 刷新token
     *
     * @return JsonResult
     */
//    @RequestMapping(value = "/refresh",method = RequestMethod.POST)
    @PostMapping(value = "/refresh")
    @ApiOperation(value = "刷新token", notes = "从请求头中获取token 验证通过返回token")
    public JsonResult refreshToken(){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String header = request.getHeader(Constans.HEADER);
        String token = header.substring(Constans.BEARER.length());
        JsonResult jsonResult = new JsonResult();
        try {
            String s = JwtTokenUtil.refreshToken(token);
            jsonResult.setData(s);
        }catch (Exception e){
            jsonResult = new JsonResult();
            jsonResult.setCode(ErrorCode.ERROR_GENERATE_ENVELOP.getErrorCode());
            jsonResult.setMsg("error generate envelop");
            return jsonResult;
        }
        return jsonResult;

    }

}

