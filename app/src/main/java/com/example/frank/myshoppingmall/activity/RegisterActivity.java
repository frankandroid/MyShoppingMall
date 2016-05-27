package com.example.frank.myshoppingmall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.frank.myshoppingmall.R;
import com.example.frank.myshoppingmall.util.ManifestUtil;
import com.example.frank.myshoppingmall.util.ToastUtils;
import com.example.frank.myshoppingmall.widget.MyToolBar;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

/**
 * 创建者     Frank
 * 创建时间   2016/5/21 15:29
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    // 默认使用中国区号
    private static final String DEFAULT_COUNTRY_ID = "42";
    @Bind(R.id.mytoolbar)
    MyToolBar mMytoolbar;
    @Bind(R.id.activity_register_phone)
    EditText  mActivityRegisterPhone;
    @Bind(R.id.activity_register_pwd)
    EditText  mActivityRegisterPwd;
    @Bind(R.id.activity_register_authcode)
    EditText  mActivityRegisterAuthcode;
    @Bind(R.id.activity_register_bt)
    Button    mActivityRegisterBt;
    @Bind(R.id.activity_register_submit)
    Button    mActivityRegisterSubmit;
    private EventHandler mEh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        SMSSDK.initSDK(this, ManifestUtil.getMetaDataValue(this, "mob_sms_appKey"),
                ManifestUtil.getMetaDataValue(this, "mob_sms_appSecrect"));

        //无gui的代码调用界面。
       /* mEh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mActivityRegisterBt.setText("提交成功");
                                ToastUtils.show(RegisterActivity.this,"成功提交验证码");
                            }
                        });

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.show(RegisterActivity.this, "获取验证码成功");
                                mActivityRegisterBt.setText("获取成功");
                            }
                        });

                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                }
            }
        };
        SMSSDK.registerEventHandler(mEh); //注册短信回调*/


        mActivityRegisterBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //无gui接口的调用。
               /* String phone = mActivityRegisterPhone.getText().toString().trim().replaceAll("\\s*", "");
                String[] country = SMSSDK.getCountry(DEFAULT_COUNTRY_ID);
                if (country != null) {

                    String code =  ("+86");
                    checkPhoneNum(phone, code);

                    SMSSDK.getVerificationCode(code,phone);
                }*/

                //打开注册页面
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");

                            //这里是短信验证成功的回调，提交用户信息。
                            //registerUser(country, phone);
                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);

                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    }
                });
                registerPage.show(RegisterActivity.this);

            }
        });

        mActivityRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String authcode = mActivityRegisterAuthcode.getText().toString().trim();


                if (TextUtils.isEmpty(authcode)) {
                    ToastUtils.show(RegisterActivity.this, "验证码不能为空");
                    return;
                }

                String phone = mActivityRegisterPhone.getText().toString().trim().replaceAll("\\s*", "");

                    String code =  ("+86");
                    checkPhoneNum(phone, code);
                    SMSSDK.submitVerificationCode(code,phone,authcode);

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mEh);
    }


    /**
     * 用于检查手机号码是不是符合格式。
     *
     * @param phone
     * @param code
     */
    private void checkPhoneNum(String phone, String code) {
        if (code.startsWith("+")) {
            code = code.substring(1);
        }

        if (TextUtils.isEmpty(phone)) {
            ToastUtils.show(this, "请输入手机号码");
            return;
        }

        if (code == "86") {
            if (phone.length() != 11) {
                ToastUtils.show(this, "手机号码长度不对");
                return;
            }

        }

        String rule = "^1(3|5|7|8|4)\\d{9}";
        Pattern p = Pattern.compile(rule);
        Matcher m = p.matcher(phone);

        if (!m.matches()) {
            ToastUtils.show(this, "您输入的手机号码格式不正确");
            return;
        }

    }


}
