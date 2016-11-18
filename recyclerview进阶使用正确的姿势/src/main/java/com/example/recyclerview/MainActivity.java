package com.example.recyclerview;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rv;
    private List<Integer> datas;
    private MyAdapter rvAdapter;
    private Toast toast;
    private RecyclerView.ItemDecoration dividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = (RecyclerView) findViewById(R.id.rv);
        initData();
    }

    private void initData() {
        datas = new ArrayList<>();
        Resources res = getResources();
        for (int i = 0; i < 38; i++) {
            datas.add(res.getIdentifier("ic_category_" + i, "mipmap", getPackageName()));
        }
        /**
         *用来确定每一个item如何进行排列摆放
         * LinearLayoutManager 相当于ListView的效果
         GridLayoutManager相当于GridView的效果
         StaggeredGridLayoutManager 瀑布流
         */
        /**第一步：设置布局管理器**/
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        /**第二步：添加分割线**/
        dividerItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST);
        rv.addItemDecoration(dividerItemDecoration);
        rvAdapter = new MyAdapter(this, datas);
        rv.setAdapter(rvAdapter);
        rvAdapter.setMyItemClickListener(new MyAdapter.onMyItemClickListener() {
            @Override
            public void onMyItemClick(View view, int position) {
                show("你点击了Item是第" + position + "个!");
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_listview:
               show("id_action_listview");
                rv.setBackgroundColor(Color.TRANSPARENT);
                rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                        false));
                rvAdapter.setType(0);
                rv.removeItemDecoration(dividerItemDecoration);
                rv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams
                        .MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration
                        .VERTICAL_LIST);
                rv.addItemDecoration(dividerItemDecoration);
                break;
            /**横向的ListView**/
            case R.id.id_action_horizontalListView:
                rvAdapter.setType(1);
                rv.removeItemDecoration(dividerItemDecoration);
                rv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST);
                rv.addItemDecoration(dividerItemDecoration);
                break;
            /**竖向的GridView**/
            case R.id.id_action_gridview:
                rvAdapter.setType(1);
                rv.setBackgroundColor(Color.TRANSPARENT);
                rv.removeItemDecoration(dividerItemDecoration);
                rv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                rv.setLayoutManager(new GridLayoutManager(this, 5));
                dividerItemDecoration = new DividerGridItemDecoration(this);
                rv.addItemDecoration(dividerItemDecoration);
                break;
            /**横向的GridView**/
            case R.id.id_action_horizontalGridView:
                rvAdapter.setType(1);
                rv.setBackgroundColor(Color.TRANSPARENT);
                rv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                rv.removeItemDecoration(dividerItemDecoration);
                rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
                dividerItemDecoration = new DividerGridItemDecoration(this);
                rv.addItemDecoration(dividerItemDecoration);
                break;
            /**竖向的瀑布流**/
            case R.id.id_action_staggeredgridview:
                rvAdapter.setType(3);
                rv.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                rv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                rv.removeItemDecoration(dividerItemDecoration);
                rv.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
                break;

            /**添加一个数据**/
            case R.id.id_action_add:
                rvAdapter.notifyItemInserted(1);
                break;
            /**删除一个数据**/
            case R.id.id_action_delete:
                rvAdapter.notifyItemRemoved(1);
                break;
        }
        return true;
    }
}