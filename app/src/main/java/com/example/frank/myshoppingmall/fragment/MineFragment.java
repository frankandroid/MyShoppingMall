package com.example.frank.myshoppingmall.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.frank.myshoppingmall.MyApplication;
import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.activity.CollectActivity;
import com.example.frank.myshoppingmall.activity.LocationActivity;
import com.example.frank.myshoppingmall.activity.LoginActivity;
import com.example.frank.myshoppingmall.activity.MyOrderActivity;
import com.example.frank.myshoppingmall.adapter.MineFragmentAdapter;
import com.example.frank.myshoppingmall.bean.MineItemInfoBean;
import com.example.frank.myshoppingmall.bean.User;
import com.example.frank.myshoppingmall.decoration.DividerItemDecoration;
import com.example.frank.myshoppingmall.http.ImageLoaderUtil;
import com.example.frank.myshoppingmall.util.PutUserUtil;
import com.example.frank.myshoppingmall.util.ToastUtils;
import com.example.frank.myshoppingmall.widget.FrankCircleImageView;
import com.example.frank.myshoppingmall.widget.MyToolBar;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建者     Frank
 * 创建时间   2016/4/18 17:35
 * 描述	      ${我的页面}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    private static final int    LOGIN_REQUEST_CODE = 0;
    private static final String TAG                = "MineFragment";
    @Bind(R.id.fragment_mine_toolbar)
    MyToolBar            mFragmentMineToolbar;
    @Bind(R.id.fragment_mine_iv)
    FrankCircleImageView mFragmentMineIv;
    @Bind(R.id.fragment_mine_ry)
    RecyclerView         mFragmentMineRy;
    @Bind(R.id.fragment_mine_bt_clear)
    Button               mFragmentMineBtClear;
    @Bind(R.id.fragment_mine_tv)
    TextView             mFragmentMineTv;

    private View mRootView;

    private MyApplication mMyApplication;

    private ArrayList<MineItemInfoBean> mMineItemInfoBeans;

    private LinearLayoutManager       mLinearLayoutManager;
    private DividerItemDecoration     mDividerItemDecoration;
    private RecyclerView.ItemAnimator mMItemAnimator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_mine, null);

        ButterKnife.bind(this, mRootView);

        mMyApplication = MyApplication.getInstance();

        initView();


        return mRootView;
    }

    private void initView() {

        initRecycleViewData();
        User user = mMyApplication.getUser();
        if (user != null) {
            ImageLoaderUtil.displayImage(user.getLogo_url(), mFragmentMineIv);
            mFragmentMineTv.setText(user.getUsername());
        }

        mFragmentMineToolbar.setTitle("我的");

        mFragmentMineIv.setOnClickListener(this);
        mFragmentMineBtClear.setOnClickListener(this);
        mFragmentMineTv.setOnClickListener(this);
    }

    /**
     * 对recycleview的数据进行初始化
     */
    private void initRecycleViewData() {

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mDividerItemDecoration = new DividerItemDecoration(getContext(), LinearLayout.VERTICAL);

        int[] images = {R.mipmap.icon_list_o, R.mipmap.icon_favorite, R.mipmap.icon_location};
        String[] itemnames = {"我的订单", "我的收藏", "收货地址"};

        mMineItemInfoBeans = new ArrayList<MineItemInfoBean>();
        MineItemInfoBean mineItemInfoBean = null;
        for (int i = 0; i < images.length; i++) {

            mineItemInfoBean = new MineItemInfoBean(images[i], itemnames[i]);
            mMineItemInfoBeans.add(mineItemInfoBean);
        }

        MineFragmentAdapter mineFragmentAdapter = new MineFragmentAdapter(getContext(), mMineItemInfoBeans);

        mFragmentMineRy.setLayoutManager(mLinearLayoutManager);
        mFragmentMineRy.addItemDecoration(mDividerItemDecoration);
        mFragmentMineRy.setAdapter(mineFragmentAdapter);

        mineFragmentAdapter.setOnItemClickLitener(new MineFragmentAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(MineItemInfoBean mineItemInfoBean, int position) {

                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(getContext(), MyOrderActivity.class);

                        break;
                    case 1:
                        intent = new Intent(getContext(), CollectActivity.class);
                        break;
                    case 2:
                        intent = new Intent(getContext(), LocationActivity.class);
                        break;

                    default:
                        break;
                }

                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fragment_mine_iv:
            case R.id.fragment_mine_tv:
                if (mMyApplication.getToken() != null && !TextUtils.isEmpty(mMyApplication.getToken())) {
                    ToastUtils.show(getContext(), "你已经登录成功");
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
                }
                break;

            case R.id.fragment_mine_bt_clear:
                mMyApplication.clearUser();
                User user = PutUserUtil.getUser(getActivity());

                mFragmentMineIv.setImageResource(R.mipmap.ic_launcher);
                mFragmentMineTv.setText("请登录");

                mFragmentMineBtClear.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case LoginActivity.RESULT_OK:

                User user = PutUserUtil.getUser(getActivity());
                if (user != null) {
                    ImageLoaderUtil.displayImage(user.getLogo_url(), mFragmentMineIv);
                    mFragmentMineTv.setText(user.getUsername());
                    mFragmentMineBtClear.setVisibility(View.VISIBLE);
                }

                break;
        }
    }
}
