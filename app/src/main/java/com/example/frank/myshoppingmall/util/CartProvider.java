package com.example.frank.myshoppingmall.util;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.example.frank.myshoppingmall.bean.CartBean;
import com.example.frank.myshoppingmall.bean.HotItemInfoBean;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者     Frank
 * 创建时间   2016/5/1 23:07
 * 描述	      ${购物车的工具类，先从本地取出数据，放入到sparsearray中。put的时候先判断有没有数据，有就加一，没有就设置为1，
 * put完以后要把数据提交到sharedprefrence中}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class CartProvider {


    public static final  String CART_JSON = "cartjson";
    private static final String TAG       = "CartProvider";

    public SparseArray<CartBean> mDatas;

    private Context mContext;


    public CartProvider(Context context) {
        mContext = context;

        mDatas = new SparseArray<>();

        List<CartBean> dataFromLocal = getDataFromLocal();

        listToSparse(dataFromLocal);
    }

    public List<CartBean> getDataFromLocal() {

        String json = PreferencesUtils.getString(mContext, CART_JSON);

        List<CartBean> cartBeans = null;
        if (json != null && !json.equals("")) {
            cartBeans = JSONUtil.fromJson(json, new TypeToken<List<CartBean>>() {
            }.getType());
        }

        return cartBeans;

    }


    public void listToSparse(List<CartBean> beans) {


        if (beans != null && beans.size() > 0) {
            for (int i = 0; i < beans.size(); i++) {
                mDatas.put(beans.get(i).id, beans.get(i));
            }
        }
    }

    public List<CartBean> sparseToList() {
        List<CartBean> cartBeans = null;
        if (mDatas != null) {
            cartBeans = new ArrayList<>();
            for (int i = 0; i < mDatas.size(); i++) {
                cartBeans.add(mDatas.valueAt(i));
            }
        }
        return cartBeans;
    }

    public void commit() {

        List<CartBean> cartBeans = sparseToList();

        if (cartBeans != null) {
            String toJSON = JSONUtil.toJSON(cartBeans);
            PreferencesUtils.putString(mContext, CART_JSON, toJSON);
        }
    }


    /**
     * 点击添加按钮第二次put的时候，由于cartbean的count还是原来的count，并没有进行更新操作。所以会出现点击加了之后只会往上增加1的情况
     * <p/>
     * cartbean 是adapter里面传过来的数据，并没有进行数据更新的操作
     * cartbean1 是mdatas里面的数据，这个是有进行过更新操作的。
     *
     * @param cartBean
     */
    public void put(CartBean cartBean) {

        CartBean cartBean1 = mDatas.get(cartBean.id);

        if (cartBean1 != null) {
            cartBean1.count = cartBean1.count + 1;
        } else {
            cartBean1 = cartBean;
            cartBean1.count = 1;
        }

        mDatas.put(cartBean.id, cartBean1);

        commit();
    }

    public void delete(CartBean cartBean) {

        mDatas.delete(cartBean.id);
        commit();
    }

    public void sub(CartBean cartBean) {

        CartBean cartBean1 = mDatas.get(cartBean.id);

        if (cartBean1.count == 1) {
            mDatas.delete(cartBean1.id);
        } else {

            Log.d(TAG,cartBean1.count+"");
            cartBean1.count = cartBean1.count - 1;
            Log.d(TAG,cartBean1.count+">>>>>>>>>>>>>>>>>>>>>>>>>>");
            mDatas.put(cartBean1.id, cartBean1);
        }
        commit();
    }


    public static CartBean convert(HotItemInfoBean.HotListEntity listEntity) {

        CartBean cartBean = new CartBean();

        cartBean.id = listEntity.id;
        cartBean.imgUrl = listEntity.imgUrl;
        cartBean.name = listEntity.name;
        cartBean.price = listEntity.price;
        cartBean.isChecked = true;
        cartBean.count = 1;

        return cartBean;
    }


}
