package com.example.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by Orange on 2016/11/18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private List<Integer> mData;

    /**
     * item的点击事件接口
     */
    private onMyItemClickListener myItemClickListener;


    /**
     * 瀑布流时的item随机高度
     */
    private List<Integer> heights = new ArrayList<>();
    /**
     * 不同的类型设置item不同的高度
     *
     * @param type
     */

    private int type = 0;


    public MyAdapter(Context mContext, List<Integer> mData) {
        this.mContext = mContext;
        this.mData = mData;
        for (Integer integer : mData) {
            int height = (int) (Math.random() * 100 + 300);
            //为每一个item设置一个随机的高度
            System.out.println("item height=" + height);
            heights.add(height);
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 设置点击事件
     *
     * @param listener
     */

    public void setMyItemClickListener(onMyItemClickListener listener) {
        myItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }


    /**
     * 为Adapter的每一个Item设置点击监听，
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        RecyclerView.LayoutParams layoutParams;
        if (type == 0) {
            layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        } else if (type == 1) {
            layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    heights.get(position));
            layoutParams.setMargins(2, 2, 2, 2);
        }

        holder.itemView.setLayoutParams(layoutParams);
        holder.iv.setImageResource(mData.get(position));
        holder.tv.setText("分类" + position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myItemClickListener != null) {
                    myItemClickListener.onMyItemClick(holder.itemView, holder.getLayoutPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class MyViewHolder extends ViewHolder {
        ImageView iv;
        TextView tv;
        View itemView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    public interface onMyItemClickListener {

        void onMyItemClick(View view, int position);
    }


}

