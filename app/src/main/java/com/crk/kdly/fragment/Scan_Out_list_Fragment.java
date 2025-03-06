package com.crk.kdly.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crk.kdly.R;
import com.crk.kdly.adapter.Ck_ListAdapter;
import com.crk.kdly.base.BaseFragment;
import com.crk.kdly.http.Ck_list_Bean;
import com.crk.kdly.net_connection.NetUtils;
import com.crk.kdly.net_connection.Server_Response;
import com.crk.kdly.tool.TLog;
import com.crk.kdly.tool.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 孙贝贝
 * @packagename com.example.kdly.fragment
 * @date on 2020/8/27 11:24
 * @wechat 18813158027
 */
public class Scan_Out_list_Fragment extends BaseFragment implements Server_Response.OnGetSeverLister, View.OnClickListener, OnLoadMoreListener, OnRefreshListener {

    private RecyclerView recyclerView;
    private static Scan_Out_list_Fragment fragment;
    private String type;
    private TextView time;
    private Button query;

    private Ck_ListAdapter adapter;
    private int pn=1;
    private int ps=10;
    private static int status = 0;
    private static int REFRESH = 2;//刷新
    private static int LOADMORE = 3;//加载
    private SmartRefreshLayout swrefesh;
    private List<Ck_list_Bean.OutlistBean> dataLists=new ArrayList<>();
    @Override
    public int loadWindowLayout() {
        return R.layout.out_list_fragment;
    }
    public static Fragment newInstance(String  type, String chorc) {
        fragment = new Scan_Out_list_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("spec_number", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initViews(View rootView) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("spec_number");
        }
        swrefesh = rootView.findViewById(R.id.swrefesh);
        swrefesh.autoRefresh();
        swrefesh.setOnRefreshListener(this);
        swrefesh.setOnLoadMoreListener(this);
        recyclerView = rootView.findViewById(R.id.recyclerivew);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Ck_ListAdapter(getContext());

        time = rootView.findViewById(R.id.time);
        time.setText(Utils.getSimpDate());
        query = rootView.findViewById(R.id.query);
        time.setOnClickListener(this);
        query.setOnClickListener(this);



    }
    @Override
    public void onResume() {
        super.onResume();

        swrefesh.autoRefresh();
        TLog.e("走的是","onResume");
    }
    private void request(){//请求数据
        NetUtils.getOrderList(this,time.getText().toString(),type,pn+"",ps+"");
    }

    @Override
    public void onSuccess(Object response) {
        swrefesh.finishRefresh();
        swrefesh.finishLoadMore();
        if (response instanceof Ck_list_Bean){
            Ck_list_Bean ck_list_bean = (Ck_list_Bean) response;
            displayData(ck_list_bean);
        }

    }

    @Override
    public void onFaire(String error) {
        swrefesh.finishRefresh();
        swrefesh.finishLoadMore();
        tostShow(error);

    }
    private void displayData( Ck_list_Bean data) {

        if (status == REFRESH) {
            swrefesh.setNoMoreData(false);

            if (dataLists.size()!=0) {
                dataLists.clear();
            }
            dataLists.addAll(data.getOutlist());
            adapter.setList(dataLists);
            recyclerView.setAdapter(adapter);
        }else if (status == LOADMORE) {
            List<Ck_list_Bean.OutlistBean> dataList2 = data.getOutlist();
            if (null != dataList2) {
                if (dataList2.size() == 0) {
                    swrefesh.setNoMoreData(true);
                }
                dataLists.addAll(dataList2);
                TLog.e("返回数据的多少"+dataLists.size());
                adapter.notifyDataSetChanged();
            }
        }




    }

    @Override
    public void onClick(View v) {
        if (v==time){
            Utils.initTimePickerYear(getContext(),time);
        }else if(v==query){//查询
            swrefesh.autoRefresh();


        }

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        status = LOADMORE;
        pn = pn + 1;
        request();

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        status = REFRESH;
        pn = 1;
        request();
    }
}
