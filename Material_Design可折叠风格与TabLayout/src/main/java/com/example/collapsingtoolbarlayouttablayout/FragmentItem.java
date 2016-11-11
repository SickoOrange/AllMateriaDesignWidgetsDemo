package com.example.collapsingtoolbarlayouttablayout;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orange on 2016/11/11.
 */

public class FragmentItem extends BaseFragment {

    private View view;
    private List<String> mDatas;

    @Override
    public View InitView(LayoutInflater inflater, @Nullable ViewGroup container) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_item, container, false);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            initData();
            recyclerView.setAdapter(new MyAdapter());
            return view;
        }
        return view;
    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            mDatas.add("Recycler View Item"+i);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(getContext()).inflate
                    (R.layout.item, parent,
                            false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
        }


        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
