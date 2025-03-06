package com.crk.kdly.net_connection;


import com.crk.kdly.http.Ck_Scan_Details;
import com.crk.kdly.http.Ck_list_Bean;
import com.crk.kdly.http.Comfim_Result;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * @author 孙贝贝
 * @packagename com.example.kuodan
 * @date on 2020/4/14 10:33
 * @wechat 18813158027
 */
public class NetUtils {
    public static void getOrderList(com.crk.kdly.net_connection.Server_Response.OnGetSeverLister lister, String time, String cwhcode, String pn, String ps){//获取订单列表
        Call<ResponseBody> responseBodyCall = com.crk.kdly.net_connection.ServerUtil.getInstance().getServer().ck_list(cwhcode,time,pn,ps);
        com.crk.kdly.net_connection.Server_Response.getResponse(responseBodyCall,new Ck_list_Bean(),lister);
    }
    public static  void  scanGetDetails(com.crk.kdly.net_connection.Server_Response.OnGetSeverLister lister, String message, String chorc){
        Call<ResponseBody> responseBodyCall = com.crk.kdly.net_connection.ServerUtil.getInstance().getServer().scan_details(message,chorc);
        com.crk.kdly.net_connection.Server_Response.getResponse(responseBodyCall,new Ck_Scan_Details(),lister);
    }
    public static  void  comfiom_ck(com.crk.kdly.net_connection.Server_Response.OnGetSeverLister lister, String cwhcode, String cinvCode, String num, String userName){
        Call<ResponseBody> responseBodyCall = com.crk.kdly.net_connection.ServerUtil.getInstance().getServer().comfim_ck(cwhcode,cinvCode,num,userName);
        com.crk.kdly.net_connection.Server_Response.getResponse(responseBodyCall,new Comfim_Result(),lister);

    }

}
