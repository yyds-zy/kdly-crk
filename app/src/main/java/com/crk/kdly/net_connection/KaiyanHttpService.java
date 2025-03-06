package com.crk.kdly.net_connection;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author 孙贝贝
 * @packagename com.example.a93018.videodemo
 * @date on 2019/1/28 10:26
 * @wechat 18813158027
 */
public interface KaiyanHttpService {
    /** 获取库房 */
    @POST("cdlyWmsRdRecord/selkfinfo")
    Call<ResponseBody> inKu(@Query("userName")String userName);
    /** 获取库房 */
    @POST("cdlyWmsRdRecord/zidingyibzh")
    Call<ResponseBody> zidingyibzh(@Query("userName")String userName);
    /** 登录 */
    @POST("cdlyWmsRdRecord/login")
    Call<ResponseBody> login(@Query("userName")String userName,@Query("password")String passorwd);
    /** 入库 */
    @POST("cdlyWmsRdRecord/inWarahouse")
    Call<ResponseBody> rk(@Query("porm")String porm,@Query("aa")String aa,@Query("shl")String num,@Query("positionCode")String positionCode , @Query("username")String username );
    /** 验证库位是否可用 */
    @POST("cdlyWmsRdRecord/checkPositionCode")
    Call<ResponseBody> pipei(@Query("positionCode")String positionCode);
    /** 出库列表 */
    @POST("cdlywmsOut/wmsoutList")
    Call<ResponseBody> ck_list(@Query("cwhcode")String cwhcode,@Query("date")String date,@Query("pn")String pn,@Query("ps")String ps);
    /** 扫描获取信息 */
    @GET("cdlywmsOut/wmsoutqrcode")
    Call<ResponseBody> scan_details(@Query("qrcodestr")String qrcodestr,@Query("chorc") String chorc);
    /** 出库提交 */
    @POST("cdlywmsOut/wmsoutenter")
    Call<ResponseBody> comfim_ck(@Query("cwhcode")String cwhcode,@Query("cinvCode") String cinvCode,@Query("qty") String qty,@Query("login") String login);







}
