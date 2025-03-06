package com.crk.kdly.kdly;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.crk.kdly.R;
import com.crk.kdly.base.BaseActivity;
import com.crk.kdly.fragment.Scan_Out_Ck_Fragment;
import com.crk.kdly.fragment.Scan_Out_list_Fragment;
import com.google.android.material.tabs.TabLayout;


public class OutYuanLiaoActivity extends BaseActivity implements View.OnClickListener {



    private ViewPager viewPager;
    private TextView title;
    private ImageView back;
    private TabLayout tabLayout = null;
    private Fragment[] mFragmentArrays = new Fragment[2];

    private String[] mTabTitles = new String[2];
    private String spec_number;
    private String chorc;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_out_yuan_liao;
    }

    @Override
    public void initViews() {
        spec_number = getIntent().getStringExtra("spec_number");
        chorc = getIntent().getStringExtra("chorc"); // c材料出库  ch成品出库
        initFind();
        initView();


    }
    private void initView() {
        mTabTitles[0] = "扫码出库列表";
        mTabTitles[1] = "扫码出库";
        mFragmentArrays[0] =Scan_Out_list_Fragment.newInstance(spec_number,chorc);
        mFragmentArrays[1] =  Scan_Out_Ck_Fragment.newInstance(spec_number,chorc);

        PagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        //将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);
    }
    private void initFind() {
        title = findViewById(R.id.title);
        back =findViewById(R.id.back);
        tabLayout =findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.tab_viewpager);
        back.setOnClickListener(this);
        if (chorc.equals("c")){
            title.setText("原材料出库");
        }else{
            title.setText("成品出库");
        }
    }


    @Override
    public void onClick(View v) {
        if (v==back){
            finish();
        }

    }
    final class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentArrays[position];
        }


        @Override
        public int getCount() {
            return mFragmentArrays.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];

        }
    }
}
