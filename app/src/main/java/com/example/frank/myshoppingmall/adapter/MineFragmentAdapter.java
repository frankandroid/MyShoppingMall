package com.example.frank.myshoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.bean.MineItemInfoBean;

import java.util.ArrayList;

/**
 * 创建者     Frank
 * 创建时间   2016/5/21 9:38
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class MineFragmentAdapter extends RecyclerView.Adapter<MineFragmentAdapter.MineViewHolder> {

    private Context mContext;
    private ArrayList<MineItemInfoBean> mMineItemInfoBeans;

    private LayoutInflater mLayoutInflater;


    public interface OnItemClickLitener
    {
        void onItemClick(MineItemInfoBean mineItemInfoBean,int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }



    public MineFragmentAdapter(Context context, ArrayList<MineItemInfoBean> mineItemInfoBeans) {
        mContext = context;
        mMineItemInfoBeans = mineItemInfoBeans;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MineViewHolder mineViewHolder = new MineViewHolder(mLayoutInflater.inflate(R.layout.item_fragment_mine,parent,false));

        return mineViewHolder;
    }

    @Override
    public void onBindViewHolder(MineViewHolder holder, final int position) {

        holder.mTextView.setText(mMineItemInfoBeans.get(position).getItemname());
        holder.mImageView.setImageResource(mMineItemInfoBeans.get(position).getImage());

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(mMineItemInfoBeans.get(position),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMineItemInfoBeans.size();
    }

    class MineViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTextView;
        private LinearLayout mLinearLayout;

        public MineViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.item_mine_iv);
            mTextView = (TextView) itemView.findViewById(R.id.item_mine_tv);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.item_mine_ll);

        }
    }


}
