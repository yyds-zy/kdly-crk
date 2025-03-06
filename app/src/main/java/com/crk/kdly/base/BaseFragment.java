package com.crk.kdly.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.crk.kdly.tool.SPUtils;


public abstract class BaseFragment extends Fragment {

    private ProgressBar progressBar;
    private Context context;
    private String username;



    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //加载一个布局
        View rootView = inflater.inflate(this.loadWindowLayout(), container, false);
        context=getContext();
        username = (String) SPUtils.get(getContext(), "username", "");
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);
        progressBar = new ProgressBar(getContext());
        progressBar.setVisibility(View.GONE);
        progressBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(progressBar);
        frameLayout.addView(rootView);
        frameLayout.addView(linearLayout);
        initViews(frameLayout);

        return frameLayout;
    }
    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgress(){
        progressBar.setVisibility(View.GONE);
    }


    //该抽象方法用于为Activit设置xml
    public abstract int loadWindowLayout();

    public abstract void initViews(View rootView);
    public String getUsername(){
        return username;
    }




    public void tostShow(String content) {//显示提示
        Toast.makeText(context,content, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NewApi")
    public void startActivity(Class clazz) {//跳转
        startActivity(new Intent(getContext(), clazz));

    }


}
