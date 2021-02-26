package com.dspread.august.security;

import com.dspread.august.common.JsonResult;
import com.dspread.august.exception.CustomerException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class Util {

    public static void SendAjaxLogin(HttpServletResponse response, String message) throws IOException {
        SendAjaxError(response, 401, message);
    }

    public static void SendAjaxError(HttpServletResponse response, int code, String message) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("code", code);
        data.put("message", message);
        response.setCharacterEncoding("UTF-8");

        response.setContentType("application/json;UTF-8");

        com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
        //mapper.writeValue(out, data);
        String str = mapper.writeValueAsString(data);
        OutputStream out = response.getOutputStream();
        out.write(str.getBytes("UTF-8"));
    }


    public static boolean IsAjaxRequest(HttpServletRequest request) {
        String ajaxFlag = request.getHeader("X-Requested-With");
        return ajaxFlag != null && "XMLHttpRequest".equals(ajaxFlag);
    }


    public static void SendUserVerifyError(HttpServletRequest request, HttpServletResponse response, CustomerException authentication) throws IOException {


        ObjectMapper mapper = new ObjectMapper();
        JsonResult jsonResult = new JsonResult(authentication.getMessage());
        jsonResult.setCode(authentication.getErrorCode().getErrorCode());
        String jsonString = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(jsonResult);
        response.setContentType("Application/json;charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write(jsonString);
        writer.flush();
        writer.close();
    }

    public static String getJsonFromRequest(HttpServletRequest request) {

        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            ServletInputStream inputStream = request.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
//            e.printStackTrace();
            return null;
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
//                e.printStackTrace();
            }

        }
        return builder.toString();

    }


    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    public static String getBodyString(ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();

    }
}
