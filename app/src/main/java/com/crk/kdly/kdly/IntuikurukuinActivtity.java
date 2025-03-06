package com.crk.kdly.kdly;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crk.kdly.R;
import com.crk.kdly.dialog.AlterDialog;
import com.crk.kdly.dialog.OnOkClickListener;
import com.crk.kdly.http.PiPeiResult;
import com.crk.kdly.http.Ruku_Result;
import com.crk.kdly.net_connection.ServerUtil;
import com.crk.kdly.net_connection.Server_Response;
import com.crk.kdly.tool.TLog;
import com.crk.kdly.tool.Utils;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class IntuikurukuinActivtity extends Activity implements Server_Response.OnGetSeverLister, View.OnClickListener {


    /**库位*/
    TextView et_huowei;

    /**焦点*/
//    EditText et_jioadian;

    /**返回*/
    ImageView img_fanhui;

    String qrCode;

    /** 规格 */
    TextView tv_cinv_std;

    /** 数量 */
    TextView tv_base_qtyn;

    /** 单位 */
    TextView tv_unit_show;

    /** 批次 */
    TextView tv_batch_show;

    /** 生产日期 */
    TextView tv_produceDate_show;

    /** 供应商编号 */
   // TextView tv_vendorCinvCode_show;

    /** 指令号 */
    TextView tv_instruct_record_show;

    /** 入库按钮 */
    Button btn_in;

    /** 清空按钮 */
    Button btn_clear;


    String positionCode = "";

    String cinvStd;

    String baseQtyn;

    String ccomUnitName;

    String batch;

    String produceDate;

//    String vendorCinvCode;

    String instructMainId;





    /**库房*/
    String spec_number;
    String username;

    /**貨位是否可用*/
    String sfky="";





    String keepisIn;

    String aa;

    Handler handler = new Handler();
    Runnable runnable;



    private BroadcastReceiver receiver = null;
    private IntentFilter filter = null;

    /* 扫码结果接收广播地址 */
    public static final String ACTION_BROADCAST_RECEIVER = "com.android.decodewedge.decode_action";
    public static final String EXTRA_BARCODE_STRING = "com.android.decode.intentwedge.barcode_string"; /* 扫码结果 */
    public static final String EXTRA_BARCODE_TYPE = "com.android.decode.intentwedge.barcode_type";    /* 条码类型 */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtity_in_tuiku);
//src\main\res\layout\activtity_in_tuiku.xml
        Intent intent=getIntent();
        spec_number=intent.getStringExtra("spec_number");
        username=intent.getStringExtra("username");//登录名

//        et_jioadian =  findViewById(R.id.et_commodity_code1_in);
//        et_jioadian.addTextChangedListener(watcher);

        et_huowei = findViewById(R.id.et_kuwei1_in);

        img_fanhui =  findViewById(R.id.fanhui4);//返回
        tv_cinv_std =  findViewById(R.id.tv_cinv_std);
        tv_base_qtyn = findViewById(R.id.tv_base_qtyn);
        tv_unit_show =  findViewById(R.id.tv_unit_show);
        tv_batch_show =  findViewById(R.id.tv_batch_show);
        tv_produceDate_show =  findViewById(R.id.tv_produceDate_show);
//        tv_vendorCinvCode_show =  findViewById(R.id.tv_vendorCinvCode_show);
        tv_instruct_record_show =  findViewById(R.id.tv_instruct_record_show);
        btn_in =  findViewById(R.id.btn_in);//入库
        btn_clear =  findViewById(R.id.btn_clear);//清除
        btn_in.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        img_fanhui.setOnClickListener(this);
        tv_base_qtyn.setOnClickListener(this);

//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                et_jioadian.requestFocus();
//                handler.postDelayed(this,1000);
//            }
//        };
//        handler.postDelayed(runnable,1000);
    }

