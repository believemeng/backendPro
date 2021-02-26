package com.dspread.august.util;

import java.util.ArrayList;

/**
 * 过滤这些api，不用检测token和登录验证
 */
public class FilterUtil {
    public static final ArrayList<String> arrUrl;
    static {
        arrUrl = new ArrayList<>();
        arrUrl.add("/api/merchant/index");
        arrUrl.add("/api/merchant/login");
        arrUrl.add("/api/merchant/regist");
        arrUrl.add("/api/merchant/logout");
        arrUrl.add("/api/verifyAppSignature");
        arrUrl.add("/api/index");
        arrUrl.add("/api/monitor");
        arrUrl.add("/api/transaction");
        arrUrl.add("/api/sharing/user/*");
        // SWAGGER2过滤【START】
        arrUrl.add("/swagger-ui.html");
        arrUrl.add("/swagger-resources");
        arrUrl.add("/swagger-resources/**");
        arrUrl.add("/v2/api-docs");
        arrUrl.add("/webjars/springfox-swagger-ui/**");
        // SWAGGER2过滤【END】
    }
}
