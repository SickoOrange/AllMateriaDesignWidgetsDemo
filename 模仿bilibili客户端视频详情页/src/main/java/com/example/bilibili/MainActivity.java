package com.example.bilibili;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView imageView;
    private FrameLayout video_danmu;
    private FloatingActionButton floatingActionButton;
    private boolean Danmu_Titel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置activity状态栏为沉浸式状态栏  结合android:fitsSystemWindows="true" 完美实现沉浸式状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        final ButtonBarLayout playButton = (ButtonBarLayout) findViewById(R.id.playButton);
        imageView = (ImageView) findViewById(R.id.image_view);
        video_danmu = (FrameLayout) findViewById(R.id.video_danmu);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    //完全伸展开
                    Log.e("main", "完全伸展开");
                    if (!Danmu_Titel) {
                        collapsingToolbarLayout.setTitle("完全伸展开");
                    } else {
                        collapsingToolbarLayout.setTitle("这是弹幕播放器");
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    //完全折叠
                    Log.e("main", "完全折叠");
                    //collapsingToolbarLayout.setTitle("");
                    playButton.setVisibility(View.VISIBLE);
                } else {
                    //正在折叠或者伸展
                    Log.e("main", "正在折叠或者伸展");
                    collapsingToolbarLayout.setTitle("正在折叠或者伸展");
                    playButton.setVisibility(View.GONE);

                }
            }
        });


        //点击Toolbar按钮 重新展开collapsingtoolbar 同时替换imageview为bilibili弹幕播放器
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appBarLayout.setExpanded(true);
                imageView.setVisibility(View.GONE);
                video_danmu.setVisibility(View.VISIBLE);
                Danmu_Titel = true;

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "退出弹幕播放器，返回视频封面", Toast
                        .LENGTH_SHORT).show();
                appBarLayout.setExpanded(false);
                imageView.setVisibility(View.VISIBLE);
                video_danmu.setVisibility(View.GONE);
                Danmu_Titel = false;
            }
        });


    }
}
