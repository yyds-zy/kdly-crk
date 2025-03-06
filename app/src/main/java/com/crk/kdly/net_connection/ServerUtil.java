package com.crk.kdly.net_connection;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 孙贝贝
 * @packagename com.example.a93018.videodemo
 * @date on 2019/1/28 14:13
 * @wechat 18813158027
 */
public class ServerUtil {
    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(10, TimeUnit.SECONDS).
            readTimeout(10, TimeUnit.SECONDS).
            writeTimeout(10, TimeUnit.SECONDS).build();



    private static ServerUtil instance = null;

    private final Retrofit retrofit;

    public static ServerUtil getInstance() {
        if (instance == null) {
            synchronized (ServerUtil.class) {
                if (instance == null) {
                    instance = new ServerUtil();
                }
            }
        }
        return instance;
    }

    private ServerUtil() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



    }


    public KaiyanHttpService getServer() {
        return retrofit.create(KaiyanHttpService.class);
    }




}
