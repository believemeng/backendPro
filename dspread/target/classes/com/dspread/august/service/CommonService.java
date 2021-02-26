/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.dspread.august.service;

import com.alibaba.fastjson.JSON;
import com.dspread.august.common.JsonResult;
import com.dspread.august.common.safety.SafetyNetResponse;
import com.dspread.august.entity.*;
import com.dspread.august.model.*;
import com.dspread.august.repository.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class CommonService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    MonitorRepository monitorRepository;

    @Autowired
    TerminalInfoRepository terminalInfoRepository;

    @Autowired
    BaseLineRepository baseLineRepository;

    @Autowired
    ErrorMsgRepository errorMsgRepository;


    //添加监控
    public JsonResult addMonitor(MonitorModel model) {
        MonitorEntity entity = MonitorModel.toEntity(model);
        entity.setCreateTime(new Date());
        MonitorEntity monitor = monitorRepository.save(entity);
        if (monitor != null) {
            String url = "http://scm.ksher.cn/th/api/login";
            httpPost(url, monitor);
            return new JsonResult(true, "操作成功", monitor);
        } else {
            return JsonResult.Fail;
        }
    }

    //添加交易
    public JsonResult addTransaction(TransactionModel model) {

        if (StringUtils.isEmpty(model.getOrderNo())) {
            return new JsonResult(false, "订单号不能为空", null);
        }
        if (!StringUtils.isEmpty(model.getAmount())) {
            try {
                Float.parseFloat(model.getAmount());
            } catch (Exception e) {
                return new JsonResult(false, "订单号金额有误", null);
            }
        }
        TransactionEntity entity = TransactionModel.toEntity(model);
        entity.setCreateTime(new Date());
        TransactionEntity transaction = transactionRepository.save(entity);

        if (transaction != null) {
            String url = "http://scm.ksher.cn/th/api/login";
            httpPost(url, transaction);
            return new JsonResult(true, "操作成功", transaction);
        } else {
            return JsonResult.Fail;
        }
    }


    public void httpPost(String url, Object model) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse closeableHttpResponse;
        try {
            HttpPost httpPost = new HttpPost(url);
            //httpPost.setHeader("Authorization", "Bearer " + headersAuthor);
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
            String jsonParam = JSON.toJSONString(model);
            StringEntity http_entity = new StringEntity(jsonParam, ContentType.create("text/json", "UTF-8"));
            httpPost.setEntity(http_entity);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
            httpPost.setConfig(requestConfig);

            closeableHttpResponse = closeableHttpClient.execute(httpPost);

            int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                //TODO:状态码非200代表没有正常返回,此处处理你的业务
            }

            HttpEntity httpEntity = closeableHttpResponse.getEntity();
            /*String asset_synchronization = EntityUtils.toString(httpEntity, "UTF-8");
            JSONArray jsonArray = JSONArray.parseArray(asset_synchronization);
            if (jsonArray.size() > 0) {
                //TODO:判断返回的json数组是否不为空,此处即可处理你的业务
            }*/
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    public JsonResult verifyScrp(TerminalInfoModel terminalInfoModel) {
        String terminalId = terminalInfoModel.getTerminalId();
        TerminalInfoEntity terminalInfoEntity = terminalInfoRepository.findByTerminalId(terminalId);
        String firmwareVersion = terminalInfoEntity.getFirmwareVersion();
        JsonResult jsonResult = new JsonResult();
        if (firmwareVersion.equalsIgnoreCase(terminalInfoModel.getFirmwareVersion())) {
            jsonResult.setMsg("verify success");
            jsonResult.setData(true);
        } else {
            jsonResult.setMsg("verify failed");
            jsonResult.setData(false);
        }
        return jsonResult;
    }

    public boolean verifyCots(String parse) {
        HashMap<String, String> map = new HashMap<>();
        String[] splitPase = parse.split(";");
        for (String item : splitPase) {
            map.put(item.split(":")[0],item.split(":")[1]);
        }
        List<BaseLineEntity> all = baseLineRepository.findByCotsModelLike(map.get("cotsModel"));
        for (BaseLineEntity entity : all) {
            BaseLineModel baseLineModel = BaseLineEntity.toModel(entity);
            String cotsVersion = baseLineModel.getCotsVersion();
            String[] split = cotsVersion.split(",");
            for (String version : split) {
                if (cotsVersion.compareTo(map.get("cotsVersion")) >= 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void saveErrorMsg(ErrorMsgModel model) {
        ErrorMsgEntity entity = ErrorMsgModel.toEntity(model);
        entity.setCreateTime(new Date());
        ErrorMsgEntity errorMsg = errorMsgRepository.save(entity);

    }
}
