/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.dspread.august.controller.sharing;

import com.dspread.august.common.JsonResult;
import com.dspread.august.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/sharing/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String index(){
        return "Sharing：I'am a sharing!";
    }
    @RequestMapping(value="/info",method = {RequestMethod.GET,RequestMethod.POST})
    public JsonResult info(){
        return new JsonResult(userService.info());
    }

    @RequestMapping(value = "/login",method = {RequestMethod.GET,RequestMethod.POST})
    public JsonResult login(String username, String password){
        return new JsonResult().SUCCESS;
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public JsonResult logout(){
        return new JsonResult(0,"注销成功",null);
    }
}

