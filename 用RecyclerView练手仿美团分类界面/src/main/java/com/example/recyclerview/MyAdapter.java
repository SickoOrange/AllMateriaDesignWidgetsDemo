package com.example.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by Orange on 2016/11/18.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private List<Integer> mData;
    private onMyItemClickListener myItemClickListener;


    public MyAdapter(Context mContext, List<Integer> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }


    /**
     * 为Adapter的每一个Item设置点击监听，
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final MyAdapter.MyViewHolder holder, int position) {
        holder.iv.setImageResource(mData.get(position));
        holder.tv.setText("分类" + position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myItemClickListener.onMyItemClick(holder.itemView, holder.getLayoutPosition());
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

    public void setMyItemClickListener(onMyItemClickListener listener) {
        myItemClickListener = listener;
    }


}

