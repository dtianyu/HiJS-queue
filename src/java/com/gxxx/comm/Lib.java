/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gxxx.comm;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonStructure;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

/**
 *
 * @author C0160
 */
public class Lib {

    public static void buildJson(JsonStructure value, String fileFullName) throws IOException {

        /* Write formatted JSON Output */
        Map<String, String> config = new HashMap<>();
        config.put(JsonGenerator.PRETTY_PRINTING, "");
        JsonWriterFactory factory = Json.createWriterFactory(config);

        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = factory.createWriter(stringWriter)) {
            jsonWriter.write(value);
            jsonWriter.close();
        }
        //建立文件
        File jsonFile = new File(fileFullName);
        if (!jsonFile.exists()) {
            jsonFile.createNewFile();
        }
        //保存内容
        OutputStream outputStream;
        outputStream = new FileOutputStream(jsonFile);
        outputStream.write(stringWriter.toString().getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();

    }

    public static String formatDate(String format, Date date) {
        if (format != null && date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } else {
            return "";
        }
    }

    public static Date getDate() {
        return Calendar.getInstance().getTime();
    }

    public static String getLocalOperateMessage(String value) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "i18n");
        return bundle.getString(value);
    }

    public static String securityMD5(String str) throws UnsupportedEncodingException {
        MessageDigest messageDigest = null;
        byte[] byteArray = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
            byteArray = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(Lib.class.getName()).log(Level.SEVERE, null, e);
        }
        StringBuilder md5Buff = new StringBuilder();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5Buff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5Buff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5Buff.toString();

    }

    public static void sendShortMessagePassword(String mobile, String value) {

        HashMap<String, Object> result = null;
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init("app.cloopen.com", "8883");
        restAPI.setAccount("aaf98f894d22eb10014d2758e69a0413", "45c42156fa5848baa695270f27adfe6c");
        restAPI.setAppId("8a48b5514d232afc014d2760881102ec");
        result = restAPI.sendTemplateSMS(mobile, "23845", new String[]{mobile, value});

        if (!"000000".equals(result.get("statusCode"))) {
            //异常返回输出错误码和错误信息
            Logger.getLogger("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
        }

    }

    public static void sendShortMessageVerifyCode(String mobile, String value) {

        HashMap<String, Object> result = null;
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init("app.cloopen.com", "8883");
        restAPI.setAccount("aaf98f894d22eb10014d2758e69a0413", "45c42156fa5848baa695270f27adfe6c");
        restAPI.setAppId("8a48b5514d232afc014d2760881102ec");
        result = restAPI.sendTemplateSMS(mobile, "23844", new String[]{mobile, value, "3"});

        if (!"000000".equals(result.get("statusCode"))) {
            //异常返回输出错误码和错误信息
            Logger.getLogger("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
        }

    }

    public static void sendShortMessageForCustomer(String mobile, String vendor, String content, String money) {

        HashMap<String, Object> result = null;
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init("app.cloopen.com", "8883");
        restAPI.setAccount("aaf98f894d22eb10014d2758e69a0413", "45c42156fa5848baa695270f27adfe6c");
        restAPI.setAppId("8a48b5514d232afc014d2760881102ec");
        result = restAPI.sendTemplateSMS(mobile, "23846", new String[]{vendor, content, money});

        if (!"000000".equals(result.get("statusCode"))) {
            //异常返回输出错误码和错误信息
            Logger.getLogger("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
        }

    }

    public static void sendShortMessageForVendor(String mobile, String customer, String content, String money, String address) {

        HashMap<String, Object> result = null;
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
        restAPI.init("app.cloopen.com", "8883");
        restAPI.setAccount("aaf98f894d22eb10014d2758e69a0413", "45c42156fa5848baa695270f27adfe6c");
        restAPI.setAppId("8a48b5514d232afc014d2760881102ec");
        result = restAPI.sendTemplateSMS(mobile, "23847", new String[]{customer, content, money, address});

        if (!"000000".equals(result.get("statusCode"))) {
            //异常返回输出错误码和错误信息
            Logger.getLogger("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
        }

    }

}
