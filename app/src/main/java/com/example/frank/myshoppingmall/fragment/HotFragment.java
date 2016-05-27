package com.example.frank.myshoppingmall.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.frank.myshoppingmall.Constants;
import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.activity.WareDetailActivity;
import com.example.frank.myshoppingmall.adapter.HotFragmentAdapter;
import com.example.frank.myshoppingmall.bean.HotItemInfoBean;
import com.example.frank.myshoppingmall.decoration.CardViewtemDecortion;
import com.example.frank.myshoppingmall.http.BaseCallBack;
import com.example.frank.myshoppingmall.http.HttpHelper;
import com.example.frank.myshoppingmall.util.ToastUtils;
import com.example.frank.myshoppingmall.widget.MyToolBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class HotFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "HotFragment";
    @Bind(R.id.fragment_hot_recycleview)
    RecyclerView       mFragmentHotRecycleview;
    @Bind(R.id.fragment_hot_swipe)
    SwipeRefreshLayout mFragmentHotSwipe;
    @Bind(R.id.mytoolbar)
    MyToolBar          mMytoolbar;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private int mCurrentPage;
    private int mTotalPage;
    private int mPageSize;


    private View mRootView;

    private HttpHelper mHttpHelper;

    public Map<String, Object> params;

    private List<HotItemInfoBean.HotListEntity> mHotListEntities = new ArrayList<HotItemInfoBean.HotListEntity>();

    private HotFragmentAdapter mHotFragmentAdapter;

    private LinearLayoutManager       mLinearLayoutManager;
    private CardViewtemDecortion      mCardViewtemDecortion;
    private RecyclerView.ItemAnimator mMItemAnimator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "oncreat");

        mRootView = inflater.inflate(R.layout.fragment_hot, null);

        ButterKnife.bind(this, mRootView);

        initView();

        initData();

        return mRootView;

    }

    private void initData() {

        params = new HashMap<String, Object>();
        mCurrentPage = 1;
        mPageSize = 10;
        params.put("curPage", mCurrentPage);
        params.put("pageSize", mPageSize);
        requestData();
    }

    private void initView() {

        mFragmentHotSwipe.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mFragmentHotSwipe.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mFragmentHotSwipe.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources()
                        .getDisplayMetrics()));
        mFragmentHotSwipe.setOnRefreshListener(this);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mCardViewtemDecortion = new CardViewtemDecortion();
        mMItemAnimator = new DefaultItemAnimator();

        mFragmentHotRecycleview.addOnScrollListener(new MyScollerListener());

        AppCompatActivity compatActivity = (AppCompatActivity) getActivity();
        compatActivity.setSupportActionBar(mMytoolbar);
        compatActivity.setTitle("热卖");


    }


    @Override
    public void onDetach() {
        super.onDetach();
        ButterKnife.unbind(mRootView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        //mHotFragmentAdapter = null;
    }

    @Override
    public void onRefresh() {
        mFragmentHotSwipe.setRefreshing(false);
    }

    private void requestData() {

        mHttpHelper = HttpHelper.getInstance();

        mHttpHelper.get(Constants.URL.HOT_LIST_ITEM, params, new BaseCallBack<HotItemInfoBean>() {
            @Override
            public void onFailure(Request request, Exception exception) {
                Log.d(TAG, "onFailure");
            }

            @Override
            public void onSuccess(Response response, HotItemInfoBean hotItemInfoBean) {
                Log.d(TAG, "onSuccess");
                mFragmentHotSwipe.setRefreshing(false);
                mTotalPage = hotItemInfoBean.totalPage;
                mHotListEntities.addAll(hotItemInfoBean.list);
                bindData(hotItemInfoBean.list);
            }


            @Override
            public void onError(Response response, int code, Exception e) {
                Log.d(TAG, "onError" + code + "");
            }

            @Override
            public void onResponse(Response response) {
                Log.d(TAG, "onResponse");
            }

            @Override
            public void onBeforeRequest(Request request) {
                // mFragmentHotSwipe.setRefreshing(true);
            }

            @Override
            public void onTokenError(Response response, int code) {
                Log.d(TAG, "onTokenError");
            }
        });

    }

    private void bindData(List<HotItemInfoBean.HotListEntity> list) {

        // mHotListEntities.addAll(list);
        if (mHotFragmentAdapter == null) {
            mHotFragmentAdapter = new HotFragmentAdapter(getContext(), mHotListEntities);
            mFragmentHotRecycleview.setLayoutManager(mLinearLayoutManager);
            mFragmentHotRecycleview.addItemDecoration(mCardViewtemDecortion);
            mFragmentHotRecycleview.setItemAnimator(mMItemAnimator);
            mFragmentHotRecycleview.setAdapter(mHotFragmentAdapter);
        } else {
            //            mHotFragmentAdapter.setData(list);这行不要了，因为list里面的数据发生改变会自动更新。
            mHotFragmentAdapter.notifyDataSetChanged();
            //            mFragmentHotRecycleview.smoothScrollToPosition(mHotFragmentAdapter.getItemCount() -
            //                    list.size());
            //            loadMoreData(list);

        }

        mHotFragmentAdapter.setOnItemClickListener(new HotFragmentAdapter.onItemClickListener() {
            @Override
            public void onItemClick(HotItemInfoBean.HotListEntity listEntity, int position) {


                ToastUtils.show(getContext(),"dianjidjaoidajsfljdalwk");
                Intent intent = new Intent(getActivity(), WareDetailActivity.class);
                intent.putExtra(Constants.TO_WEBVIEW,listEntity);
                startActivity(intent);

            }
        });

    }

    public void loadMoreData(List<HotItemInfoBean.HotListEntity> list) {

        if (list != null && list.size() > 0) {

            int size = list.size();
            int begin = mHotListEntities.size();
            for (int i = 0; i < size; i++) {
                mHotListEntities.add(list.get(i));
                mHotFragmentAdapter.notifyItemInserted(i + begin);
            }

        }

    }


    public class MyScollerListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int lastVisibleItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
            int itemCount = mHotFragmentAdapter.getItemCount();

            if (lastVisibleItemPosition == itemCount - 1 && dy > 0) {

                mCurrentPage++;
                params.put("curPage", mCurrentPage);
                if (mCurrentPage > mTotalPage) {
                    Toast.makeText(getContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                requestData();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
