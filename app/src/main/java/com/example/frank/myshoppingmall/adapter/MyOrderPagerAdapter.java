package com.example.frank.myshoppingmall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/5/31 17:55
 * 描述	      ${这个是我的订单页面的pageradapter}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class MyOrderPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;


    private String[] mTitles ={"全部","支付成功","待支付","支付失败"};

    public MyOrderPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
