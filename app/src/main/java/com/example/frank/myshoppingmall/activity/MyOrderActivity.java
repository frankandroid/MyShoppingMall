package com.example.frank.myshoppingmall.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.adapter.MyOrderPagerAdapter;
import com.example.frank.myshoppingmall.fragment.OrderAllFragment;
import com.example.frank.myshoppingmall.fragment.OrderFailedFragment;
import com.example.frank.myshoppingmall.fragment.OrderNoPaymentFragment;
import com.example.frank.myshoppingmall.fragment.OrderSuccessFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 创建者     Frank
 * 创建时间   2016/5/21 11:16
 * 描述	      ${我的订单页面的activity}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class MyOrderActivity extends AppCompatActivity {

    private List<Fragment>         mFragments;
    private ViewPager              mViewPager;
    private TabLayout              mTabLayout;
    private OrderAllFragment       mOrderAllFragment;
    private OrderSuccessFragment   mOrderSuccessFragment;
    private OrderNoPaymentFragment mOrderNoPaymentFragment;
    private OrderFailedFragment    mOrderFailedFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);

        initView();

        ButterKnife.bind(this);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.activity_myorder_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.activity_myorder_tablayout);
        mFragments = new ArrayList<>();

        mOrderAllFragment = new OrderAllFragment();
        mOrderSuccessFragment = new OrderSuccessFragment();
        mOrderNoPaymentFragment = new OrderNoPaymentFragment();
        mOrderFailedFragment = new OrderFailedFragment();

        mFragments.add(mOrderAllFragment);
        mFragments.add(mOrderSuccessFragment);
        mFragments.add(mOrderNoPaymentFragment);
        mFragments.add(mOrderFailedFragment);

        MyOrderPagerAdapter myOrderPagerAdapter = new MyOrderPagerAdapter(getSupportFragmentManager(),mFragments);

        mViewPager.setAdapter(myOrderPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

    }
}
