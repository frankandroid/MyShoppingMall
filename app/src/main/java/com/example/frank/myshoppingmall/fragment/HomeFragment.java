package com.example.frank.myshoppingmall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.frank.myshoppingmall.Constants;
import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.activity.WareListActivity;
import com.example.frank.myshoppingmall.adapter.HomeRecycleViewAdapter;
import com.example.frank.myshoppingmall.adapter.HomeViewpagerAdapter;
import com.example.frank.myshoppingmall.bean.Banner;
import com.example.frank.myshoppingmall.bean.HomeItemInfoBean;
import com.example.frank.myshoppingmall.decoration.CardViewtemDecortion;
import com.example.frank.myshoppingmall.http.BaseCallBack;
import com.example.frank.myshoppingmall.http.HttpHelper;
import com.example.frank.myshoppingmall.widget.MyToolBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者     Frank
 * 创建时间   2016/4/18 17:35
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ViewPager
        .OnPageChangeListener {

    @Bind(R.id.mytoolbar)
    MyToolBar          mMytoolbar;
    @Bind(R.id.fragment_home_recyclerview)
    RecyclerView       mFragmentHomeRecyclerview;
    @Bind(R.id.fragment_home_swiperefresh)
    SwipeRefreshLayout mFragmentHomeSwiperefresh;

    public static final String TAG = "HomeFragment";

    private View                   mRootView;
    private List<HomeItemInfoBean> mHomeItemInfoBeans;
    private HttpHelper             mHttpHelper;
    private HomeRecycleViewAdapter mHomeRecycleViewAdapter;
    private ViewPager              mViewPager;
    private HomeViewpagerAdapter   mHomeViewpagerAdapter;

    private LinearLayoutManager       mLinearLayoutManager;
    private CardViewtemDecortion      mCardViewtemDecortion;
    private RecyclerView.ItemAnimator mItemAnimator;
    private Timer                     mMTimer;
    private TimerTask                 mMTimerTask;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bundle bundle = msg.getData();
                    int currentItem = bundle.getInt("currentitem");
                    //Log.d(TAG,  "handler current"+currentItem);
                    mViewPager.setCurrentItem(currentItem);
                    break;
            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_home, null);

        mHomeItemInfoBeans = new ArrayList<>();
        mHttpHelper = HttpHelper.getInstance();

        ButterKnife.bind(this, mRootView);

        initView();
        initData();

        return mRootView;
    }

    private void initData() {

        initBanner();
        initRecycleView();

    }

    private void initRecycleView() {
        mHttpHelper.get(Constants.URL.HOME_ITEM_URL, new BaseCallBack<List<HomeItemInfoBean>>() {
            @Override
            public void onFailure(Request request, Exception exception) {
                Log.d(TAG, "onFailure");
            }

            @Override
            public void onSuccess(Response response, List<HomeItemInfoBean> homeItemInfoBeans) {

                mHomeItemInfoBeans = homeItemInfoBeans;

                if (mHomeRecycleViewAdapter == null) {
                    mHomeRecycleViewAdapter = new HomeRecycleViewAdapter(mHomeItemInfoBeans,
                            getContext());

                } else {
                    mHomeRecycleViewAdapter.notifyDataSetChanged();
                    mFragmentHomeRecyclerview.smoothScrollToPosition(mHomeRecycleViewAdapter.getItemCount() -
                            homeItemInfoBeans.size());
                }
                mFragmentHomeRecyclerview.setLayoutManager(mLinearLayoutManager);
                mFragmentHomeRecyclerview.addItemDecoration(mCardViewtemDecortion);
                mFragmentHomeRecyclerview.setItemAnimator(mItemAnimator);
                mFragmentHomeRecyclerview.setAdapter(mHomeRecycleViewAdapter);

                mFragmentHomeSwiperefresh.setRefreshing(false);
                mHomeRecycleViewAdapter.setOnRecycleItemClickListener(new HomeRecycleViewAdapter
                        .OnRecycleItemClickListener() {
                    @Override
                    public void onItemClick(HomeItemInfoBean homeItemInfoBean, int i, int position) {

                        Intent intent = new Intent(getContext(), WareListActivity.class);
                        switch (i){
                            case 1:
                                intent.putExtra(Constants.WARE_REQUEST_ID,homeItemInfoBean.getCpOne().getId());
                                break;
                            case 2:
                                intent.putExtra(Constants.WARE_REQUEST_ID,homeItemInfoBean.getCpTwo().getId());
                                break;
                            case 3:
                                intent.putExtra(Constants.WARE_REQUEST_ID,homeItemInfoBean.getCpThree().getId());
                                break;
                        }

                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(HomeItemInfoBean homeItemInfoBean, int i, int position) {

                    }
                });

                mFragmentHomeRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {


                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        int firstVisibleItemPosition = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();

                        if (firstVisibleItemPosition ==0) {
                            mFragmentHomeSwiperefresh.setEnabled(true);
                        } else {
                            mFragmentHomeSwiperefresh.setEnabled(false);
                        }

                        int lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
                        Log.d(TAG, lastVisibleItemPosition + "" + "++++++lastVisibleItemPosition");
                        int itemCount = mHomeRecycleViewAdapter.getItemCount();

                        Log.d(TAG, itemCount + "" + "++++++itemCount");
                        if (lastVisibleItemPosition == itemCount - 1 && dy > 0) {
                            Toast.makeText(getContext(), "滑到了底部，可以开始加载新的数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onResponse(Response response) {
                Log.d(TAG, "onResponse");
            }

            @Override
            public void onBeforeRequest(Request request) {
                mFragmentHomeSwiperefresh.setRefreshing(true);
                Log.d(TAG, "onBeforeRequest");
            }

            @Override
            public void onTokenError(Response response, int code) {
                Log.d(TAG, "onTokenError");
            }
        });
    }

    private void initBanner() {

        mHttpHelper.get(Constants.URL.BANNER_URL, new BaseCallBack<List<Banner>>() {
            @Override
            public void onFailure(Request request, Exception exception) {

            }

            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mHomeViewpagerAdapter = new HomeViewpagerAdapter(getContext(), banners);
                mViewPager.setAdapter(mHomeViewpagerAdapter);

                mViewPager.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                mFragmentHomeSwiperefresh.setEnabled(false);
                                stopScoller();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                mFragmentHomeSwiperefresh.setEnabled(false);
                                break;
                            case MotionEvent.ACTION_UP:
                                mFragmentHomeSwiperefresh.setEnabled(true);
                                startScoller();
                                break;
                            case MotionEvent.ACTION_CANCEL:
                                mFragmentHomeSwiperefresh.setEnabled(true);
                                break;
                        }
                        return false;
                    }
                });

                mViewPager.setOnPageChangeListener(HomeFragment.this);
                startScoller();
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

    }


    public void startScoller(){
        if (mMTimer==null){
            mMTimer = new Timer();
            mMTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    int currentItem = mViewPager.getCurrentItem();
                    //Log.d(TAG,"currentItem position"+currentItem);
                    if (currentItem==mViewPager.getAdapter().getCount()-1){
                        currentItem =0;
                    }else{
                        currentItem++;
                    }
                    Message message = Message.obtain();
                    message.what=0;
                    Bundle bundle = new Bundle();
                    bundle.putInt("currentitem", currentItem);
                    message.setData(bundle);
                    mHandler.sendMessage(message);
                }
            }, 2000, 2000);
        }
    }

    public void stopScoller(){
        if (mMTimer!=null){
            mMTimer.cancel();
            mMTimer=null;
        }
    }




    private void initView() {

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mCardViewtemDecortion = new CardViewtemDecortion();
        mItemAnimator = new DefaultItemAnimator();

        mViewPager = (ViewPager) mRootView.findViewById(R.id.fragment_home_viewpager);

        mFragmentHomeSwiperefresh.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mFragmentHomeSwiperefresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mFragmentHomeSwiperefresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources()
                        .getDisplayMetrics()));
        mFragmentHomeSwiperefresh.setOnRefreshListener(this);
    }


    @Override
    public void onDetach() {
        stopScoller();
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopScoller();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        //Log.d(TAG,"onrefresh+++++++++++++++++++++++++++++++");
        mFragmentHomeSwiperefresh.setRefreshing(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



}



