package com.example.frank.myshoppingmall.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.bean.Banner;
import com.example.frank.myshoppingmall.http.HttpHelper;
import com.example.frank.myshoppingmall.http.ImageLoaderUtil;

import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/4/24 14:19
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class HomeViewpagerAdapter extends PagerAdapter {

    private static final String TAG = "HomeViewpagerAdapter";
    private static List<Banner>   mBanners;
    private        LayoutInflater mLayoutInflater;
    private        Context        mContext;
    private        HttpHelper     mHttpHelper;
    private        ImageView      mImageView;
    private        TextView       mTextView;
    private        LinearLayout   mLinearLayout;

    public static final int     THE_MAX_NUMBER    = 100;
    public int     THE_BANNER_AMOUNT = 5;
    public boolean isFinished        = false;
    private ImageView mIndicator;


    public HomeViewpagerAdapter(Context context, List<Banner> banners) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mBanners = banners;

        THE_BANNER_AMOUNT = banners.size();
    }


    @Override
    public int getCount() {
        return mBanners.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


       // Log.d(TAG,"instantiateItem   "+position);
        View bannerview = mLayoutInflater.inflate(R.layout.item_viewpager, container, false);

        mImageView = (ImageView) bannerview.findViewById(R.id.item_viewpage_iv);
        mTextView = (TextView) bannerview.findViewById(R.id.item_viewpage_tv);

        mLinearLayout = (LinearLayout) bannerview.findViewById(R.id.item_viewpage_ll_container);
        ImageLoaderUtil.displayImage(mBanners.get(position).getImgUrl(), mImageView);
        mTextView.setText(mBanners.get(position).getName());

        setIndicator(position);

        container.addView(bannerview);

        return bannerview;
    }


    public void setIndicator(int position) {

        //Log.d(TAG,position+"adapter");
        mLinearLayout.removeAllViews();
        for (int i = 0; i < mBanners.size(); i++) {
            ImageView imageView = new ImageView(mContext);

            if (i == position) {
                imageView.setImageResource(R.mipmap.dot_focus);
            } else {
                imageView.setImageResource(R.mipmap.dot_normal);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            params.leftMargin = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, mContext
                    .getResources().getDisplayMetrics()) + .5f);

            mLinearLayout.addView(imageView, params);
        }

    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }



}
