package com.example.collapsingtoolbarlayouttablayout;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private List<FragmentItem> mList = new ArrayList<FragmentItem>();
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Material Design");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpage);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));

        FragmentItem item1 = new FragmentItem();
        FragmentItem item2 = new FragmentItem();
        mList.add(item1);
        mList.add(item2);
        myAdapter adapter = new myAdapter(getSupportFragmentManager(), mList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }


    public class myAdapter extends FragmentPagerAdapter {
        private List<FragmentItem> mList;
        private String[] TitelList = new String[]{"Hello", "World"};

        public myAdapter(FragmentManager fm, List<FragmentItem> mList) {
            super(fm);
            this.mList = mList;
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TitelList[position];
        }
    }
}
