package com.crk.kdly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crk.kdly.R;
import com.crk.kdly.http.Ck_list_Bean;

import java.util.List;

/**
 * @author 孙贝贝
 * @packagename com.example.kdly.adapter
 * @date on 2020/8/27 14:05
 * @wechat 18813158027
 */
public class Ck_ListAdapter extends RecyclerView.Adapter<Ck_ListAdapter.Ck_ViewHolder> {
    private Context context;
    private List<Ck_list_Bean.OutlistBean> outlist;
    public Ck_ListAdapter(Context context) {
        this.context = context;
    }
    public  void setList(List<Ck_list_Bean.OutlistBean> outlist){
        this.outlist=outlist;
    }
    @Override
    public Ck_ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cklist_item,viewGroup,false);
        return new Ck_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( Ck_ViewHolder holder, int i) {
        Ck_list_Bean.OutlistBean outlistBean = outlist.get(i);
        holder.order_num.setText("单号:"+outlistBean.getMain_id());
        holder.order_guige.setText("规格："+outlistBean.getCinv_std());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        List<Ck_list_Bean.OutlistBean.List2Bean> list2 = outlistBean.getList2();
        Second_Adapter adapter =new Second_Adapter(context,list2);
        holder.recyclerView.setAdapter(adapter);



    }

    @Override
    public int getItemCount() {
        return outlist.size();
    }

    class  Ck_ViewHolder extends RecyclerView.ViewHolder{
        TextView order_num, order_guige;
        public RecyclerView recyclerView;
        public Ck_ViewHolder( View itemView) {
            super(itemView);
            order_num =itemView.findViewById(R.id.order_num);
            order_guige=itemView.findViewById(R.id.order_guige);
             recyclerView =itemView.findViewById(R.id.recycler_view);


        }
    }
}
