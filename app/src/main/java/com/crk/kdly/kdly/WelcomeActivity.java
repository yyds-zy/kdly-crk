package com.crk.kdly.kdly;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.crk.kdly.R;
import com.crk.kdly.http.Login_Reslut;
import com.crk.kdly.http.utils;
import com.crk.kdly.net_connection.ServerUtil;
import com.crk.kdly.net_connection.Server_Response;
import com.crk.kdly.tool.SPUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class WelcomeActivity extends Activity implements Server_Response.OnGetSeverLister ,View.OnClickListener {
//    String spec_number="";
    EditText edtext_user;
    EditText edtext_pass;
    Button login;
    TextView text_show;
//    Spinner spinner;
//    ArrayAdapter<String> adapter;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        edtext_user = findViewById(R.id.username);
        edtext_pass =  findViewById(R.id.password);
        login = findViewById(R.id.button1);
        text_show =  findViewById(R.id.show_login);
//        spinner =  findViewById(R.id.spinner1);
//
//        spinner.setOnItemSelectedListener(this);
        login.setOnClickListener(this);
//        getKuSoure();//库房资源

    }
//    private void getKuSoure(){//获取库房资源
//        Call<ResponseBody> responseBodyCall = ServerUtil.getInstance().getServer().inKu();
//        Server_Response.getResponse(responseBodyCall,null,this);
//
//    }
    private void login(){//登录
        Call<ResponseBody> responseBodyCall = ServerUtil.getInstance().getServer().login(username,password);
        Server_Response.getResponse(responseBodyCall,new Login_Reslut(),this);

    }
    @Override
    public void onSuccess(Object response) {
        if (response instanceof String){
//            String result = (String) response;
//            List<String> ls_kuwei = new ArrayList<String>();
//                JSONArray arr = null;
//                try {
//                    arr = new JSONArray(result);
//                    for (int i = 0; i < arr.length(); i++) {
//                        JSONObject temp = (JSONObject) arr.get(i);
//                        String cinv_code = temp.getString("cinv_code");
//                        String cinv_std = temp.getString("cinv_std");
//                        ls_kuwei.add(cinv_code + "[" + cinv_std + "]");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//                adapter = new ArrayAdapter<String>(WelcomeActivity.this, android.R.layout.simple_spinner_item, ls_kuwei);
//                adapter.setDropDownViewResource(R.layout.drop_down_item);
//                spinner.setAdapter(adapter);


        }else if (response instanceof Login_Reslut){
            Login_Reslut result = (Login_Reslut) response;
            if (result.isSuccess()){
                SPUtils.put(this,"username",username);
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this,MainActivity.class);
//                intent.putExtra("spec_number", spec_number);
                intent.putExtra("username", username);
                startActivity(intent);
            } else{
                Toast.makeText(WelcomeActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void onFaire(String error) {
        Toast.makeText(WelcomeActivity.this, error, Toast.LENGTH_SHORT).show();

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        spec_number = (String) spinner.getSelectedItem();
//        spec_number = spec_number.substring(0, 4);
//
//    }

//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }

    @Override
    public void onClick(View v) {
        if (v==login){//登录
            username = edtext_user.getText().toString();
            password = edtext_pass.getText().toString();
            if (TextUtils.isEmpty(username)){
                Toast.makeText(WelcomeActivity.this,"请输入账号",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(WelcomeActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                return;
            }
            login();
        }


    }
}
