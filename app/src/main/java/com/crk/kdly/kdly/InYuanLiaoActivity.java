package com.crk.kdly.kdly;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.crk.kdly.R;
import com.crk.kdly.dialog.AlterDialog;
import com.crk.kdly.dialog.OnOkClickListener;
import com.crk.kdly.http.utils;
import com.crk.kdly.net_connection.ServerUtil;
import com.crk.kdly.net_connection.Server_Response;
import com.crk.kdly.tool.TLog;
import com.crk.kdly.tool.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * 成品入库
 */
public class InYuanLiaoActivity extends Activity implements Server_Response.OnGetSeverLister , AdapterView.OnItemSelectedListener , View.OnClickListener{

	BigDecimal b = new BigDecimal(0);
		TextView et_location_code;

		EditText et_commodity_code;
		ImageView img_fanhui;
		
		String content;
		String content_p;

		String bb;

		TextView tv_qtysumin;

		TextView tv_qrcode_show;
		String qrcodeShow;

		Button btn_in;
		Button btn_clear;

		String positionCode = "";

		String cinvStd;

		String serialNumber;

		String spec_number;

		String keepCinvStd;

		String keepcoding;

		String keepname;

		String username;

		MyHandler handler;
		String keepisIn;

	//自定义备注下拉框
	ArrayAdapter<String> adapter2;
	Spinner spinner2;
	String zdybz = "";

	Handler handler1 = new Handler();
	Runnable runnable;

	/* 扫码结果接收广播地址 */
	public static final String ACTION_BROADCAST_RECEIVER = "com.android.decodewedge.decode_action";
	public static final String EXTRA_BARCODE_STRING = "com.android.decode.intentwedge.barcode_string"; /* 扫码结果 */
	public static final String EXTRA_BARCODE_TYPE = "com.android.decode.intentwedge.barcode_type";    /* 条码类型 */


	private BroadcastReceiver receiver = null;
	private IntentFilter filter = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_in_yuan_liao);
		
		Intent intent=getIntent();
		spec_number=intent.getStringExtra("spec_number");//库房
		username=intent.getStringExtra("username");//登录名

		System.out.println("spec_number---"+spec_number);
		System.out.println("username---"+username);

		tv_qtysumin = (TextView) findViewById(R.id.tv_qtysumin);//计数

		et_location_code = (TextView) findViewById(R.id.et_kuwei2_in);//货位
		et_commodity_code = (EditText) findViewById(R.id.et_commodity_code_in);//焦点
		et_location_code.setEnabled(false);
		
		img_fanhui = (ImageView) findViewById(R.id.fanhui1);//返回按钮

		tv_qrcode_show = (TextView) findViewById(R.id.tv_qrcode_show);//二维码显示

		btn_in = (Button) findViewById(R.id.btn_in1);//入库按钮
		btn_clear = (Button) findViewById(R.id.btn_clear1);//清空按钮
		
		handler = new MyHandler();
		
	    init(); 
	    clear();
        back();
        //清空按钮增加点击事件
        btn_clear.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View arg0) {
    			clear();
    		}
    	});

        et_location_code.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String num = et_location_code.getText().toString().trim();
				AlterDialog dialog =  new AlterDialog(InYuanLiaoActivity.this, num, new OnOkClickListener() {
					@Override
					public void onclick(String text) {
						et_location_code.setText(text);
					}
				});
				dialog.show();
			}
		});

