package com.example.recyclerview;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        initData();
    }

    private void initData() {
        Resources resources = getResources();
        List<Integer> mData = new ArrayList<>();
        int identifier;
        for (int i = 0; i < 39; i++) {
            identifier = resources.getIdentifier("ic_category_" + i, "mipmap", getPackageName());
            mData.add(identifier);
        }

        MyAdapter myAdapter = new MyAdapter(this, mData);
        myAdapter.setMyItemClickListener(new MyAdapter.onMyItemClickListener() {
            @Override
            public void onMyItemClick(View view, int position) {
                show("你点击了Item是第" + position + "个!");
            }

        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView
                    .State state) {
                //nothing
            }
        });
        recyclerView.setAdapter(myAdapter);
    }

    private void show(String s) {
        if (toast == null) {
            toast = makeText(this, s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.cancel();
            toast = null;
            toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
