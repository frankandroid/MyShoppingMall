package com.example.frank.myshoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.bean.LeftMenuBean;

import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/4/28 15:07
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class LeftMenuAdapter extends RecyclerView.Adapter<LeftMenuAdapter.LeftMenuViewHolder> {

    private List<LeftMenuBean> mLeftMenuBeans;
    private Context            mContext;

    private LayoutInflater mLayoutInflater;

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position,LeftMenuBean leftMenuBean);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }



    public LeftMenuAdapter(List<LeftMenuBean> leftMenuBeans, Context context) {
        mLeftMenuBeans = leftMenuBeans;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public LeftMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LeftMenuViewHolder leftMenuViewHolder = new LeftMenuViewHolder(mLayoutInflater.inflate(R.layout
                .item_categoryfragment_leftmenu, null));

        return leftMenuViewHolder;
    }

    @Override
    public void onBindViewHolder(final LeftMenuViewHolder holder, int position) {

        holder.mTextView.setText(mLeftMenuBeans.get(position).getName());

        if (mOnItemClickLitener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position =holder.getLayoutPosition();

                    mOnItemClickLitener.onItemClick(holder.itemView,position,mLeftMenuBeans.get(position));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mLeftMenuBeans.size();
    }

    public class LeftMenuViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public LeftMenuViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.left_text);
        }
    }
}
