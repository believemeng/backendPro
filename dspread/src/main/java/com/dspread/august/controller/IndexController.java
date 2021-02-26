/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.dspread.august.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dspread.august.common.AESUtil;
import com.dspread.august.common.ErrorCode;
import com.dspread.august.common.JsonResult;
import com.dspread.august.common.safety.SafetyNetResponse;
import com.dspread.august.common.wbaes.AES;
import com.dspread.august.common.wbaes.AESGenerator;
import com.dspread.august.exception.CustomerException;
import com.dspread.august.model.*;
import com.dspread.august.service.CommonService;
import com.dspread.august.util.Envelope;
import com.dspread.august.util.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Date;

// file deepcode ignore XSS: <comment the reason here>
@RestController
@RequestMapping(value = "/api/")
@Api(tags = "spoc index 服务接口")
public class IndexController {
    @Autowired
    private CommonService commonService;

    /**
     * 测试接口index
     *
     * @return String
     */
//    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @GetMapping(value = "/index")
    @ApiOperation(value = "测试接口", notes = "测试连接，返回 SHARING AND CREATOR!")
    public String hello() {
        return "SHARING AND CREATOR!";
    }

    /**
     * 监听接口
     *
     * @param monitorModel 监听数据
     * @return JsonResult
     */
//    @RequestMapping(value = "/monitor", method = {RequestMethod.POST, RequestMethod.GET})
    @PostMapping(value = "/monitor")
    @ApiOperation(value = "监控接口", notes = "未作修改")
//    @ApiImplicitParam(name = "MonitorModel", value = "监控信息实体", paramType = "query", dataType = "MonitorModel", required = true)
    public JsonResult monitor(@RequestBody MonitorModel monitorModel) {
        return commonService.addMonitor(monitorModel);//  deepcode ignore XSS: <comment the reason here>
    }

    /**
     * 上传交易信息
     *
     * @param ransactionModel 交易数据
     * @return JsonResult
     */
//    @RequestMapping(value = "/transaction", method = {RequestMethod.POST, RequestMethod.GET})
    @PostMapping(value = "/transaction")
    @ApiOperation(value = "上传交易信息", notes = "未作修改")
//    @ApiImplicitParam(name = "TransactionModel", value = "交易数据", paramType = "query", dataType = "TransactionModel", required = true)
    public JsonResult transaction(@RequestBody TransactionModel ransactionModel) {
        return commonService.addTransaction(ransactionModel);//  deepcode ignore XSS: <comment the reason here>
    }

    /**
     * 获取数字信封
     *
     * @param model 设备公钥model
     * @return JsonResult
     */
//    @RequestMapping(value = "/envelop", method = {RequestMethod.POST, RequestMethod.GET})
    @PostMapping(value = "/envelop")
    @ApiOperation(value = "获取数字信封")
//    @ApiImplicitParam(name = "model", value = "设备公钥model", paramType = "query", dataType = "String", required = true)
    public JsonResult generateEnv(@RequestBody String model) {
        String s = null;
        JsonResult jsonResult = new JsonResult();
        try {
            s = Envelope.generateTokenEnvelopStr(model);
            jsonResult.setData(s);
        } catch (Exception e) {
            jsonResult.setCode(ErrorCode.ERROR_GENERATE_ENVELOP.getErrorCode());
            jsonResult.setMsg("error generate envelop");
            return jsonResult;
        }
        return jsonResult;
    }

    /**
     * 获取设备状态
     *
     * @param safetyNetModel nonce+safetyNet jws
     * @return JsonResult
     */
//    @RequestMapping(value = "/getDeviceStatus", method = {RequestMethod.POST, RequestMethod.GET})
    @PostMapping(value = "/getDeviceStatus")
    @ApiOperation(value = "获取设备状态")
//    @ApiImplicitParam(name = "SafetyNetModel", value = "nonce+safetyNet jws", paramType = "query", dataType = "SafetyNetModel", required = true)
    public JsonResult getDeviceStatus(@RequestBody SafetyNetModel safetyNetModel) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();
        String id = session.getId();
        String nonce = (String) session.getAttribute("nonce");
        request.getSession().invalidate();
        if (StringUtils.isEmpty(nonce) || (safetyNetModel != null && StringUtils.isEmpty(safetyNetModel.getNonce())) || !nonce.equalsIgnoreCase(safetyNetModel.getNonce())) {
            throw new CustomerException(ErrorCode.ERROR_RANDOM_VERIFY, "nonce verify error");
        }
        JsonResult jsonResult = new JsonResult();
        try {
            String jwsResult = safetyNetModel.getJws();
            final String[] jwtParts = jwsResult.split("\\.");

            if (jwtParts.length == 3) {
                //we're only really interested in the body/payload
                Base64.Decoder decoder = Base64.getDecoder();
                String decodedPayload = new String(decoder.decode(jwtParts[1]));
//                System.out.println("result = " + decodedPayload);
                SafetyNetResponse parse = SafetyNetResponse.parse(decodedPayload);
                String header = request.getHeader("User-Agent");

                if (!parse.isBasicIntegrity() || !parse.isCtsProfileMatch()){
                    jsonResult.setMsg("Attestation Failed!".concat(parse.getAdvice()));
                    jsonResult.setData(false);

                    commonService.saveErrorMsg(new ErrorMsgModel(2,request.getHeader("TerminalId"),safetyNetModel.toString()));
                }else {
                    if (commonService.verifyCots(header)){
                        jsonResult.setMsg("Attestation Success!");
                        jsonResult.setData(true);
                    }else {
                        jsonResult.setMsg("Attestation Failed!".concat("cots system version not suupor"));
                        jsonResult.setData(false);
                    }
                }
            } else {
//                throw new NullPointerException();
            }
        } catch (Exception e) {

            jsonResult.setCode(ErrorCode.ERROR_Attestation_COTS.getErrorCode());
            jsonResult.setMsg("error Attestation");
            commonService.saveErrorMsg(new ErrorMsgModel(2,request.getHeader("TerminalId"),jsonResult.toString()));

            return jsonResult;
        }
        return jsonResult;
    }

    /**
     * 获取随机数
     *
     * @return JsonResult
     */
