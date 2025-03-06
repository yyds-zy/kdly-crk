package com.crk.kdly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crk.kdly.R;
import com.crk.kdly.http.Ck_list_Bean;

import java.util.List;

/**
 * @author 孙贝贝
 * @packagename com.example.kdly.adapter
 * @date on 2020/8/27 18:28
 * @wechat 18813158027
 */
public class Second_Adapter extends RecyclerView.Adapter<Second_Adapter.Second_ViewHolder> {
    private Context context;
    private  List<Ck_list_Bean.OutlistBean.List2Bean> list;

    public Second_Adapter(Context context, List<Ck_list_Bean.OutlistBean.List2Bean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Second_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.second_item,viewGroup,false);
        return new Second_ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull Second_ViewHolder holder, int i) {
        Ck_list_Bean.OutlistBean.List2Bean list2Bean = list.get(i);

        holder.hu_num.setText("货位号："+list2Bean.getPosition_code());
        holder.num.setText("数量："+list2Bean.getBase_qtyn());
    }

    class  Second_ViewHolder extends RecyclerView.ViewHolder{
        public TextView hu_num,num;


        public Second_ViewHolder(@NonNull View itemView) {
            super(itemView);

            hu_num=itemView.findViewById(R.id.hw_num);
            num=itemView.findViewById(R.id.num);

        }
    }
}
