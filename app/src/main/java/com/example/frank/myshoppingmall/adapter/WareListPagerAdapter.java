package com.example.frank.myshoppingmall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/5/19 15:39
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class WareListPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> mFragments;

    private String[] tabTitles = {"默认","价格","销量"};

    public WareListPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
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
        return tabTitles[position];
    }
}
