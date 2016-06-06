package com.example.frank.myshoppingmall.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.frank.myshoppingmall.Constants;
import com.example.frank.myshoppingmall.MyApplication;
import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.adapter.MyOrderAdapter;
import com.example.frank.myshoppingmall.bean.OrderListBean;
import com.example.frank.myshoppingmall.bean.User;
import com.example.frank.myshoppingmall.decoration.CardViewtemDecortion;
import com.example.frank.myshoppingmall.http.BaseCallBack;
import com.example.frank.myshoppingmall.http.HttpHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者     Frank
 * 创建时间   2016/5/31 15:02
 * 描述	      ${orderframent的封装类}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public abstract class BaseOrderFragment extends BaseFragment {


    private List<OrderListBean> mOrderListBeans;
    private LayoutInflater      mLayoutInflater;
    private RecyclerView        mRecyclerView;

    private CardViewtemDecortion mCardViewtemDecortion;
    private LinearLayoutManager  mLinearLayoutManager;

    private MyOrderAdapter mMyOrderAdapter;


    private Map<String, Object> mParams;

    private HttpHelper mHttpHelper;

    private int index;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLayoutInflater = LayoutInflater.from(mActivity);

        mOrderListBeans = new ArrayList<>();

        mParams = new HashMap<>();

        mLinearLayoutManager = new LinearLayoutManager(mActivity);
        mCardViewtemDecortion = new CardViewtemDecortion();

        mHttpHelper = HttpHelper.getInstance();

        index = SetIndex();
        doRequest(index);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = mLayoutInflater.inflate(R.layout.fragment_baseorder, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_baseorder_ry);
        return rootView;
    }

    private void setUpRecycleView() {

        mMyOrderAdapter = new MyOrderAdapter(mOrderListBeans, mActivity);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(mCardViewtemDecortion);
        mRecyclerView.setAdapter(mMyOrderAdapter);

    }

    /**
     * 这里有点特别重要的就是由于网路请求是异步请求，所以很多时候都是主线程走完了，你数据还没有请求成功。
     * @param index
     * @return
     */
    private List<OrderListBean> doRequest(int index) {

        Long userId = getUserId();

        mParams.put("user_id", userId);
        mParams.put("status", index);

        mHttpHelper.get(Constants.URL.GET_MYORDER, mParams, new BaseCallBack<List<OrderListBean>>() {
            @Override
            public void onFailure(Request request, Exception exception) {

            }

            @Override
            public void onSuccess(Response response, List<OrderListBean> orderListBeans) {
                mOrderListBeans = orderListBeans;
                setUpRecycleView();
            }


            @Override
            public void onError(Response response, int code, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onTokenError(Response response, int code) {

            }
        });

        return mOrderListBeans;
    }


    public abstract int SetIndex();


    private Long getUserId() {

        MyApplication myApplication = (MyApplication) mActivity.getApplication();

        User user = myApplication.getUser();

        if (user != null) {
            return user.getId();
        }

        return Long.valueOf(0);
    }


}
