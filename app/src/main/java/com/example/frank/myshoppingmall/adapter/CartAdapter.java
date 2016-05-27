package com.example.frank.myshoppingmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.bean.CartBean;
import com.example.frank.myshoppingmall.http.ImageLoaderUtil;
import com.example.frank.myshoppingmall.util.CartProvider;
import com.example.frank.myshoppingmall.widget.NumberAddSubView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/5/1 21:30
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private static final String TAG = "CartAdapter";
    private List<CartBean> mCartBeans;
    private Context        mContext;
    private LayoutInflater mLayoutInflater;
    private CartProvider   mCartProvider;

    //定义一个list集合用来装没有选中的数据
    public List<Integer> positions = new ArrayList<>();


    private OnItemCheckListener mOnItemCheckListener;

    private onAddAndSubButtonClickListener mOnAddAndSubButtonClickListener;

    public interface onAddAndSubButtonClickListener {
        void onAddButtonClick(CartBean cartBean);

        void onSubButtonClick(CartBean cartBean);
    }

    public void setOnAddAndSubButtonClickListener(onAddAndSubButtonClickListener clickListener) {
        this.mOnAddAndSubButtonClickListener = clickListener;
    }

    public interface OnItemCheckListener {
        void onItemChecked(CartBean cartBean, boolean isChecked);
    }

    public void setOnItemCheckListener(OnItemCheckListener listener) {
        this.mOnItemCheckListener = listener;
    }


    public CartAdapter(List<CartBean> cartBeans, Context context) {
        mCartBeans = cartBeans;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

    }

    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CartViewHolder cartViewHolder = new CartViewHolder(mLayoutInflater.inflate(R.layout.item_fragment_cart, null));

        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, final int position) {

        holder.mNumberAddSubView.setValue(mCartBeans.get(position).count);
        holder.mTextTitle.setText(mCartBeans.get(position).name);
        holder.mTextPrice.setText("￥" + mCartBeans.get(position).price + "");
        ImageLoaderUtil.displayImage(mCartBeans.get(position).imgUrl, holder.mImageView);

        holder.mCheckBox.setTag(position);//
        //checkbox  复用问题
        if (positions != null) {
             holder.mCheckBox.setChecked(!positions.contains(Integer.valueOf(position)));
            //注意要添加下面这一行，默认是为true，不添加这个就和positions没有联系起来。
             mCartBeans.get(position).isChecked = !positions.contains(Integer.valueOf(position));
            Log.d(TAG,positions.contains(Integer.valueOf(position))+"");
            Log.d(TAG,positions.toString()+">>>>>first");
        }

        holder.mNumberAddSubView.setOnButtonClickListener(new NumberAddSubView.OnButtonClickListener() {
            @Override
            public void onButtonAddClick(View view, int value) {
                mCartProvider = new CartProvider(mContext);
                mCartProvider.put(mCartBeans.get(position));
                mCartBeans.get(position).count=value;
                mOnAddAndSubButtonClickListener.onAddButtonClick(mCartBeans.get(position));
            }

            //这里得到的数据是减过之后的数据
            @Override
            public void onButtonSubClick(View view, int value) {
                mOnAddAndSubButtonClickListener.onSubButtonClick(mCartBeans.get(position));
                mCartProvider = new CartProvider(mContext);
                if (value > 0) {
                    mCartProvider.sub(mCartBeans.get(position));
                    mCartBeans.get(position).count=value;
                } else {
                    mCartProvider.delete(mCartBeans.get(position));
                    mCartBeans.remove(position);
                    //在最后移除的时候，总是会把自己的状态给下一个item。所以对应item位置的值也移除掉。
                    //这样remove的话集合中可能是没有这个元素的，比如他自己本身是选中的时候。
                    //[1, 2, 3]>>>>>first
                    positions.remove(Integer.valueOf(position));

                    List<Integer> list = new ArrayList<Integer>();
                    for (int i = 0; i < positions.size(); i++) {
                        if (positions.get(i)>position){
                            list.add(i,positions.get(i)-1);
                        }else{
                            list.add(i,positions.get(i));
                        }
                    }
                    positions.clear();
                    positions.addAll(list);
                    notifyDataSetChanged();
                }
            }
        });



        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    if (positions.contains(holder.mCheckBox.getTag())){

                        positions.remove(Integer.valueOf(position));
                        mOnItemCheckListener.onItemChecked(mCartBeans.get(position), isChecked);
                        mCartBeans.get(position).isChecked = true;

                    }
                }else{
                    if (!positions.contains(holder.mCheckBox.getTag())){
                        positions.add(position);
                        mOnItemCheckListener.onItemChecked(mCartBeans.get(position), isChecked);
                        mCartBeans.get(position).isChecked=false;
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if (mCartBeans != null && mCartBeans.size() > 0) {
            return mCartBeans.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private CheckBox         mCheckBox;
        private TextView         mTextTitle;
        private TextView         mTextPrice;
        private NumberAddSubView mNumberAddSubView;
        private ImageView        mImageView;

        public CartViewHolder(View itemView) {
            super(itemView);

            mCheckBox = (CheckBox) itemView.findViewById(R.id.item_fragment_cart_ck);
            mTextTitle = (TextView) itemView.findViewById(R.id.item_fragment_cart_tv_title);
            mTextPrice = (TextView) itemView.findViewById(R.id.item_fragment_cart_tv_price);
            mNumberAddSubView = (NumberAddSubView) itemView.findViewById(R.id.item_fragment_cart_num);
            mImageView = (ImageView) itemView.findViewById(R.id.item_fragment_cart_iv);
        }
    }


}
