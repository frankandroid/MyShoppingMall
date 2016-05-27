package com.example.frank.myshoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.bean.HomeItemInfoBean;
import com.example.frank.myshoppingmall.http.ImageLoaderUtil;

import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/4/22 16:06
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class HomeRecycleViewAdapter extends RecyclerView.Adapter<HomeRecycleViewAdapter.HomeViewHolder> {

    /**
     * 定义两种type
     */
    public static final int STYLE_LEFT  = 0;
    public static final int STYLE_RIGHT = 1;

    /**
     * 定义三个值，用来判断用户点击的是那个
     */

    public static final int BIG = 1;
    public static final int SMALLTOP = 2;
    public static final int SMALLBOTTOM = 3;



    private List<HomeItemInfoBean> mHomeItemInfoBeans;
    private Context                mContext;
    private LayoutInflater         mLayoutInflater;

    private OnRecycleItemClickListener mOnRecycleItemClickListener;

    public interface OnRecycleItemClickListener {
        void onItemClick(HomeItemInfoBean homeItemInfoBean, int i,int position);

        void onItemLongClick(HomeItemInfoBean homeItemInfoBean, int i,int positoin);
    }

    public void setOnRecycleItemClickListener(OnRecycleItemClickListener listener) {
        mOnRecycleItemClickListener = listener;
    }


    public HomeRecycleViewAdapter(List<HomeItemInfoBean> homeItemInfoBeans, Context context) {
        mHomeItemInfoBeans = homeItemInfoBeans;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        HomeViewHolder homeViewHolder = null;
        if (viewType == STYLE_LEFT) {

            homeViewHolder = new HomeViewHolder(mLayoutInflater.inflate(R.layout.item_homefragment_recycleview_left,
                    parent,false));
        } else {
            homeViewHolder = new HomeViewHolder(mLayoutInflater.inflate(R.layout.item_homefragment_recycleview_left,
                    parent,false));
        }
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(HomeViewHolder homeHolder, int position) {

        final HomeItemInfoBean homeItemInfoBean = mHomeItemInfoBeans.get(position);

        homeHolder.mtitle.setText(homeItemInfoBean.getTitle());

        ImageLoaderUtil.displayImage(homeItemInfoBean.getCpOne().getImgUrl(), homeHolder.mImageViewBig);
        ImageLoaderUtil.displayImage(homeItemInfoBean.getCpTwo().getImgUrl(), homeHolder.mImageViewSmallTop);
        ImageLoaderUtil.displayImage(homeItemInfoBean.getCpThree().getImgUrl(), homeHolder.mImageViewSmallBottom);

        clickhelpe(homeHolder.mImageViewBig,1,homeItemInfoBean,position);
        clickhelpe(homeHolder.mImageViewSmallTop,2,homeItemInfoBean,position);
        clickhelpe(homeHolder.mImageViewSmallBottom,3,homeItemInfoBean,position);

    }

    @Override
    public int getItemCount() {
        return mHomeItemInfoBeans.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (position % 2 == 0) {
            return STYLE_LEFT;
        } else {
            return STYLE_RIGHT;
        }

    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        private TextView mtitle;
        private ImageView mImageViewBig;
        private ImageView mImageViewSmallTop;
        private ImageView mImageViewSmallBottom;

        public HomeViewHolder(View itemView) {
            super(itemView);

            mtitle = (TextView) itemView.findViewById(R.id.item_homefragment_tv_title);
            mImageViewBig = (ImageView) itemView.findViewById(R.id.item_homefragment_iv_big);
            mImageViewSmallTop = (ImageView) itemView.findViewById(R.id.item_homefragment_iv_small_top);
            mImageViewSmallBottom = (ImageView) itemView.findViewById(R.id.item_homefragment_iv_small_bottom);

        }
    }

    /**
     * 对其中某一个控件设置监听的方法的抽取
     */

    public void clickhelpe(View v, final int i , final HomeItemInfoBean homeItemInfoBean, final int position){

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRecycleItemClickListener.onItemClick(homeItemInfoBean,i,position);
            }
        });


        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                mOnRecycleItemClickListener.onItemLongClick(homeItemInfoBean, i,position);
                return false;
            }
        });

    }



    public void addData(int position,HomeItemInfoBean homeItemInfoBean){
        mHomeItemInfoBeans.add(position,homeItemInfoBean);
        notifyItemInserted(position);
    }

    public void deleteData(int position,HomeItemInfoBean homeItemInfoBean){
        mHomeItemInfoBeans.remove(position);
        notifyItemRemoved(position);
    }



}
