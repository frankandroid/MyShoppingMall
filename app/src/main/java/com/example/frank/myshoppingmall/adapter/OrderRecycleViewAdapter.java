package com.example.frank.myshoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.bean.CartBean;
import com.example.frank.myshoppingmall.http.ImageLoaderUtil;

import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/5/27 9:44
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class OrderRecycleViewAdapter extends RecyclerView.Adapter<OrderRecycleViewAdapter.OrderRecycleViewHolder> {


    private Context        mContext;
    private LayoutInflater mLayoutInflater;
    private List<CartBean> mCartBeans;

    public OrderRecycleViewAdapter(Context context, List<CartBean> cartBeans) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mCartBeans = cartBeans;
    }

    @Override
    public OrderRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        OrderRecycleViewHolder orderRecycleViewHolder = new OrderRecycleViewHolder(mLayoutInflater.inflate(R.layout
                .item_order_recycleview, parent, false));

        return orderRecycleViewHolder;
    }

    @Override
    public void onBindViewHolder(OrderRecycleViewHolder holder, int position) {

        ImageLoaderUtil.displayImage(mCartBeans.get(position).imgUrl, holder.mImageView);
        holder.mName.setText(mCartBeans.get(position).name);
        holder.mPrice.setText("price: " + mCartBeans.get(position).price + "");
        holder.mCount.setText("count：" + mCartBeans.get(position).count + "");
        holder.mSum.setText("sum:"+mCartBeans.get(position).price * mCartBeans.get(position).count + "");
    }

    @Override
    public int getItemCount() {
        return mCartBeans.size();
    }

    public class OrderRecycleViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView  mName;
        private TextView  mPrice;
        private TextView  mCount;
        private TextView  mSum;

        public OrderRecycleViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.item_order_iv);
            mName = (TextView) itemView.findViewById(R.id.item_order_tv_name);
            mPrice = (TextView) itemView.findViewById(R.id.item_order_tv_price);
            mCount = (TextView) itemView.findViewById(R.id.item_order_tv_count);
            mSum = (TextView) itemView.findViewById(R.id.item_order_tv_sum);

        }
    }
}
