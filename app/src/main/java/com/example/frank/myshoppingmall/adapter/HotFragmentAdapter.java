package com.example.frank.myshoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.bean.CartBean;
import com.example.frank.myshoppingmall.bean.HotItemInfoBean;
import com.example.frank.myshoppingmall.http.ImageLoaderUtil;
import com.example.frank.myshoppingmall.util.CartProvider;

import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/4/25 22:36
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class HotFragmentAdapter extends RecyclerView.Adapter<HotFragmentAdapter.HotViewHolder> {

    private Context mContext;
    private List<HotItemInfoBean.HotListEntity> mHotListEntities;

    private View mRootView;
    private LayoutInflater mLayoutInflater;

    private onItemClickListener mOnItemClickListener;

    public void setData(List<HotItemInfoBean.HotListEntity> list) {
        mHotListEntities.addAll(list);
    }

    public interface onItemClickListener{
        void onItemClick(HotItemInfoBean.HotListEntity listEntity,int position);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }



    public HotFragmentAdapter(Context context, List<HotItemInfoBean.HotListEntity> hotListEntities) {
        mContext = context;
        mHotListEntities = hotListEntities;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public HotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       HotViewHolder hotViewHolder= new HotViewHolder(mLayoutInflater.inflate(R.layout.item_hotfragment,parent,false));

        return hotViewHolder;
    }

    public void onBindViewHolder(HotViewHolder holder, final int position) {

        final HotItemInfoBean.HotListEntity hotListEntity = mHotListEntities.get(position);

        holder.mPrice.setText(hotListEntity.price+"");
        holder.mName.setText(hotListEntity.name+"");

        ImageLoaderUtil.displayImage(hotListEntity.imgUrl, holder.mImageView);

        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(hotListEntity,position);
            }
        });

        holder.mButtonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartBean convert = CartProvider.convert(hotListEntity);

                CartProvider cartProvider = new CartProvider(mContext);

                cartProvider.put(convert);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mHotListEntities.size();
    }

    public class HotViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        private TextView mName;
        private TextView mPrice;
        private Button mButtonBuy;

        public HotViewHolder(View itemView) {
            super(itemView);

            mRootView = itemView;
            mImageView = (ImageView) itemView.findViewById(R.id.item_hotfragment_iv);
            mName = (TextView) itemView.findViewById(R.id.item_hotfragment_tv_name);
            mPrice = (TextView) itemView.findViewById(R.id.item_fragment_tv_price);
            mButtonBuy = (Button) itemView.findViewById(R.id.item_hotfragment_bt_buy);
        }
    }

}
