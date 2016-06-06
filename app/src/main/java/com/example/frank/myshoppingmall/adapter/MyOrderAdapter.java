package com.example.frank.myshoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.bean.OrderListBean;

import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/5/31 15:44
 * 描述	      ${这个是我的订单页面的recycleview的a}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderAdapterViewHolder> {


    private List<OrderListBean> mOrderListBeans;
    private Context             mContext;
    private LayoutInflater      mLayoutInflater;

    public MyOrderAdapter(List<OrderListBean> orderListBeans, Context context) {
        mOrderListBeans = orderListBeans;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyOrderAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyOrderAdapterViewHolder myOrderAdapterViewHolder = new MyOrderAdapterViewHolder(mLayoutInflater.inflate(R
                .layout.item_myorder_ry, parent, false));

        return myOrderAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(MyOrderAdapterViewHolder holder, int position) {

        holder.mTextView.setText(mOrderListBeans.get(position).getOrderNum());
    }

    @Override
    public int getItemCount() {
        return mOrderListBeans.size();
    }

    public class MyOrderAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public MyOrderAdapterViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item_myorder_tv_test);
        }
    }

}
