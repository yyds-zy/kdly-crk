package com.crk.kdly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crk.kdly.R;
import com.crk.kdly.http.Comfim_Result;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 孙贝贝
 * @packagename com.example.kdly.adapter
 * @date on 2020/8/28 11:30
 * @wechat 18813158027
 */
public class Alier_Dilog_Adapter extends RecyclerView.Adapter<Alier_Dilog_Adapter.Alier_ViewHolder> {

    private Context context;
    private List<Comfim_Result.ListBean> list;

    public Alier_Dilog_Adapter(Context context, List<Comfim_Result.ListBean> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public Alier_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.alier_item, viewGroup, false);
        return new Alier_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Alier_ViewHolder holder, int i) {
        Comfim_Result.ListBean listBean = list.get(i);
        holder.ckNumTv.setText("出库数量："+listBean.getBase_qtyn());
        holder.hwTv.setText("货位："+listBean.getPosition_code());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Alier_ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hw_tv)
        TextView hwTv;
        @BindView(R.id.ck_num_tv)
        TextView ckNumTv;
        @BindView(R.id.unit_tv)
        TextView unitTv;

        public Alier_ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
