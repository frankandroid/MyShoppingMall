package com.example.frank.myshoppingmall.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.example.frank.myshoppingmall.Constants;
import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.bean.HotItemInfoBean;
import com.example.frank.myshoppingmall.util.CartProvider;
import com.example.frank.myshoppingmall.util.ToastUtils;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 创建者     Frank
 * 创建时间   2016/5/19 18:14
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class WareDetailActivity extends AppCompatActivity {

    public static final String TAG = "WareDetailActivity";

    private WebView mWebView;
    private Toolbar mToolbar;
    private Button mRightButton;
    private HotItemInfoBean.HotListEntity mHotListEntity;

    private CartProvider mCartProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_waredetail);

        mHotListEntity= (HotItemInfoBean.HotListEntity) getIntent().getSerializableExtra(Constants.TO_WEBVIEW);

        Log.d(TAG,mHotListEntity.name);
        initView();
        initData();


    }

    private void initData() {



    }

    private void initView() {

        mCartProvider = new CartProvider(this);

        mToolbar = (Toolbar) findViewById(R.id.ware_detail_toolbar);
        mToolbar.setTitle("商品详情");

        mRightButton = (Button) findViewById(R.id.mytoobar_bt);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WareDetailActivity.this.finish();
            }
        });

        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.initSDK(WareDetailActivity.this);
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();

                // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                oks.setTitle("这个是分享的title");
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                oks.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                oks.setText("我是分享文本");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://sharesdk.cn");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("我是测试评论文本");
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite(getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl("http://sharesdk.cn");

                // 启动分享GUI
                oks.show(WareDetailActivity.this);
            }
        });



        mWebView = (WebView) findViewById(R.id.ware_detail_webview);
        mWebView.loadUrl(Constants.URL.WARES_DETAIL);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        mWebView.addJavascriptInterface(new JavaScriptHelper(this), "appInterface");

    }


    public class JavaScriptHelper {

        private Context mContext;

        public JavaScriptHelper(Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public  void showDetail(){


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    mWebView.loadUrl("javascript:showDetail("+mHotListEntity.id+")");

                }
            });
        }

        @JavascriptInterface
        public void buy(long id){

            mCartProvider.put(mCartProvider.convert(mHotListEntity));
            ToastUtils.show(mContext, "已添加到购物车");

        }




    }



}
