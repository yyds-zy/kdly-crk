package com.crk.kdly.net_connection;

import android.util.Log;

import com.crk.kdly.tool.TLog;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author 孙贝贝
 * @packagename com.example.a93018.videodemo
 * @date on 2019/1/29 10:11
 * @wechat 18813158027
 */
public class Server_Response {

    public static void getResponse(Call<ResponseBody> kaiyanCall, final Object o, final OnGetSeverLister lister){
        kaiyanCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                int code = response.code();
                if (code!=200){
                    lister.onFaire("服务器异常"+code);
                    return;
                }


                String s1 = response.toString();

                Log.e("网络地址",s1);

                try {

                    String s = new String(response.body().bytes());
                    Log.e("获取的结果",s);
                    if (o!=null){
                        Gson gson =new Gson();
                        Object o1 = gson.fromJson(s, o.getClass());
                        lister.onSuccess(o1);

                    }else{
                        lister.onSuccess(s);
                    }






                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
               TLog.e("异常",t.getMessage());
               lister.onFaire("网络异常请稍后重试");

            }
        });
    }
    public interface OnGetSeverLister{
        void onSuccess(Object response);
        void onFaire(String error);
    }
}
