package com.example.frank.myshoppingmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.bean.PayBean;

import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/5/25 21:45
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class OrderListAdapter extends BaseAdapter {

    private List<PayBean>  mPayBeans;
    private Context        mContext;
    private LayoutInflater mLayoutInflater;


    private int index;

    public void setInder(int position) {
        this.index = position;
    }

    //定义radiobutton点击事件的监听

    private OnRadioButtonClickListener mOnRadioButtonClickListener;

    public void setOnRadioButtonClickListener(OnRadioButtonClickListener listener) {
        this.mOnRadioButtonClickListener = listener;
    }

    public interface OnRadioButtonClickListener {
        void onRadioButtonClick(int position);
    }


    public OrderListAdapter(List<PayBean> payBeans, Context context) {
        mPayBeans = payBeans;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mPayBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mPayBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_order_paylist, null);
            holder = new ViewHolder();
            holder.mName = (TextView) convertView.findViewById(R.id.item_order_paylist_tv_name);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.item_order_paylist_iv);
            holder.mDescript = (TextView) convertView.findViewById(R.id.item_order_paylist_tv_descript);
            holder.mRadioButton= (RadioButton) convertView.findViewById(R.id.item_order_paylist_rb);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mImageView.setImageResource(mPayBeans.get(position).getImageid());
        holder.mName.setText(mPayBeans.get(position).getName());
        holder.mDescript.setText(mPayBeans.get(position).getDescript());

        if (position == index) {
            holder.mRadioButton.setChecked(true);
        }else{
            holder.mRadioButton.setChecked(false);
        }

        holder.mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRadioButtonClickListener.onRadioButtonClick(position);
            }
        });

        return convertView;

    }

    private static class ViewHolder {

        private ImageView   mImageView;
        private TextView    mName;
        private TextView    mDescript;
        private RadioButton mRadioButton;

    }


}