//    private TextWatcher watcher =new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//            aa = et_jioadian.getText().toString().trim();
//            TLog.e("onTextChanged扫描结果货位==","aa333"+aa+"bbb");
//            if (aa.length() == 10) {//货位
//                TLog.e("扫描结果货位==",aa);
//                zifuchaunchuli_huowei();
//            } else if (aa.split("~").length == 10) {//产品
//                TLog.e("扫描结果产品===",aa);
//                zifuchaunchuli_cinv();
//            }
//        }
//    };

    //
    private void zifuchaunchuli_huowei(){

        String temp=aa.substring(0,4);
        TLog.e("扫描结果产品zifuchaunchuli_huowei===",aa);
        if(!spec_number.equals(temp)){
            Toast.makeText(IntuikurukuinActivtity.this,"库房"+spec_number+"下没有此库位号", Toast.LENGTH_SHORT).show();
//            et_jioadian.setText("");
        }
        else
        {
            request_pipei(aa);
        }
//        et_jioadian.requestFocus();
    }
    //
    private void zifuchaunchuli_cinv(){

        String[] str = aa.split("~");
        //20420~605B110~605B110~63~米~B01946~0725~01202407111~13~HB0120240725001344
        // 0      1       2     3  4   5     6     7         8    9
        if(str.length==10){

            qrCode = aa;

            cinvStd = str[1];
            baseQtyn = str[3];
            ccomUnitName = str[4];
            batch = str[5];
            produceDate = str[6];
            instructMainId = str[7];

            tv_cinv_std.setText(cinvStd);
            tv_base_qtyn.setText(baseQtyn);
            tv_unit_show.setText(ccomUnitName);
            tv_batch_show.setText(batch);
            tv_produceDate_show.setText(produceDate);
            tv_instruct_record_show.setText(instructMainId);

            btn_in.setBackgroundColor(Color.rgb(132, 193, 255));
            btn_in.setEnabled(true);
//            et_jioadian.setText("");

        }  else{
            cinv_clear();
//            et_jioadian.setText("");
            Toast.makeText(IntuikurukuinActivtity.this, "二维码格式不符", Toast.LENGTH_SHORT).show();
        }

//        et_jioadian.requestFocus();
    }
    //

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    /** 判断此货位码是否可用 */
    private void request_pipei(String positionCode){//扫描库位码匹配
        Call<ResponseBody> pipei = ServerUtil.getInstance().getServer().pipei(positionCode);
        Server_Response.getResponse(pipei,new PiPeiResult(),this);
    }

    /** 点击入库按钮，执行入库操作 */
    private void request_Rk(){//点击入库
        Call<ResponseBody> rk = ServerUtil.getInstance().getServer().rk("2", qrCode, tv_base_qtyn.getText().toString(), positionCode , username);
        Server_Response.getResponse(rk,new Ruku_Result(),this);
    }

    @Override
    public void onSuccess(Object response) {
        if (response instanceof  PiPeiResult){//判断此货位码是否可用，返回结果
            TLog.e("扫描结果产品zifuchaunchuli_huowei-onSuccess===",aa);
            PiPeiResult piPeiResult = (PiPeiResult) response;
            sfky = piPeiResult.getSfky();
            if("ky".equals(sfky)){
                positionCode = aa;
                et_huowei.setText(positionCode);
                TLog.e("扫描结果产品zifuchaunchuli_huowei-onSuccess=sfky==",sfky+"-"+positionCode);
//                et_jioadian.setText("");
            }else{
//                String sfkymsg = piPeiResult.getMsg();
                TLog.e("扫描结果产品zifuchaunchuli_huowei-onSuccess=sfky==",sfky);
                Toast.makeText(IntuikurukuinActivtity.this, ""+sfky, Toast.LENGTH_SHORT).show();
                kuwei_clear();
//                et_jioadian.setText("");
            }
        }
        else
        if (response instanceof Ruku_Result){//执行入库操作 返回结果
            Ruku_Result ruku_result = (Ruku_Result) response;
            if(ruku_result.getIsIn().equals("true")){
                Toast.makeText(IntuikurukuinActivtity.this,""+ruku_result.getIsInmsg(),Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(IntuikurukuinActivtity.this,"入库成功",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFaire(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

    }

    /** 清空 */
    public void clear(){
        // TODO Auto-generated method stub
        kuwei_clear();
        cinv_clear();
    }
    /** 貨位 清空 */
    public void kuwei_clear(){
        // TODO Auto-generated method stub


        et_huowei.setText("");
        positionCode = "";

    }
    /** 產品信息 清空*/
    public void cinv_clear(){
        // TODO Auto-generated method stub


        qrCode = "";

        cinvStd = "";
        baseQtyn = "";
        ccomUnitName = "";
        batch = "";
        produceDate = "";
//        vendorCinvCode = "";
        instructMainId = "";

        tv_cinv_std.setText("");
        tv_base_qtyn.setText("");
        tv_unit_show.setText("");
        tv_batch_show.setText("");
        tv_produceDate_show.setText("");
//        tv_vendorCinvCode_show.setText("");
        tv_instruct_record_show.setText("");

    }




    @Override
    public void onClick(View v) {
        if (v== img_fanhui){//关闭当前页
            finish();


        }else if (v==btn_in){//入库
            tv_base_qtyn.clearFocus();//清除焦点
            if("".equals(cinvStd)) {
                Toast.makeText(IntuikurukuinActivtity.this,"產品信息不能為空！",Toast.LENGTH_SHORT).show();
            }
            else
            if("".equals(positionCode)){
                Toast.makeText(IntuikurukuinActivtity.this,"貨位信息不能為空！",Toast.LENGTH_SHORT).show();

            }else{
                request_Rk();
                clear();
                btn_in.setBackgroundColor(Color.GRAY);
                btn_in.setEnabled(false);
            }

        }else if (v==btn_clear){//清除
            clear();

        }else if (v == tv_base_qtyn){//修改数量
            String num = tv_base_qtyn.getText().toString().trim();
            AlterDialog dialog = new AlterDialog(IntuikurukuinActivtity.this, num, new OnOkClickListener() {
                @Override
                public void onclick(String text) {
                    tv_base_qtyn.setText(text);
                }
            });
            dialog.show();
        }
//        et_jioadian.requestFocus();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // Register dynamically decode wedge intent broadcast receiver.
        receiver = new DecodeWedgeIntentReceiver();
        filter = new IntentFilter();
        filter.addAction(ACTION_BROADCAST_RECEIVER);

        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister our BroadcastReceiver.
        unregisterReceiver(receiver);
        receiver = null;
        filter = null;
    }


    // Receives action ACTION_BROADCAST_RECEIVER
    public class DecodeWedgeIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent wedgeIntent) {
            String action = wedgeIntent.getAction();
            if (action.equals(ACTION_BROADCAST_RECEIVER)) {
                 aa = wedgeIntent.getStringExtra(EXTRA_BARCODE_STRING);
                if (aa != null) {
//                    TLog.e("扫描结果", aa);
                    if (null != aa && aa.length() > 0 ){
                        TLog.e("onTextChanged扫描结果货位==","aa333"+aa+"bbb");
                        if (aa.length() == 10) {//货位
                            TLog.e("扫描结果货位==",aa);
                            zifuchaunchuli_huowei();
                        } else if (aa.split("~").length == 10) {//产品
                            TLog.e("扫描结果产品===",aa);
                            zifuchaunchuli_cinv();
                        }
                    }
                }
            }
        }
    }
}