//		runnable = new Runnable() {
//			@Override
//			public void run() {
//				et_commodity_code.requestFocus();
//				handler1.postDelayed(this,1000);
//			}
//		};
//		handler1.postDelayed(runnable,1000);

		//自定义备注
		spinner2 =  findViewById(R.id.spinner2);
		spinner2.setOnItemSelectedListener(this);
		getZidingyi();//自定义备注
	}

	private void getZidingyi(){//获取库房资源
		Call<ResponseBody> responseBodyCall = ServerUtil.getInstance().getServer().zidingyibzh(username);
		Server_Response.getResponse(responseBodyCall,null,this);

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


	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler1.removeCallbacks(runnable);
	}


	// Receives action ACTION_BROADCAST_RECEIVER
	public class DecodeWedgeIntentReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent wedgeIntent) {
			String action = wedgeIntent.getAction();
			if (action.equals(ACTION_BROADCAST_RECEIVER)) {
				// Read content of result intent.
				String aa = wedgeIntent.getStringExtra(EXTRA_BARCODE_STRING);
				if (aa != null) {
					if(null != aa && !("".equals(aa)) ){
						String temp=aa.substring(0,4);
						if(aa.length()>0 && aa.length()<=15){
							TLog.e("库位号比较",spec_number+"----" + temp);
							//Toast.makeText(InYuanLiaoActivity.this, aa + "  库位号长度：" + aa.length(), Toast.LENGTH_SHORT).show();
							if(spec_number.equals(temp)){
								clear();
								aa = aa.trim();
								if(aa.length() != 10) {
									Utils.showDialogVersionzdl(InYuanLiaoActivity.this, aa + "\r\n" + "正确的库位号，长度是10位。");
									et_commodity_code.setText("");
									return;
								}
								positionCode = aa;
								et_location_code.setText(positionCode);
								MyThread_p  mythread1 = new MyThread_p();
								Thread thread2 = new Thread(mythread1);
								thread2.start();
							}  else{
								Utils.showDialogVersionzdl(InYuanLiaoActivity.this, "库房"+spec_number+"下没有此库位号");
							}
							et_commodity_code.setText("");

						}else if(aa.length()>15){

							if("".equals(positionCode)){
								Utils.showDialogVersionzdl(InYuanLiaoActivity.this, "请先扫描货位号");
								et_commodity_code.setText("");
							}else if(!("".equals(positionCode))){

								String[] str = aa.split("~");

								if(str.length != 10){
									clear();
									Utils.showDialogVersionzdl(InYuanLiaoActivity.this, "二维码格式不符");
								}else{

									if(null == bb || bb.isEmpty()){
										bb = aa;
										qrcodeShow = "\n 行1：[" + str[1]+"~"+str[5]+"~"+str[3]+"]  \n ";
										b = new BigDecimal(String.valueOf(str[3]));
										//TLog.e("123","--0-b---"+b);
										//TLog.e("123","--0-qrcodeShow---"+qrcodeShow);
									}else{
										bb = bb + "," + aa;
										b = new BigDecimal (0);
										String[] arr = bb.split(",");
										for(String strarr : arr){
											b = b.add(new BigDecimal(String.valueOf(strarr.split("~")[3])));
										}
										qrcodeShow = qrcodeShow +  " 行"+ bb.split(",").length +"：[" + str[1]+"~"+str[5]+"~"+str[3]+"]  \n ";

										//TLog.e("123","--1-b---"+b);
										//TLog.e("123","--1-qrcodeShow---"+qrcodeShow);
									}

									if("".equals(keepCinvStd) ){// 验证 库位是否可用，返回空字符串，表示可用。
										//TLog.e("123","---b---"+b);
										//TLog.e("123","---qrcodeShow---"+qrcodeShow);

										tv_qtysumin.setText(""+b);
										tv_qrcode_show.setText(qrcodeShow);

										btn_in.setBackgroundColor(Color.rgb(132, 193, 255));
										btn_in.setEnabled(true);

										btn_in.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View arg0) {
												// TODO Auto-generated method stub
												MyThread  mythread = new MyThread();
												Thread thread1 = new Thread(mythread);
												thread1.start();
												clear();
												btn_in.setBackgroundColor(Color.GRAY);
												btn_in.setEnabled(false);
											}
										});
										et_commodity_code.setText("");
									}else{
										Utils.showDialogVersionzdl(InYuanLiaoActivity.this, "该货位已被占用或不存在");
										kuwei_clear();

										btn_in.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View arg0) {
												// TODO Auto-generated method stub
												Utils.showDialogVersionzdl(InYuanLiaoActivity.this, "入库失败,尝试再次入库，或者联系管理员。");
												clear();
											}
										});
									}

								}

							}
						}
					}
				}
			}
		}
	}

	public void init(){
		

//		et_commodity_code.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//					int arg3) {
//				// TODO Auto-generated method stub
//			}
//
//			@SuppressLint("ShowToast") @Override
//			public void afterTextChanged(Editable arg0) {
//				// TODO Auto-generated method stub
//				String aa = et_commodity_code.getText().toString();
//
//				if(null != aa && !("".equals(aa)) ){
//					String temp=aa.substring(0,4);
//					if(aa.length()>0 && aa.length()<=15){
//						TLog.e("库位号比较",spec_number+"----"+temp);
//						if(spec_number.equals(temp)){
//								clear();
//								if(aa.length() == 10){
//
//								}  else{
//									Utils.showDialogVersionzdl(InYuanLiaoActivity.this, "正确的库位号，长度是10位。");
//									et_commodity_code.setText("");
//								}
//
//								positionCode = aa;
//
//								et_location_code.setText(positionCode);
//
//								MyThread_p  mythread1 = new MyThread_p();
//							     Thread thread2 = new Thread(mythread1);
//							     thread2.start();
//
//							}  else{
//								Utils.showDialogVersionzdl(InYuanLiaoActivity.this, "库房"+spec_number+"下没有此库位号");
//							}
//						    et_commodity_code.setText("");
//
//					}else if(aa.length()>15){
//
//							if("".equals(positionCode)){
//								Utils.showDialogVersionzdl(InYuanLiaoActivity.this, "请先扫描货位号");
//								et_commodity_code.setText("");
//							}else if(!("".equals(positionCode))){
//
//								String[] str = aa.split("~");
//
//								if(str.length != 10){
//									clear();
//									Utils.showDialogVersionzdl(InYuanLiaoActivity.this, "二维码格式不符");
//								}else{
//
//									if(null == bb || bb.isEmpty()){
//										bb = aa;
//										qrcodeShow = "\n 行1：[" + str[1]+"~"+str[5]+"~"+str[3]+"]  \n ";
//										 b = new BigDecimal(String.valueOf(str[3]));
//										//TLog.e("123","--0-b---"+b);
//										//TLog.e("123","--0-qrcodeShow---"+qrcodeShow);
//									}else{
//										bb = bb + "," + aa;
//										b = new BigDecimal (0);
//										String[] arr = bb.split(",");
//										for(String strarr : arr){
//											b = b.add(new BigDecimal(String.valueOf(strarr.split("~")[3])));
//										}
//										qrcodeShow = qrcodeShow +  " 行"+ bb.split(",").length +"：[" + str[1]+"~"+str[5]+"~"+str[3]+"]  \n ";
//
//										//TLog.e("123","--1-b---"+b);
//										//TLog.e("123","--1-qrcodeShow---"+qrcodeShow);
//									}
//
//								if("".equals(keepCinvStd) ){// 验证 库位是否可用，返回空字符串，表示可用。
//									//TLog.e("123","---b---"+b);
//									//TLog.e("123","---qrcodeShow---"+qrcodeShow);
//
//									tv_qtysumin.setText(""+b);
//									tv_qrcode_show.setText(qrcodeShow);
//
//									 btn_in.setBackgroundColor(Color.rgb(132, 193, 255));
//									 btn_in.setEnabled(true);
//
//									 btn_in.setOnClickListener(new View.OnClickListener() {
//
//											@Override
//											public void onClick(View arg0) {
//												// TODO Auto-generated method stub
//												 MyThread  mythread = new MyThread();
//											     Thread thread1 = new Thread(mythread);
//											     thread1.start();
//											     clear();
//											    btn_in.setBackgroundColor(Color.GRAY);
//											    btn_in.setEnabled(false);
//											}
//										});
//									et_commodity_code.setText("");
//								}else{
//									Utils.showDialogVersionzdl(InYuanLiaoActivity.this, "该货位已被占用或不存在");
//									kuwei_clear();
//
//									 btn_in.setOnClickListener(new View.OnClickListener() {
//
//											@Override
//											public void onClick(View arg0) {
//												// TODO Auto-generated method stub
//												Utils.showDialogVersionzdl(InYuanLiaoActivity.this, "入库失败,尝试再次入库，或者联系管理员。");
//												clear();
//											}
//										});
//								}
//
//								}
//
//							}
//						}
//				}
//
//			}
//		});
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		zdybz = (String) spinner2.getSelectedItem();
//		 zdybz = zdybz.split("~")[1];
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	@Override
	public void onSuccess(Object response) {
		if (response instanceof String){
			String result = (String) response;
			List<String> ls_zdybz = new ArrayList<String>();
			JSONArray arr = null;
			try {
				arr = new JSONArray(result);
				for (int i = 0; i < arr.length(); i++) {
					JSONObject temp = (JSONObject) arr.get(i);
//					String cinv_code = temp.getString("zdybzcode");
					String cinv_std = temp.getString("zdybzvalue");
//					ls_zdybz.add(cinv_code + "" + cinv_std );
					ls_zdybz.add(cinv_std);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			adapter2 = new ArrayAdapter<String>(InYuanLiaoActivity.this, android.R.layout.simple_spinner_item, ls_zdybz);
			adapter2.setDropDownViewResource(R.layout.drop_down_item2);
			spinner2.setAdapter(adapter2);
		}else{
		}
	}

	@Override
	public void onFaire(String error) {
		Toast.makeText(InYuanLiaoActivity.this, error, Toast.LENGTH_SHORT).show();
	}


	class MyThread implements Runnable  
    {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String str = dohttpClientPost_p();

		     try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Message message=new Message();  
           Bundle bundle=new Bundle();  
           bundle.putString("isIn", str);  
           message.setData(bundle);
           handler.sendMessage(message);
           message.what=2;
		}
    	
    }
	class MyThread_p implements Runnable
    {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String str = dohttpClientPost();
		}

    }

	
	class MyHandler extends Handler  
    {  
    	

        @Override  
        public void handleMessage(Message msg) {  
            // TODO Auto-generated method stub  
            super.handleMessage(msg);
            
            String result = msg.getData().getString("isIn");
			JSONObject temp=null;
			try {
				temp = new JSONObject(result);

				keepisIn =temp.getString("isIn");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            if(msg.what==1)  
            {
            	if(!cinvStd.equals("")){
            	}else{

            	}
            	
            	
            }
            if(msg.what==2){
            	try {
					if(keepisIn.equals("true")){
						clear();
						Utils.showDialogVersionzdl(InYuanLiaoActivity.this, temp.getString("isInmsg"));
					}else{
						clear();
						Utils.showDialogVersionzdl(InYuanLiaoActivity.this, temp.getString("isInmsg"));
					}
				}catch (Exception e){
            		e.printStackTrace();
				}

            }
        }
    }

	private String dohttpClientPost(){ //库房验证是否可用
	    TLog.e("比较库位请求参数",positionCode+"abc");
		HttpClient client = new DefaultHttpClient();

		HttpPost post = new HttpPost(utils.url+"getCinvStd");

		ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();

		list.add(new BasicNameValuePair("positionCode",positionCode));
		try {
			post.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
		HttpResponse response =	client.execute(post);
		
	
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			content = EntityUtils.toString(response.getEntity());

			System.out.println(content);
			try {
				JSONObject temp = new JSONObject(content);
				keepCinvStd=temp.getString("cinvStd");

				Log.e("keepCinvStd", keepCinvStd+"aaa");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

	private String dohttpClientPost_p(){
    	
		HttpClient client = new DefaultHttpClient();

		HttpPost post = new HttpPost(utils.url+"inWarahouse");

		ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();

		list.add(new BasicNameValuePair("porm","1"));
		list.add(new BasicNameValuePair("aa", bb));
		list.add(new BasicNameValuePair("username", username));
		if("普通".equals(zdybz)){

			list.add(new BasicNameValuePair("zdybz", ""));
		}
		else{
			list.add(new BasicNameValuePair("zdybz", zdybz));
		}

		list.add(new BasicNameValuePair("positionCode", positionCode));

		try {
			post.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
		HttpResponse response =	client.execute(post);
	
		if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
			content_p = EntityUtils.toString(response.getEntity());
			
			try {
				JSONObject temp = new JSONObject(content_p);
			String	content_p=	temp.getString("isIn");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content_p;
	}



	
	public void back(){
		img_fanhui.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	
	public void clear(){
		
				// TODO Auto-generated method stub
				et_commodity_code.setText("");
				et_location_code.setText("");

				bb = "";
				tv_qrcode_show.setText("");
				tv_qtysumin.setText("");
				qrcodeShow="";
            	keepcoding="";
            	keepname="";
				positionCode = "";
				cinvStd= "-1";

				serialNumber= "";
		
	}
	
	public void kuwei_clear(){
		
		// TODO Auto-generated method stub
		et_commodity_code.setText("");


		positionCode = "";
    	keepcoding="";
    	keepname="";

		cinvStd= "-1";

		serialNumber= "";

}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.in_yuan_liao, menu);
		return true;
	}

}
