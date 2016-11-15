package com.example.coordinatorlayout;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private Toolbar toolBar;
    private TabLayout tabLayout;
    private Toast toast;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置statusbar为全透明 大于lolipop才有效果

       /* 第一种方法会让下面导航条同样变得透明
                第二种方法不会 */
       /* Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager
                .LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                .SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);*/


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }


        setContentView(R.layout.activity_main);
        initView();
        getSupportActionBar().setTitle("melodyorange");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //给viewpager设置数据集
        setData();
    }

    private void setData() {
        initFragmentDataList(4);
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        setTabIconAndTitle();
    }

    private void setTabIconAndTitle() {

        int tabCount = tabLayout.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            switch (i) {
                case 0:
                    tabLayout.getTabAt(0).setCustomView(R.layout.tab_first_icon);
                    break;
                case 1:
                    tabLayout.getTabAt(1).setText("jinx");
                    break;
                case 2:
                    tabLayout.getTabAt(2).setText("Sivir");
                    break;
                case 3:
                    tabLayout.getTabAt(3).setText("Kalista");
                    break;
            }
        }
    }

    private void initFragmentDataList(int tabCount) {
        for (int i = 0; i < tabCount; i++) {
            mFragmentList.add(new MyFragment());
        }
    }

    private void initView() {
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.icon_blog:
                show("this is blog");
                break;
            case R.id.icon_weibo:
                show("this is weibo");
                break;
            case R.id.toolbar_setting:
                show("this is setting");
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //优化Toast显示
    private void show(String s) {
        if (toast == null) {
            toast = Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.cancel();
            toast = null;
            show(s);
        }
    }


    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position
            );
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "jinx";
        }
    }

}
