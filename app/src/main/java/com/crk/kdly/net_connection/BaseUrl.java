package com.crk.kdly.net_connection;

import com.crk.kdly.tool.TLog;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author 孙贝贝
 * @packagename com.example.changtongyuan.net_connection
 * @date on 2019/5/21 15:33
 * @wechat 18813158027
 */
public class BaseUrl {

    public static RequestBody getBody(String json) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return body;
    }

    public static String toJson(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    public static RequestBody getBody2(Object o) {
        TLog.e("传值参数" + toJson(o));

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), toJson(o));
        return body;
    }


//    public static String url = "http://192.168.0.4:8080/cdly/a/mainfunction/wms/";//现场
//    public static String url="http://172.16.136.223/8080/cdly/a/mainfunction/wms/";//现场
    public static String url="http://172.16.140.7:8080/cdly/a/mainfunction/wms/";//现场


}
