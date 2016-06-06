package com.example.frank.myshoppingmall;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.frank.myshoppingmall.fragment.CartFragment;
import com.example.frank.myshoppingmall.fragment.CategoryFragment;
import com.example.frank.myshoppingmall.fragment.HomeFragment;
import com.example.frank.myshoppingmall.fragment.HotFragment;
import com.example.frank.myshoppingmall.fragment.MineFragment;
import com.example.frank.myshoppingmall.widget.FragmentTabHost;
import com.example.frank.myshoppingmall.widget.MyToolBar;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "MainActivity";

    private FragmentTabHost mFragmentTabHost;
    private LayoutInflater  mLayoutInflater;
    private MyToolBar       mMyToolBar;

    private Class fragmentArray[] = {HomeFragment.class, HotFragment.class, CategoryFragment.class, CartFragment
            .class, MineFragment.class};

    private int[] mImageViewArray = {R.drawable.selector_icon_home, R.drawable.selector_icon_hot, R.drawable
            .selector_icon_category, R.drawable.selector_icon_cart, R.drawable.selector_icon_mine};

    private String[] mTexts = {"首页", "热卖", "种类", "cart", "我的"};
    private CartFragment mCartfragment;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTab();

    }

    private void initTab() {

        mFragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);

        mFragmentTabHost.setup(this, getSupportFragmentManager(), R.id.realcontent);

        mLayoutInflater = LayoutInflater.from(this);

        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {

            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(mTexts[i]).setIndicator(getTabItemView(i));

            mFragmentTabHost.addTab(tabSpec, fragmentArray[i], null);

        }

        mFragmentTabHost.setCurrentTab(0);
        mFragmentTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_END);

        mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                if (tabId == "cart") {
                    refDada();
                }

            }
        });

    }

    private void refDada() {

        if (mCartfragment == null) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("cart");
            if (fragment != null) {
                mCartfragment = (CartFragment) fragment;
                mCartfragment.refData();
            }
        } else {
            mCartfragment.refData();
        }


    }


    private View getTabItemView(int index) {

        View itemView = mLayoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.tab_iv);
        imageView.setImageResource(mImageViewArray[index]);

        TextView textView = (TextView) itemView.findViewById(R.id.tab_tv);

        textView.setText(mTexts[index]);

        return itemView;

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
