package com.crk.kdly;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;

import com.crk.kdly.tool.AppUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;


/**
 * Created by SAMSUNG on 2018/6/14.
 */

public class CpApp extends Application {
    private static Context mContext;




    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initBugly(mContext);
    }

    private void initBugly(Context context) {
        String packageName = context.getPackageName();
        String processName = AppUtils.getProcessName(android.os.Process.myPid());
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        strategy.setDeviceID(Build.SERIAL);
        strategy.setAppVersion(AppUtils.getAppVersion(context));
        strategy.setDeviceModel(Build.BRAND + " " + AppUtils.isTablet(context) + " " + Build.MODEL);
        // CrashReport.setUserSceneTag(context, 9527); // 上报后的Crash会显示该标签
        CrashReport.setIsDevelopmentDevice(context, BuildConfig.DEBUG);
        CrashReport.initCrashReport(context, "f525075111", true);
    }

    static { //设置全局构建器
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);//启用矢量图兼容
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            ClassicsHeader header = new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.FixedBehind);
            header.setPrimaryColorId(R.color.common_gray_bg);//设置全局主题颜色
            header.setAccentColorId(R.color.gray_text);
            header.setTextSizeTitle(13f);
            return header;//指定为经典Header，默认是 贝塞尔雷达Header
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            layout.setEnableLoadMoreWhenContentNotFull(true);//内容不满一页时候启用加载更多
            ClassicsFooter footer = new ClassicsFooter(context);
            footer.setBackgroundResource(R.color.common_gray_bg);
            footer.setSpinnerStyle(SpinnerStyle.Scale);//设置为拉伸模式
            footer.setTextSizeTitle(13f);

            footer.setAccentColorId(R.color.gray_text);
            return footer;//指定为经典Footer，默认是 BallPulseFooter
        });
    }


    public static Context getContext() {
        return mContext;
    }


}