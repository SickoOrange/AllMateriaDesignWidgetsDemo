package com.example.collapsingtoolbarlayouttablayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private List<FragmentItem> mList = new ArrayList<FragmentItem>();
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int[] mImages = new int[]{R.drawable.ic_favorite_border_white_24dp, R.drawable
            .ic_change_history_white_24dp};
    private String[] TitelList = new String[]{"Hello", "World"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Material Design");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
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

        SetUpTabIcon();
        viewPager.setCurrentItem(1);
        viewPager.setCurrentItem(0);


    }

    private void SetUpTabIcon() {

            tabLayout.getTabAt(0).setCustomView(getIconView(0));
        tabLayout.getTabAt(1).setCustomView(getIconView(1));

    }

    private View getIconView(int i) {
        View iconRootView = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_icon, null);
        ImageView icon_Image = (ImageView) iconRootView.findViewById(R.id.icon_image);
        TextView icon_Name = (TextView) iconRootView.findViewById(R.id.icon_name);
        icon_Image.setImageResource(mImages[i]);
        icon_Name.setText(TitelList[i]);

        return iconRootView;
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
