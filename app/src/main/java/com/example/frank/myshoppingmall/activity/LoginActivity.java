package com.example.frank.myshoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frank.myshoppingmall.Constants;
import com.example.frank.myshoppingmall.MyApplication;
import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.bean.LoginRespMsg;
import com.example.frank.myshoppingmall.bean.User;
import com.example.frank.myshoppingmall.http.BaseCallBack;
import com.example.frank.myshoppingmall.http.HttpHelper;
import com.example.frank.myshoppingmall.util.DESUtil;
import com.example.frank.myshoppingmall.util.ToastUtils;
import com.example.frank.myshoppingmall.widget.MyToolBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 创建者     Frank
 * 创建时间   2016/5/18 10:03
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class LoginActivity extends Activity implements View.OnClickListener {


    private static final String TAG = "LoginActivity";
    @Bind(R.id.username)
    EditText        mUsername;
    @Bind(R.id.usernameWrapper)
    TextInputLayout mUsernameWrapper;
    @Bind(R.id.password)
    EditText        mPassword;
    @Bind(R.id.passwordWrapper)
    TextInputLayout mPasswordWrapper;
    @Bind(R.id.btn)
    Button          mBtn;
    @Bind(R.id.activity_login_scrollview)
    ScrollView      mActivityLoginScrollview;
    @Bind(R.id.mytoolbar)
    MyToolBar       mMytoolbar;
    @Bind(R.id.login_register)
    TextView        mLoginRegister;
    @Bind(R.id.login_forgetpassword)
    TextView        mLoginForgetpassword;
    private String mUsername1;
    private String mPassword1;

    private Map<String, Object> userMap = new HashMap<>();

    private HttpHelper    mHttpHelper;
    private MyApplication mMyApplication;
    private ScrollView    mScrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        initView();
        initData();

    }

    private void initData() {


    }

    private void initView() {

        mBtn.setOnClickListener(this);
        mHttpHelper = HttpHelper.getInstance();

        mMyApplication = MyApplication.getInstance();

        mPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                changeScrollView();

                return false;
            }
        });


        mLoginForgetpassword.setOnClickListener(this);
        mLoginRegister.setOnClickListener(this);

    }

    private void changeScrollView() {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mActivityLoginScrollview.scrollTo(0, mActivityLoginScrollview.getMeasuredHeight());
            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()) {
            case R.id.btn:

                mUsername1 = mUsername.getText().toString();
                mPassword1 = mPassword.getText().toString();

                if (TextUtils.isEmpty(mUsername1)) {
                    ToastUtils.show(this, "用户名不能为空",Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(mPassword1)) {
                    ToastUtils.show(this, "密码不能为空",Toast.LENGTH_SHORT);
                    return;
                }

                userMap.put("phone", mUsername1);
                userMap.put("password", DESUtil.encode(Constants.DES_KEY, mPassword1));
                doRequest(userMap);
                break;
            case R.id.login_register:

                intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_forgetpassword:
                intent = new Intent(LoginActivity.this,ForgetActivity.class);
                startActivity(intent);
                break;

        }

    }

    /**
     * 拿到用户输入的用户名和密码去服务器校验。
     * @param map
     */
    private void doRequest(Map<String,Object> map) {
        mHttpHelper.post(Constants.URL.LOGIN, map, new BaseCallBack<LoginRespMsg<User>>() {
            @Override
            public void onFailure(Request request, Exception exception) {

            }

            @Override
            public void onSuccess(Response response, LoginRespMsg<User> userLoginRespMsg) {

                if (userLoginRespMsg.getMessage().equals("success")) {
                    mMyApplication.putUser(userLoginRespMsg.getData(), userLoginRespMsg.getToken());
                    setResult(RESULT_OK);
                    finish();
                }else{
                    ToastUtils.show(LoginActivity.this,"用户名或者密码错误", Toast.LENGTH_SHORT);
                }

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
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
