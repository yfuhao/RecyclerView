package com.bawei.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.recyclerview.Bean.NewBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/*
  Created by yufuhao on 2017/5/23.
*/

public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<NewBean.ListBean> list;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context context, List<NewBean.ListBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view1 = inflater.inflate(R.layout.item, parent, false);
            MyViewHolder holder1 = new MyViewHolder(view1);
            return holder1;
        } else {
            View view2 = inflater.inflate(R.layout.itme_two, parent, false);
            TwoViewHolder holder2 = new TwoViewHolder(view2);
            return holder2;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.one_tv.setText(list.get(position).getTitle());
            ImageLoader.getInstance().displayImage(list.get(position).getPic(), myViewHolder.one_img);
        }
        if (holder instanceof TwoViewHolder) {
            TwoViewHolder twoViewHolder = (TwoViewHolder) holder;
            twoViewHolder.two_tv.setText(list.get(position).getTitle());

            String pic = list.get(position).getPic();
            String[] split = pic.split("\\|");
            System.out.println("list_new" + split[1].toString());
            ImageLoader.getInstance().displayImage(split[0].toString(), twoViewHolder.two_img01);
            ImageLoader.getInstance().displayImage(split[1].toString(), twoViewHolder.two_img02);

        }
    }

    @Override
    public int getItemViewType(int position) {
        System.out.println("position = " + position);
        if (list.get(position).getType() == 1) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //这是一个viewholder
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView one_tv;
        ImageView one_img;

        public MyViewHolder(View view) {
            super(view);
            one_tv = (TextView) view.findViewById(R.id.one_title);
            one_img = (ImageView) view.findViewById(R.id.one_image);
        }
    }

    class TwoViewHolder extends RecyclerView.ViewHolder {
        TextView two_tv;
        ImageView two_img01;
        ImageView two_img02;

        public TwoViewHolder(View view) {
            super(view);
            two_tv = (TextView) view.findViewById(R.id.two_title);
            two_img01 = (ImageView) view.findViewById(R.id.two_img01);
            two_img02 = (ImageView) view.findViewById(R.id.two_img02);
        }
    }
}
