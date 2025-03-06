package com.crk.kdly.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	String content;
	MyHandler handler ;
	public String dohttpClientPost(){
    	
		HttpClient client = new DefaultHttpClient();
//		HttpPost post = new HttpPost("http://172.16.133.14:8080/cdly/a/basedata/cdlyDynamicCode/getCodeByType");
//		ArrayList<NameValuePair> list = new ArrayList<NameValuePair>();
//		list.add(new BasicNameValuePair("cinvcCode", "12"));
//		try {
//			post.setEntity(new UrlEncodedFormEntity(list));
//		HttpResponse response =	client.execute(post);
//
//		if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
//			content = EntityUtils.toString(response.getEntity());
//			// ����
//			content =new String(content.getBytes("ISO-8859-1"), "utf-8");
//		}
//
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return content;
	}
	
	class MyThread implements Runnable  
	{

		@Override
		public void run() {

//			String str = dohttpClientPost();
//			System.out.println("=====str==="+str);
//			Message message=new Message();
//	        Bundle bundle=new Bundle();
//	        bundle.putString("str", str);
//	        message.setData(bundle);//bundle��ֵ����ʱ��Ч�ʵ�
//	        handler.sendMessage(message);//����message��Ϣ
//	        message.what=1;//��־���ĸ��̴߳�����
//
		}
		
	}
	
	class MyHandler extends Handler  
	{  
		
	    @Override
	    public void handleMessage(Message msg) {  

//	        super.handleMessage(msg);
//	        String result = msg.getData().getString("str");
//	        System.out.println("---b---"+result);
//	        if(msg.what==1)
//	        {
//	       // 	tv_number_show.setText(result);
//	        }
	    }
	}
	
}




