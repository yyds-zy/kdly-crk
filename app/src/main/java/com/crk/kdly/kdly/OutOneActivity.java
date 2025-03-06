package com.crk.kdly.kdly;

//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONException;
//import org.json.JSONObject;

import com.crk.kdly.R;
//import com.example.kdly.http.utils;

import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Color;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.KeyEvent;
import android.view.Menu;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;

public class OutOneActivity extends Activity {
//	EditText et_chuku;
//	EditText et_frist;
//	//����
//	TextView tv_spec_show;
//	//����
//	TextView tv_number_show;
//	//����
//	TextView tv_unit_show;
//	//����
//	TextView tv_batch_show;
//	//��������
//	TextView tv_produceDate_show;
//	//����������
//	TextView tv_vendorCinvCode_show;
//	//������������
//	TextView tv_instruct_record_show;
//	//����
//	TextView tv_coding_show;
//	//����
//	TextView tv_name_show;
//	ImageView img_fanhui;
//	Button btn_in;
//	Button btn_clear;
//
//	String content_p;
//	String content_l;
//	String aa;
//
//	//������
//		String positionCode = "";
//		//����
//		String cinvStd;
//		//����
//		String baseQtyn;
//		//����
//		String ccomUnitName;
//		//����
//		String batch;
//		//��������
//		String produceDate;
//		//����������
//		String vendorCinvCode;
//		//����������
//		String instructMainId;
//		//������
//		String checker;
//		//������
//		String serialNumber;
//
//		//��������������������������
//		String spec_number;
//		//��������
//		String keepCinvStd;
//		//��������
//		String cinvCode;
//		//��������
//		String keepname;
		

		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_out_one);
		
//		Init();
//		back();
		
	}
	
//	public void Init(){
//		et_frist = (EditText) findViewById(R.id.et_frist1);
//		et_chuku = (EditText) findViewById(R.id.et_chuku1);
//		et_chuku.setEnabled(false);
//
//		img_fanhui = (ImageView) findViewById(R.id.fanhui);
//		tv_spec_show = (TextView) findViewById(R.id.tv_spec5_show);
//		tv_number_show = (TextView) findViewById(R.id.tv_number5_show);
//		tv_unit_show = (TextView) findViewById(R.id.tv_unit5_show);
//		tv_batch_show = (TextView) findViewById(R.id.tv_batch5_show);
//		tv_produceDate_show = (TextView) findViewById(R.id.tv_produceDate5_show);
//		tv_vendorCinvCode_show = (TextView) findViewById(R.id.tv_vendorCinvCode5_show);
//		tv_instruct_record_show = (TextView) findViewById(R.id.tv_instruct_record5_show);
//		tv_coding_show = (TextView) findViewById(R.id.tv_coding5_show);
//		tv_name_show = (TextView) findViewById(R.id.tv_name5_show);
//
//		btn_in = (Button) findViewById(R.id.btn_ok5);
//		btn_clear = (Button) findViewById(R.id.btn_clear5);
//
//		btn_clear.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				clear();
//			}
//		});
//
//		et_frist.addTextChangedListener(new TextWatcher() {
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
//
//			}
//
//			@Override
//			public void afterTextChanged(Editable arg0) {
//				// TODO Auto-generated method stub
//				//����������������
//				aa = et_frist.getText().toString();
//				//������������������  7/21 ����
//			//	et_frist.setText("");
//
//			//	clear();
//				try {
//					aa = new String(aa.getBytes("GBK"), "utf-8");
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				String[] str = aa.split("~");
//				if(aa.length()>15){
//				if(str.length==10){
//
//			//		clear();
//					et_chuku.setText(aa);
//					//����
//					cinvCode = str[0];
//					//����
//					cinvStd = str[1];
//					//��������
//					vendorCinvCode = str[2];
//					//����
//					baseQtyn = str[3];
//					//����
//					ccomUnitName = str[4];
//					//����
//					batch = str[5];
//					//����������
//					instructMainId = str[6];
//					//��������
//					produceDate = str[7];
//					//������
//					checker = str[8];
//					//������
//					serialNumber = str[9];
//
//					new Thread(new Runnable() {
//
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//						 dohttpClientPost_l();
//
//						}
//					}).start();
//
//					try {
//						Thread.sleep(500);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//					tv_coding_show.setText(cinvCode);
//                	tv_name_show.setText(keepname);
//					tv_spec_show.setText(cinvStd);
//					 tv_number_show.setText(baseQtyn);
//					 tv_unit_show.setText(ccomUnitName);
//					 tv_batch_show.setText(batch);
//					 tv_produceDate_show.setText(produceDate);
//					 tv_vendorCinvCode_show.setText(vendorCinvCode);
//					 tv_instruct_record_show.setText(instructMainId);
//
//					 btn_in.setBackgroundColor(Color.rgb(132, 193, 255));
//					 btn_in.setEnabled(true);
//					//��������
//					 btn_in.setOnClickListener(new View.OnClickListener() {
//
//							@Override
//							public void onClick(View arg0) {
//								// TODO Auto-generated method stub
//								//��������
//								 new Thread(new Runnable() {
//
//									@Override
//									public void run() {
//										// TODO Auto-generated method stub
//										dohttpClientPost_p();
//									}
//								}).start();
//							     clear();
//							    btn_in.setBackgroundColor(Color.GRAY);
//							    btn_in.setEnabled(false);
//							}
//						});
//					//������������������
//				//	et_commodity_code.setText("");
//				}else if(str.length>1&&str.length<10){
//	 				Toast.makeText(OutOneActivity.this,"嗯嗯", Toast.LENGTH_SHORT).show();
//					clear();
//				}
//				else if(str.length>10){
//			//		Toast.makeText(OutOneActivity.this,"����������������������������", 500).show();
//					clear();
//				}
//			}else{
//				Toast.makeText(OutOneActivity.this,"你好", Toast.LENGTH_SHORT).show();
//			}
//			}
//		});
//	}
	
	
	
