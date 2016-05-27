package com.example.frank.myshoppingmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.adapter.OrderListAdapter;
import com.example.frank.myshoppingmall.adapter.OrderRecycleViewAdapter;
import com.example.frank.myshoppingmall.bean.AddressBean;
import com.example.frank.myshoppingmall.bean.CartBean;
import com.example.frank.myshoppingmall.bean.PayBean;
import com.example.frank.myshoppingmall.cityhelper.AddressActivity;
import com.example.frank.myshoppingmall.decoration.CardViewtemDecortion;
import com.example.frank.myshoppingmall.decoration.FullyLinearLayoutManager;
import com.example.frank.myshoppingmall.widget.MyToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 创建者     Frank
 * 创建时间   2016/5/25 19:25
 * 描述	      ${提交订单的页面}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class OrderActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.activity_order_toolbar)
    MyToolBar    mActivityOrderToolbar;
    @Bind(R.id.activity_order_username)
    TextView     mActivityOrderUsername;
    @Bind(R.id.order_activity_recycle)
    RecyclerView mOrderActivityRecycle;
    @Bind(R.id.order_activity_list)
    ListView     mOrderActivityList;
    @Bind(R.id.btn_createOrder)
    Button       mBtnCreateOrder;
    @Bind(R.id.order_activity_buttom)
    LinearLayout mOrderActivityButtom;
    @Bind(R.id.activity_order_address)
    ImageButton  mActivityOrderAddress;
    @Bind(R.id.activity_order_address_tv)
    TextView     mActivityOrderAddressTv;


    private String[]       mDescript;
    private String[]       mNames;
    private int[]          mImageids;
    private List<CartBean> mCartBeans;
    private List<PayBean>  mPayBeans;

    private FullyLinearLayoutManager  mLinearLayoutManager;
    private CardViewtemDecortion      mCardViewtemDecortion;
    private RecyclerView.ItemAnimator mMItemAnimator;

    public static final int    ADRESSREQUESTCODE = 1;
    public static final String ADDRESS           = "address";
    public static final String ADDRESSBEAN       = "addressbean";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {

        mLinearLayoutManager = new FullyLinearLayoutManager(this);
        mCardViewtemDecortion = new CardViewtemDecortion();
        mMItemAnimator = new DefaultItemAnimator();

        mImageids = new int[]{R.mipmap.icon_alipay_72, R.mipmap.icon_wechat_72, R.mipmap.icon_bd_72};
        mDescript = new String[]{"支付用支付宝，网银用户使用", "用微信支付，安全便捷", "百度支付安全服务"};
        mNames = new String[]{"支付宝", "微信", "百度钱包"};

        PayBean payBean = null;
        mPayBeans = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            payBean = new PayBean(mImageids[i], mDescript[i], mNames[i]);
            mPayBeans.add(payBean);
        }

        final OrderListAdapter orderListAdapter = new OrderListAdapter(mPayBeans, OrderActivity.this);
        mOrderActivityList.setAdapter(orderListAdapter);
        setListViewHeightBasedOnChildren(mOrderActivityList);

        orderListAdapter.setOnRadioButtonClickListener(new OrderListAdapter.OnRadioButtonClickListener() {
            @Override
            public void onRadioButtonClick(int position) {
                orderListAdapter.setInder(position);
                orderListAdapter.notifyDataSetChanged();
            }
        });


        mOrderActivityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                orderListAdapter.setInder(position);
                orderListAdapter.notifyDataSetChanged();
            }
        });

        if (getIntent() != null) {
            mCartBeans = (List<CartBean>) getIntent().getSerializableExtra("orderlist");

            if (mCartBeans != null && mCartBeans.size() > 0) {

                OrderRecycleViewAdapter orderRecycleViewAdapter = new OrderRecycleViewAdapter(this, mCartBeans);
                mOrderActivityRecycle.setLayoutManager(mLinearLayoutManager);
                mOrderActivityRecycle.addItemDecoration(mCardViewtemDecortion);
                mOrderActivityRecycle.setItemAnimator(mMItemAnimator);
                mOrderActivityRecycle.setAdapter(orderRecycleViewAdapter);
            }
        }

        mActivityOrderToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.this.finish();
            }
        });

        mActivityOrderAddress.setOnClickListener(this);

    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.activity_order_address:

                Intent intent = new Intent(OrderActivity.this, AddressActivity.class);
                startActivityForResult(intent, ADRESSREQUESTCODE);
                break;
            case R.id.btn_createOrder:
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_OK:
                AddressBean addressBean = (AddressBean) data.getSerializableExtra(ADDRESSBEAN);

                mActivityOrderAddressTv.setText(addressBean.getAddress());
                break;
        }


    }
}
