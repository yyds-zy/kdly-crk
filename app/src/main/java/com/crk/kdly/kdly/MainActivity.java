package com.crk.kdly.kdly;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.crk.kdly.R;
import com.crk.kdly.kdly.InOneActivity;
import com.crk.kdly.kdly.InYuanLiaoActivity;
import com.crk.kdly.kdly.IntuikurukuinActivtity;
import com.crk.kdly.net_connection.ServerUtil;
import com.crk.kdly.net_connection.Server_Response;
import com.crk.kdly.tool.SPUtils;
import com.crk.kdly.tool.TLog;
import com.crk.kdly.tool.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class MainActivity extends Activity implements Server_Response.OnGetSeverLister , AdapterView.OnItemSelectedListener , View.OnClickListener {

	Button btn_in_one;
	Button btn_out_one;
	Button out_yuanliao;
	Button btn_out_two;
	Button in_chengpin;
	Button in_tukuyxgin;
//	String spec_number;
    String spec_number="";
    String username;

    //库房下拉框
    ArrayAdapter<String> adapter;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
//        spec_number=intent.getStringExtra("spec_number");
//        TLog.e("标号",spec_number);

        btn_in_one =  findViewById(R.id.in_one);//原材料入库
        btn_out_one =  findViewById(R.id.out_one);//成品出库
        out_yuanliao =  findViewById(R.id.out_yuanliao);//原材料出库
        in_chengpin =  findViewById(R.id.in_chengpin);//成品入库
        in_tukuyxgin =  findViewById(R.id.in_tukuyxgin);//退库入库-半成品（异型管中转库的）

        username=intent.getStringExtra("username");
        System.out.println("-----username---"+username);

        btn_in_one.setOnClickListener(this);
        btn_out_one.setOnClickListener(this);
        out_yuanliao.setOnClickListener(this);
        in_chengpin.setOnClickListener(this);
        in_tukuyxgin.setOnClickListener(this);

        spinner =  findViewById(R.id.spinner1);

        spinner.setOnItemSelectedListener(this);
        getKuSoure();//库房资源

    }
    private void getKuSoure(){//获取库房资源
        Call<ResponseBody> responseBodyCall = ServerUtil.getInstance().getServer().inKu(username);
        Server_Response.getResponse(responseBodyCall,null,this);

    }


    @Override
    public void onClick(View v) {
        if (v==btn_in_one){//原材料入库
            if(!"".equals(spec_number)){
                Intent intent =new Intent();
                intent.putExtra("spec_number",spec_number);
                intent.setClass(this, InOneActivity.class);
                startActivity(intent);
            }
        }
        else if (v==in_chengpin){//成品入库
            if(!"".equals(spec_number)) {
                if("8401".equals(spec_number)
                    ||
                    "8423".equals(spec_number)
                    ||
                    "8427".equals(spec_number)
                    ||
                    "8431".equals(spec_number)
                    ||
                    "8416".equals(spec_number)  // 硬管按指令扫码入库
                    ||
                    "8414".equals(spec_number)  // 硬管按指令扫码入库
                ){
                    Intent intent = new Intent();
                    intent.putExtra("spec_number", spec_number);
                    intent.putExtra("username", username);
                    intent.setClass(this, InYuanLiaoActivity.class);
                    startActivity(intent);
                }else{
                    Utils.showDialogVersionzdl(MainActivity.this, "请选择库房，或者联系管理员。");

                }


            }
        }
        else if (v==in_tukuyxgin){//退库入库-半成品（异型管中转库的）
            if(!"".equals(spec_number)) {
                if(
                    "8416".equals(spec_number)  // 硬管按指令扫码入库
                    ||
                    "8414".equals(spec_number)  // 硬管按指令扫码入库
                ){
                    Intent intent = new Intent();
                    intent.putExtra("spec_number", spec_number);
                    intent.putExtra("username", username);
                    intent.setClass(this, IntuikurukuinActivtity.class);
                    startActivity(intent);
                }else{
                    Utils.showDialogVersionzdl(MainActivity.this, "请选择库房，或者联系管理员。");

                }


            }
        }
        else if (v==out_yuanliao){//原材料出库
            if(!"".equals(spec_number)) {
                Intent intent = new Intent();
                intent.putExtra("spec_number", spec_number);
                intent.putExtra("chorc", "c");
                intent.setClass(this, OutYuanLiaoActivity.class);
                startActivity(intent);
            }
        }else if (v==btn_out_one){//成品出库
            if(!"".equals(spec_number)) {
                Intent intent = new Intent();
                intent.putExtra("spec_number", spec_number);
                intent.putExtra("chorc", "ch");
                intent.setClass(this, OutYuanLiaoActivity.class);
                startActivity(intent);
            }
        }else{
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spec_number = (String) spinner.getSelectedItem();
        spec_number = spec_number.substring(0, 4);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onSuccess(Object response) {
        if (response instanceof String){
            String result = (String) response;
            List<String> ls_kuwei = new ArrayList<String>();
            JSONArray arr = null;
            try {
                arr = new JSONArray(result);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject temp = (JSONObject) arr.get(i);
                    String cinv_code = temp.getString("cinv_code");
                    String cinv_std = temp.getString("cinv_std");
                    ls_kuwei.add(cinv_code + "[" + cinv_std + "]");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, ls_kuwei);
            adapter.setDropDownViewResource(R.layout.drop_down_item);
            spinner.setAdapter(adapter);
        }else{
        }
    }

    @Override
    public void onFaire(String error) {
        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
    }
}
