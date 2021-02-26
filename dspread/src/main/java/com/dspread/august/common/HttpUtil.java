package com.dspread.august.common;

import com.sun.jndi.toolkit.url.Uri;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.util.List;

public class HttpUtil {

    public static String doGet(String url){
        return doGet(url, null);
    }

    public static String doGet(String url, List<org.apache.http.cookie.Cookie> cookies) {
        String result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext context = HttpClientContext.create();
        SetCookie(context, cookies, url);
        try {
            HttpGet httpget = new HttpGet(url);
            System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget, context);
            // 获取响应实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 打印响应内容长度
                System.out.println("Response content length: " + entity.getContentLength());
                // 打印响应内容
                result = EntityUtils.toString(entity, "UTF-8");
                System.out.println("Response content: " + result);
            }

        } catch (IOException e) {
            //logger.error("http请求失败，url："+url,e);
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            }
            catch (IOException e){
//                e.printStackTrace();
            }
        }
        return  result;
    }

    /**
     * post请求（用于请求json格式的参数）
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, String params) throws Exception{
        return doPost(url, params, null);
    }

    public static String doPost(String url, String params, List<org.apache.http.cookie.Cookie> cookies) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        HttpClientContext context = HttpClientContext.create();
        SetCookie(context, cookies, url);
        try {

            response = httpclient.execute(httpPost, context);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString;
            }
            else{
                //do log
            }
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
//                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
        return null;
    }

    static void SetCookie(HttpClientContext context, List<org.apache.http.cookie.Cookie> cookies, String url){
        if (cookies != null && cookies.size() > 0) {
            CookieStore cookieStore = new BasicCookieStore();
            String host = null;
            try {
                Uri uri = new Uri(url);
                host = uri.getHost();
            }catch (Exception ex){

            }
            for(int i=0; i<cookies.size(); i++){
                org.apache.http.cookie.Cookie cookie = cookies.get(i);
                if(StringUtils.isEmpty(cookie.getDomain()) && !StringUtils.isEmpty(host)){
                    if(cookie instanceof BasicClientCookie){
                        ((BasicClientCookie)cookie).setDomain(host);
                        ((BasicClientCookie)cookie).setPath("/");
                    }
                }
                cookieStore.addCookie(cookie);
            }
            context.setCookieStore(cookieStore);
        }
    }

}