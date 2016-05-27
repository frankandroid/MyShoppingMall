package com.example.frank.myshoppingmall.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.frank.myshoppingmall.Constants;
import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.adapter.CategoryContentAdapter;
import com.example.frank.myshoppingmall.adapter.LeftMenuAdapter;
import com.example.frank.myshoppingmall.bean.Banner;
import com.example.frank.myshoppingmall.bean.CategoryContentBean;
import com.example.frank.myshoppingmall.bean.LeftMenuBean;
import com.example.frank.myshoppingmall.decoration.CardViewtemDecortion;
import com.example.frank.myshoppingmall.decoration.DividerItemDecoration;
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
 * 描述	      ${分类的fragment}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 * <p/>
 * url :http://112.124.22.238:8081/course_api/wares/list?categoryId=0&curPage=1&pageSize=10
 */
public class CategoryFragment extends Fragment implements LeftMenuAdapter.OnItemClickLitener, CategoryContentAdapter
        .OnContentItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "CategoryFragment";
    @Bind(R.id.mytoolbar)
    MyToolBar          mMytoolbar;
    @Bind(R.id.fragment_category_ry_index)
    RecyclerView       mFragmentCategoryRyIndex;
    @Bind(R.id.fragment_category_slider)
    SliderLayout       mFragmentCategorySlider;
    @Bind(R.id.fragment_category_ry_content)
    RecyclerView       mFragmentCategoryRyContent;
    @Bind(R.id.fragment_category_content)
    LinearLayout       mFragmentCategoryContent;
    @Bind(R.id.fragment_category_sw)
    SwipeRefreshLayout mFragmentCategorySw;


    private View                       mRootView;
    private HttpHelper                 mHttpHelper;
    private List<Banner>               mBanners;
    private List<LeftMenuBean>         mLeftMenuBeans;
    private AppCompatActivity          mCompatActivity;
    private RecyclerView.LayoutManager mLayoutManager;
    private GridLayoutManager          mGridLayoutManager;
    private DividerItemDecoration      mDividerItemDecoration;
    private CardViewtemDecortion       mCardViewtemDecortion;
    private RecyclerView.ItemAnimator  mItemAnimator;

    private LeftMenuAdapter        mLeftMenuAdapter;
    private CategoryContentAdapter mCategoryContentAdapter;

    private Map<String, Object>                   mUrlMap;
    private List<CategoryContentBean.ContentItem> mContentItems;

    private int categoryId;
    private int curPage;
    private int pageSize;
    private int totalPage;

    private MyScrollListener mMyScrollListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCompatActivity = (AppCompatActivity) getActivity();

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_category, null);
        ButterKnife.bind(this, mRootView);

        initView();
        initData();

        return mRootView;
    }

    private void initView() {

        mHttpHelper = HttpHelper.getInstance();

        mCompatActivity.setSupportActionBar(mMytoolbar);
        mMytoolbar.setTitle("分类");

        mDividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        mItemAnimator = new DefaultItemAnimator();
        mCardViewtemDecortion = new CardViewtemDecortion();

        mContentItems = new ArrayList<>();
        mUrlMap = new HashMap<>();
        mFragmentCategorySw.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mFragmentCategorySw.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mFragmentCategorySw.setOnRefreshListener(this);

        mMyScrollListener =new MyScrollListener();
        mFragmentCategoryRyContent.addOnScrollListener(mMyScrollListener);


        initSlider();
    }

    /**
     * 广告轮播图
     */
    private void initSlider() {

        //url = "http://112.124.22.238:8081/course_api/banner/query?type=1"
        mHttpHelper.get(Constants.URL.BANNER_URL, new BaseCallBack<List<Banner>>() {
            @Override
            public void onFailure(Request request, Exception exception) {

            }

            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mBanners = banners;
                bandSlideData();

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

    private void bandSlideData() {
        if (mBanners != null) {

            for (Banner banner : mBanners) {
                DefaultSliderView sliderView = new DefaultSliderView(this.getActivity());
                sliderView.image(banner.getImgUrl());
                sliderView.description(banner.getName());
                sliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mFragmentCategorySlider.addSlider(sliderView);
            }
        }

        mFragmentCategorySlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        mFragmentCategorySlider.setCustomAnimation(new DescriptionAnimation());
        mFragmentCategorySlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mFragmentCategorySlider.setDuration(3000);
    }

    private void initData() {

        categoryId = 1;
        curPage = 1;
        pageSize = 10;

        initLeftMenu();
        initContent();
    }

    private void initContent() {

        mUrlMap.put("categoryId", categoryId);
        mUrlMap.put("curPage", curPage);
        mUrlMap.put("pageSize", pageSize);

        //http://112.124.22.238:8081/course_api/wares/list?categoryId=1&curPage=1&pageSize=10
        String url = Constants.URL.CATEGORY_CONTENT_ITEM;

        mHttpHelper.get(url, mUrlMap, new BaseCallBack<CategoryContentBean>() {
            @Override
            public void onFailure(Request request, Exception exception) {

            }

            @Override
            public void onSuccess(Response response, CategoryContentBean categoryContentBean) {

                totalPage = categoryContentBean.getTotalPage();

                mContentItems.addAll(categoryContentBean.getList());

                if (mCategoryContentAdapter == null) {
                    mCategoryContentAdapter = new CategoryContentAdapter(mContentItems,
                            getContext());
                    mGridLayoutManager = new GridLayoutManager(getContext(), 2);
                    mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            if (position == mGridLayoutManager.getItemCount() - 1) {
                                return mGridLayoutManager.getSpanCount();
                            } else {
                                return 1;
                            }
                        }
                    });
                    mFragmentCategoryRyContent.setLayoutManager(mGridLayoutManager);
                    mFragmentCategoryRyContent.addItemDecoration(mCardViewtemDecortion);
                    mFragmentCategoryRyContent.setItemAnimator(mItemAnimator);
                    mFragmentCategoryRyContent.setAdapter(mCategoryContentAdapter);
                } else {
                    mCategoryContentAdapter.notifyDataSetChanged();
                }
                mCategoryContentAdapter.setOnContentItemClickListener(CategoryFragment.this);
                mFragmentCategorySw.setRefreshing(false);
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

    private void initLeftMenu() {

        String url = Constants.URL.CATEGORY_LEFT_ITEM;
        mHttpHelper.get(url, new BaseCallBack<List<LeftMenuBean>>() {
            @Override
            public void onFailure(Request request, Exception exception) {

            }

            @Override
            public void onSuccess(Response response, List<LeftMenuBean> leftMenuBeans) {
                mLeftMenuBeans = leftMenuBeans;


                if (mLeftMenuAdapter == null) {
                    mLeftMenuAdapter = new LeftMenuAdapter(mLeftMenuBeans,
                            getContext());
                    mLayoutManager = new LinearLayoutManager(getContext());
                    mFragmentCategoryRyIndex.setLayoutManager(mLayoutManager);
                    mFragmentCategoryRyIndex.addItemDecoration(mDividerItemDecoration);
                    mFragmentCategoryRyIndex.setItemAnimator(mItemAnimator);
                    mFragmentCategoryRyIndex.setAdapter(mLeftMenuAdapter);

                } else {
                    mLeftMenuAdapter.notifyDataSetChanged();

                }

                mLeftMenuAdapter.setOnItemClickLitener(CategoryFragment.this);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    /**
     * 这个是对左侧菜单进行监听
     */
    public void onItemClick(View view, int position, LeftMenuBean leftMenuBean) {

        categoryId = leftMenuBean.getId();
        curPage = 1;
        mContentItems.clear();
        initContent();
        mCategoryContentAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(), leftMenuBean.getId() + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onContentItemClick(View view, int position, CategoryContentBean.ContentItem contentItem) {
        Toast.makeText(getContext(), contentItem.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        curPage++;
        if (curPage<=totalPage){
            initContent();
        }else{
            ToastUtils.show(getContext(),"没有更多数据");
            mFragmentCategorySw.setRefreshing(false);
        }
    }

    public class MyScrollListener extends RecyclerView.OnScrollListener{
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int lastVisibleItemPosition = mGridLayoutManager.findLastCompletelyVisibleItemPosition();
            int itemCount = mCategoryContentAdapter.getItemCount();

            if (lastVisibleItemPosition == itemCount - 1 && dy > 0) {

                curPage++;
                mUrlMap.put("curPage", curPage);
                if (curPage > totalPage) {
                   // Toast.makeText(getContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
                    mCategoryContentAdapter.mLoadMoreViewHodler.setdata(CategoryContentAdapter.STATE_END);
                    return;
                }else{
                    mCategoryContentAdapter.mLoadMoreViewHodler.setdata(CategoryContentAdapter.STATE_LOADING);
                    initContent();
                }
            }
        }
    }

}