//	public void clear(){
//
//		// TODO Auto-generated method stub
//		et_frist.setText("");
//		et_chuku.setText("");
//		tv_batch_show.setText("");
//		tv_coding_show.setText("");
//		tv_instruct_record_show.setText("");
//		tv_name_show.setText("");
//		tv_number_show.setText("");
//		tv_produceDate_show.setText("");
//		tv_spec_show.setText("");
//		tv_unit_show.setText("");
//		tv_vendorCinvCode_show.setText("");
//		tv_coding_show.setText("");
//    	tv_name_show.setText("");
//
//
//    //	keepcoding="";
//    	keepname="";
//		//������
//		positionCode = "";
//		//����
//		cinvStd= "-1";
//		//����
//		baseQtyn= "";
//		//����
//		ccomUnitName= "";
//		//����
//		batch= "";
//		//��������
//		produceDate= "";
//		//����������
//		vendorCinvCode= "";
//		//����������
//		instructMainId= "";
//		//������
//		checker= "";
//		//������
//		serialNumber= "";
//
//}
	
//	public void back(){
//		img_fanhui.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
//	}
	
	
	//������������
//		private String dohttpClientPost_p(){
//
//			HttpClient client = new DefaultHttpClient();
//			//
//			HttpPost post = new HttpPost(utils.url_out);
//
//		//	System.out.println("��������"+"04A12_06"+"\n"+spec);
//			ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
//			//post����������������+������
//			list.add(new BasicNameValuePair("porm","4"));
//			list.add(new BasicNameValuePair("aa", aa));
//			list.add(new BasicNameValuePair("cinvStd", cinvStd));
//			list.add(new BasicNameValuePair("baseQtyn",baseQtyn));
//			list.add(new BasicNameValuePair("ccomUnitName", ccomUnitName));
//			list.add(new BasicNameValuePair("batch", batch));
//			list.add(new BasicNameValuePair("produceDate", produceDate));
//			list.add(new BasicNameValuePair("vendorCinvCode",vendorCinvCode));
//			list.add(new BasicNameValuePair("instructMainId", instructMainId));
//			list.add(new BasicNameValuePair("checker", checker));
//			list.add(new BasicNameValuePair("serialNumber", serialNumber));
//
//
//			try {
//				post.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
//			HttpResponse response =	client.execute(post);
//
//			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
//				content_p = EntityUtils.toString(response.getEntity());
//				// ����
//				//content_p =new String(content_p.getBytes("ISO-8859-1"), "utf-8");
//
//				try {
//					JSONObject temp = new JSONObject(content_p);
//					content_p=	temp.getString("isIn");
//				System.out.println("aaa----"+content_p);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ClientProtocolException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return content_p;
//		}

		//����������������������   ��������
//		private String dohttpClientPost_l(){
//
//			HttpClient client = new DefaultHttpClient();
//			//
//			HttpPost post = new HttpPost(utils.url+"getCinvCodeCinvName");
//
//		//	System.out.println("��������"+"04A12_06"+"\n"+spec);
//			ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
//			//post����
//			list.add(new BasicNameValuePair("cinvStd",cinvStd));
//
//			try {
//				post.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
//			HttpResponse response =	client.execute(post);
//
//			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
//				content_l = EntityUtils.toString(response.getEntity());
//				// ����
//				//content_l =new String(content_l.getBytes("ISO-8859-1"), "utf-8");
//				System.out.println(content_l);
//				try {
//					JSONObject temp = new JSONObject(content_l);
//			//		keepcoding=temp.getString("cinvCode");
//					keepname=temp.getString("cinvName");
//			//		System.out.println("keepcoding---"+keepcoding+"keepname---"+keepname);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ClientProtocolException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return content_l;
//		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.out_one, menu);
		return true;
	}

}
