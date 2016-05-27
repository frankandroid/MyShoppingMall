package com.example.frank.myshoppingmall.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.frank.myshoppingmall.Constants;
import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.adapter.WareListPagerAdapter;
import com.example.frank.myshoppingmall.fragment.WareDefaultFragment;
import com.example.frank.myshoppingmall.fragment.WarePriceFragment;
import com.example.frank.myshoppingmall.fragment.WareSalesFragment;
import com.example.frank.myshoppingmall.widget.MyToolBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/5/19 14:02
 * 描述	      ${商品列表的activity}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class WareListActivity extends AppCompatActivity {


    private TextView mTextView;

    private MyToolBar mMyToolBar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;


    private List<Fragment> mFragments;
    private String         mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warelist);
        mId = getIntent().getIntExtra(Constants.WARE_REQUEST_ID, 10) + "";


        initView();
        initData();


    }

    private void initData() {

    }

    private void initView() {
        mMyToolBar = (MyToolBar) findViewById(R.id.warelist_mytoolbar);
        mMyToolBar.setTitle("商品列表");

        mViewPager = (ViewPager) findViewById(R.id.warelist_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.warelist_tablayout);

        mFragments = new ArrayList<>();

        WareDefaultFragment wareDefaultFragment = WareDefaultFragment.newInstance(mId);

        WarePriceFragment warePriceFragment = WarePriceFragment.newInstance("hahahahahhahaha");

        WareSalesFragment wareSalesFragment = WareSalesFragment.newInstance("hehehehehheheh");

        mFragments.add(wareDefaultFragment);
        mFragments.add(warePriceFragment);
        mFragments.add(wareSalesFragment);
        WareListPagerAdapter listPagerAdapter = new WareListPagerAdapter(getSupportFragmentManager(),mFragments);

        mViewPager.setAdapter(listPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

    }


}
