package com.example.frank.myshoppingmall.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.bean.CategoryContentBean;
import com.example.frank.myshoppingmall.http.ImageLoaderUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/4/28 16:53
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class CategoryContentAdapter extends RecyclerView.Adapter<CategoryContentAdapter.CategoryViewHolder> {

    private List<CategoryContentBean.ContentItem> mContentItems;
    private Context                               mContext;
    private LayoutInflater                        mLayoutInflater;

    //两种viewtype类型。
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_LODEMORE = 2;
    public LoadMoreViewHodler mLoadMoreViewHodler;

    //两种状态
    public static final int STATE_END          = 0;//没有更多数据的情况
    public static final int STATE_LOADING      = 1;

    public interface OnContentItemClickListener {
        void onContentItemClick(View view, int position, CategoryContentBean.ContentItem contentItem);
    }

    private OnContentItemClickListener mOnContentItemClickListener;

    public void setOnContentItemClickListener(OnContentItemClickListener mOnContentItemClickListener) {
        this.mOnContentItemClickListener = mOnContentItemClickListener;
    }


    public CategoryContentAdapter(List<CategoryContentBean.ContentItem> contentItems, Context context) {
        mContentItems = contentItems;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_NORMAL) {

            ContentViewHolder contentViewHolder = new ContentViewHolder(mLayoutInflater.inflate(R.layout
                    .item_categoryfragment_content, null));

            return contentViewHolder;
        } else {
            mLoadMoreViewHodler = new LoadMoreViewHodler(mLayoutInflater.inflate(R.layout
                    .item_categoryloadmore, null));

            return mLoadMoreViewHodler;
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_LODEMORE;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, final int position) {

        if (position == getItemCount() - 1) {

            LoadMoreViewHodler loadMoreViewHodler = (LoadMoreViewHodler) holder;
        } else {
            ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
            contentViewHolder.mDesc.setText(mContentItems.get(position).getName());
            contentViewHolder.mPrice.setText(mContentItems.get(position).getPrice() + "");
            ImageLoaderUtil.displayImage(mContentItems.get(position).getImgUrl(), contentViewHolder.mImageView);

            contentViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnContentItemClickListener.onContentItemClick(holder.itemView, position, mContentItems.get
                            (position));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mContentItems.size() + 1;
    }

    public class ContentViewHolder extends CategoryViewHolder {

        private TextView  mPrice;
        private TextView  mDesc;
        private ImageView mImageView;

        public ContentViewHolder(View itemView) {
            super(itemView);
            mPrice = (TextView) itemView.findViewById(R.id.item_categoryfragment_tv_price);
            mDesc = (TextView) itemView.findViewById(R.id.item_categoryfragment_tv_desc);
            mImageView = (ImageView) itemView.findViewById(R.id.item_categoryfragment_iv);
        }
    }

    public class LoadMoreViewHodler extends CategoryViewHolder {

        private TextView     mLoadMoreTv;
        private TextView     mNoMoreTv;
        private ProgressBar  mProgressBar;
        private LinearLayout mLinearLayoutLodeMore;

        public LoadMoreViewHodler(View itemView) {
            super(itemView);

            mLoadMoreTv = (TextView) itemView.findViewById(R.id.item_loadmore_tv);
            mNoMoreTv = (TextView) itemView.findViewById(R.id.item_nomore_tv);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.item_loadmore_pb);
            mLinearLayoutLodeMore = (LinearLayout) itemView.findViewById(R.id.item_loadmore_ll);
        }

        public void setdata(@State int data){

            if (data==STATE_END){
                mLinearLayoutLodeMore.setVisibility(View.GONE);
                mNoMoreTv.setVisibility(View.VISIBLE);
            }else{
                mLinearLayoutLodeMore.setVisibility(View.VISIBLE);
                mNoMoreTv.setVisibility(View.GONE);
            }

        }


    }

    @IntDef({STATE_END, STATE_LOADING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public CategoryViewHolder(View itemView) {
            super(itemView);
        }
    }
}
