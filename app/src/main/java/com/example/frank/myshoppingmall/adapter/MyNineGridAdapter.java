package com.example.frank.myshoppingmall.adapter;

import android.content.Context;
import android.view.View;

import com.w4lle.library.NineGridAdapter;

import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/5/31 16:18
 * 描述	      ${这个是九宫格的适配器，仿微博微信}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class MyNineGridAdapter extends NineGridAdapter {


    public MyNineGridAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public String getUrl(int positopn) {
        return null;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view) {
        return null;
    }
}
