package com.example.frank.myshoppingmall.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.activity.OrderActivity;
import com.example.frank.myshoppingmall.adapter.CartAdapter;
import com.example.frank.myshoppingmall.bean.CartBean;
import com.example.frank.myshoppingmall.decoration.CardViewtemDecortion;
import com.example.frank.myshoppingmall.util.CartProvider;
import com.example.frank.myshoppingmall.widget.MyToolBar;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建者     Frank
 * 创建时间   2016/4/18 17:35
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class CartFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "CartFragment";
    @Bind(R.id.mycarttoolbar)
    MyToolBar    mMycarttoolbar;
    @Bind(R.id.fragment_cart_ry)
    RecyclerView mFragmentCartRy;
    @Bind(R.id.fragment_cart_buttomck)
    CheckBox     mFragmentCartButtomck;
    @Bind(R.id.fragment_cart_sum)
    TextView     mFragmentCartSum;
    @Bind(R.id.fragment_cart_bt_submit)
    Button       mFragmentCartBtSubmit;
    @Bind(R.id.fragment_cart_bt_delete)
    Button       mFragmentCartBtDelete;


    private View   mRootView;
    private Button mRightButton;

    private MyToolBar         mMyToolBar;
    private AppCompatActivity mAppCompatActivity;

    private RecyclerView.LayoutManager mLayoutManager;
    private CardViewtemDecortion       mCardViewtemDecortion;
    private RecyclerView.ItemAnimator  mItemAnimator;
    private CartProvider               mCartProvider;
    private List<CartBean>             mCartBeans;

    private double        sum;
    private CartAdapter   mCartAdapter;
    private DecimalFormat mDf;

    private boolean isShouldDoUnCheck= true;


    private List<CartBean> mOrderList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppCompatActivity = (AppCompatActivity) getActivity();
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_cart, null);
        ButterKnife.bind(this, mRootView);
        initView();

        initData();


        return mRootView;
    }

    private void initData() {

        for (CartBean cartbean : mCartBeans) {
            sum = sum + cartbean.price * cartbean.count;
        }
        mFragmentCartSum.setText("合计" + mDf.format(sum));
    }

    private void initView() {

        mDf = new DecimalFormat("######0.00");

        mFragmentCartBtDelete.setOnClickListener(this);
        mFragmentCartBtSubmit.setOnClickListener(this);
        mMyToolBar = (MyToolBar) mRootView.findViewById(R.id.mycarttoolbar);
        mAppCompatActivity.setSupportActionBar(mMyToolBar);
        mMyToolBar.setTitle("购物车");
        mMyToolBar.setRightButtonText("编辑");

        mMyToolBar.setOnRightButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyToolBar.getRightButton().getText() == "编辑") {
                    mMyToolBar.setRightButtonText("完成");
                    mFragmentCartBtSubmit.setVisibility(View.GONE);
                    mFragmentCartBtDelete.setVisibility(View.VISIBLE);
                    mFragmentCartSum.setVisibility(View.GONE);
                    List<Integer> list = new ArrayList<Integer>();
                    for (int i = 0; i < mCartBeans.size(); i++) {
                        list.add(i);
                    }
                    mCartAdapter.positions.addAll(list);
                    mCartAdapter.notifyDataSetChanged();
                    mFragmentCartButtomck.setChecked(false);
                } else if (mMyToolBar.getRightButton().getText() == "完成") {
                    mMyToolBar.setRightButtonText("编辑");
                    mFragmentCartBtSubmit.setVisibility(View.VISIBLE);
                    mFragmentCartBtDelete.setVisibility(View.GONE);
                    mFragmentCartSum.setVisibility(View.VISIBLE);
                    mFragmentCartButtomck.setChecked(true);
                }
            }
        });
        mCartProvider = new CartProvider(getContext());

        mCartBeans = mCartProvider.sparseToList();

        mCartAdapter = new CartAdapter(mCartBeans, getContext());

        mLayoutManager = new LinearLayoutManager(getContext());
        mCardViewtemDecortion = new CardViewtemDecortion();
        mItemAnimator = new DefaultItemAnimator();

        mFragmentCartRy.setLayoutManager(mLayoutManager);
        mFragmentCartRy.addItemDecoration(mCardViewtemDecortion);
        mFragmentCartRy.setItemAnimator(mItemAnimator);
        mFragmentCartRy.setAdapter(mCartAdapter);

        mCartAdapter.setOnItemCheckListener(new CartAdapter.OnItemCheckListener() {
            @Override
            public void onItemChecked(CartBean cartBean, boolean isChecked) {

                if (isChecked) {
                    sum = (sum + (cartBean.price * cartBean.count));

                    cartBean.isChecked=false;
                    Log.d(TAG, cartBean.count + ">>>>>>>>>>>>>");

                } else {
                    sum = ((sum) - (cartBean.price * cartBean.count));
                    cartBean.isChecked=true;
                    Log.d(TAG, cartBean.count + "uncheck");
                }
                mFragmentCartSum.setText("合计" + mDf.format(sum));

                if (mCartAdapter.positions.size() == 0) {
                    mFragmentCartButtomck.setChecked(true);
                } else if (mCartAdapter.positions.size() > 0||mCartBeans.size()!=mCartAdapter.positions.size()) {
                    isShouldDoUnCheck=false;
                    mFragmentCartButtomck.setChecked(false);

                }else if (mCartAdapter.positions.size()==mCartBeans.size()){
                    isShouldDoUnCheck=true;
                    mFragmentCartButtomck.setChecked(false);
                }
            }
        });

        mCartAdapter.setOnAddAndSubButtonClickListener(new CartAdapter.onAddAndSubButtonClickListener() {
            @Override
            public void onAddButtonClick(CartBean cartBean) {

                if (cartBean.isChecked) {
                    sum = (sum + cartBean.price);
                    mFragmentCartSum.setText("合计" + mDf.format(sum));
                }

            }

            @Override
            public void onSubButtonClick(CartBean cartBean) {

                if (cartBean.isChecked) {
                    sum = (sum - cartBean.price);

                    mFragmentCartSum.setText("合计" + mDf.format(sum));
                }
            }
        });

        mFragmentCartButtomck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mCartAdapter.positions.size()!=0){
                        mCartAdapter.positions.clear();
                        mCartAdapter.notifyDataSetChanged();

                        sum = 0;
                        for (int i = 0; i < mCartBeans.size(); i++) {
                            sum = sum + (mCartBeans.get(i).price * mCartBeans.get(i).count);
                        }
                        mFragmentCartSum.setText("合计" + mDf.format(sum));
                        isShouldDoUnCheck=true;
                    }

                } else {

                    if (isShouldDoUnCheck){
                        for (int i = 0; i < mCartBeans.size(); i++) {
                            mCartAdapter.positions.add(i);
                        }
                        mCartAdapter.notifyDataSetChanged();
                        mFragmentCartSum.setText("合计 ￥0.00");
                        sum = 0;
                        isShouldDoUnCheck=false;
                    }

                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void refData() {


        if (mMyToolBar.getRightButton().getText() == "完成") {
            mMyToolBar.setRightButtonText("编辑");
            mFragmentCartBtSubmit.setVisibility(View.VISIBLE);
            mFragmentCartBtDelete.setVisibility(View.GONE);
            mFragmentCartSum.setVisibility(View.VISIBLE);
        }
        mFragmentCartButtomck.setChecked(true);
        mCartBeans.clear();

        if (mCartProvider.getDataFromLocal() != null) {
            mCartBeans.addAll(mCartProvider.getDataFromLocal());
        }


        mCartAdapter.notifyDataSetChanged();

        sum = 0;
        for (CartBean cartbean : mCartBeans) {
            sum = sum + (cartbean.price * cartbean.count);
        }
        mFragmentCartSum.setText("合计" + mDf.format(sum));

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_cart_bt_delete:

                Iterator<CartBean> iterator = mCartBeans.iterator();
                while (iterator.hasNext()) {
                    CartBean cartBean = iterator.next();

                    if (cartBean.isChecked) {
                        iterator.remove();
                    }
                }

                mCartProvider.mDatas.clear();
                mCartProvider.listToSparse(mCartBeans);
                mCartProvider.commit();

                List<Integer> list = new ArrayList<Integer>();
                for (int i = 0; i < mCartBeans.size(); i++) {
                    list.add(i);
                }
                mCartAdapter.positions.clear();
                mCartAdapter.positions.addAll(list);
                mCartAdapter.notifyDataSetChanged();
                break;
            case R.id.fragment_cart_bt_submit:


                mOrderList =new ArrayList<CartBean>();

                Iterator<CartBean> iteratorder = mCartBeans.iterator();
                while (iteratorder.hasNext()) {
                    CartBean cartBean = iteratorder.next();

                    if (cartBean.isChecked) {
                        iteratorder.remove();
                        mOrderList.add(cartBean);
                    }
                }

                List<Integer> list1 = new ArrayList<Integer>();
                for (int i = 0; i < mCartBeans.size(); i++) {
                    list1.add(i);
                }

                mCartProvider.mDatas.clear();
                mCartProvider.listToSparse(mCartBeans);
                mCartProvider.commit();

                mCartAdapter.positions.clear();
                mCartAdapter.notifyDataSetChanged();
                isShouldDoUnCheck=true;
                mFragmentCartButtomck.setChecked(true);

                Intent intent =new Intent(getActivity(), OrderActivity.class);

                intent.putExtra("orderlist", (Serializable) mOrderList);

                startActivity(intent);


                break;
        }

    }
}