//    @RequestMapping(value = "/getRandomNumber", method = {RequestMethod.POST, RequestMethod.GET})
    @PostMapping(value = "/getRandomNumber")
    @ApiOperation(value = "获取随机数")
    public JsonResult getRandomNumber() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        JsonResult jsonResult = new JsonResult();
        String secureRandom;
        try {
            secureRandom = AESUtil.generateRandom();
            HttpSession session = request.getSession();
            String id = session.getId();
            session.setAttribute("nonce", secureRandom);
            jsonResult.setData(secureRandom);
        } catch (Exception e) {

            jsonResult.setCode(ErrorCode.ERROR_GENERATE_RANDOM.getErrorCode());
            jsonResult.setMsg("error GENERATE RANDOM");
            commonService.saveErrorMsg(new ErrorMsgModel(0,request.getHeader("TerminalId"),jsonResult.toString()));

            return jsonResult;
        }
        return jsonResult;
    }

    /**
     * 验证app完整性
     *
     * @param appSignature 应用签名信息
     * @return JsonResult
     */

//    {"nonce":"WCSaQHzcpTEFfU+NojmMOy68XSi4DRGyq5mu/J9G5RM=",
//            "timestampMs":1602660974149,
//            "apkPackageName":"com.scottyab.safetynet.sample.debug",
//            "apkDigestSha256":"lE5aCuKNZ4Om65Oc6BfUR0aAJyujpWcqPiMhfTPw9VE=",
//            "ctsProfileMatch":true,
//            "apkCertificateDigestSha256":["ZEfXS3RiKVA5CnY321oj8GR5VAeV6G8v7gfNBrf/etY="],
//        "basicIntegrity":true,
//            "evaluationType":"BASIC,HARDWARE_BACKED"}

//    @RequestMapping(value = "/verifyAppSignature", method = {RequestMethod.POST, RequestMethod.GET})
    @PostMapping(value = "/verifyAppSignature")
    @ApiOperation(value = "验证app完整性")
//    @ApiImplicitParam(name = "appSignature", value = "应用签名信息", paramType = "query", dataType = "String", required = true)
    public JsonResult verifyAppSignature(@RequestBody String appSignature) {
        String[] split = appSignature.split("\":\"");
//        return "06:27:09:D4:CB:97:7A:46:E7:C1:07:2F:1E:6E:02:3E:31:57:31:12".equalsIgnoreCase(split[1].substring(0,split[1].length()-2)) ? JsonResult.SUCCESS : JsonResult.Fail;
        return "C7:A2:03:C4:1D:04:28:9A:C2:7D:C1:2C:27:73:38:4E:C3:BE:3C:45".equalsIgnoreCase(appSignature) ? JsonResult.SUCCESS : JsonResult.Fail;

    }


    /**
     * 验证SCRP
     *
     * @param terminalInfoModel scrp设备信息
     * @return JsonResult
     */
//    @RequestMapping(value = "/verifySCRP", method = {RequestMethod.POST, RequestMethod.GET})
    @PostMapping(value = "/verifySCRP")
    @ApiOperation(value = "验证SCRP")
//    @ApiImplicitParam(name = "TerminalInfoModel", value = "scrp设备信息", paramType = "query", dataType = "TerminalInfoModel", required = true)
    public JsonResult verifySCRP(@RequestBody TerminalInfoModel terminalInfoModel) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        JsonResult jsonResult = null;
        try {
            jsonResult = commonService.verifyScrp(terminalInfoModel);
        } catch (Exception e) {
            jsonResult = new JsonResult();
            jsonResult.setCode(ErrorCode.ERROR_VERIFY_SCRP.getErrorCode());
            jsonResult.setMsg("error VERIFY SCRP");
            commonService.saveErrorMsg(new ErrorMsgModel(1,request.getHeader("TerminalId"),jsonResult.toString()));

            return jsonResult;
        }


        return jsonResult;
    }


    /**
     * 生成AESTable文件
     *
     * @return
     */
//    @RequestMapping(value = "/generateAESTable", method = {RequestMethod.POST, RequestMethod.GET})
    @PostMapping(value = "/generateAESTable")
    @ApiOperation(value = "生成AESTable文件")
    public void generateAESTable() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        HttpServletRequest request = attributes.getRequest();
        ObjectOutputStream out = null;
        OutputStream output = null;
        try {
            AES generate = AESGenerator.generate(Utils.hexStringToBytes("00000000000000000000000000000000"));
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
            response.setHeader("Last-Modified", new Date().toString());
            response.setHeader("ETag", String.valueOf(System.currentTimeMillis()));
            response.setHeader("content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("aes-table", "utf-8"));
            output = response.getOutputStream();
            out = new ObjectOutputStream(output);
            out.writeObject(generate);
            out.flush();
            out.close();
        } catch (Exception e) {
//            e.printStackTrace();
            commonService.saveErrorMsg(new ErrorMsgModel(0,request.getHeader("TerminalId"),e.getMessage()));

        }

    }

}

