package com.example.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager viewPager;
    private ArrayList<VideoFragment> mFragmentList = new ArrayList<>();
    private Button start;
    private ImageView dot_1;
    private ImageView dot_2;
    private ImageView dot_3;
    private ArrayList<ImageView> mDotsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int lastIndex;

            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mDotsList.get(position).setSelected(true);
                mDotsList.get(lastIndex).setSelected(false);
                lastIndex = position;
                if (position == 2) {
                    start.setVisibility(View.VISIBLE);
                } else {
                    start.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), 3));

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        start = (Button) findViewById(R.id.start_button);
        start.setOnClickListener(this);

        dot_1 = (ImageView) findViewById(R.id.dot_1);
        dot_2 = (ImageView) findViewById(R.id.dot_2);
        dot_3 = (ImageView) findViewById(R.id.dot_3);

        dot_1.setSelected(true);
        mDotsList.add(dot_1);
        mDotsList.add(dot_2);
        mDotsList.add(dot_3);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:
                //TODO
                break;
        }

    }

    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm, int FragmentCount) {
            super(fm);
            for (int i = 0; i < FragmentCount; i++) {
                VideoFragment videoFragment = new VideoFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("index", i);
                videoFragment.setArguments(bundle);
                mFragmentList.add(videoFragment);
            }

        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
