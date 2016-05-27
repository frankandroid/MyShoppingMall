package com.example.frank.myshoppingmall;

import android.app.Application;

import com.example.frank.myshoppingmall.bean.User;
import com.example.frank.myshoppingmall.util.PutUserUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 创建者     Frank
 * 创建时间   2016/4/23 9:16
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class MyApplication extends Application {


    private static MyApplication mMyApplication;

    //这里之所以把user保存到application当中，是为了提高性能，不用每次都去sharedprefrence中取。
    private User mUser;

    public static MyApplication getInstance() {
        return mMyApplication;
    }

    public String getToken(){

        return PutUserUtil.getToken(mMyApplication);
    }


    public User getUser(){
        return mUser;
    }

    public User initUser(){
        return PutUserUtil.getUser(mMyApplication);
    }


    public void putUser(User user,String token){
        this.mUser= user;
        PutUserUtil.putUser(mMyApplication,user);
        PutUserUtil.putToken(mMyApplication,token);
    }

    public void clearUser(){
        this.mUser = null;

        PutUserUtil.clearUser(mMyApplication);
        PutUserUtil.clearToken(mMyApplication);
    }



    @Override
    public void onCreate() {
        super.onCreate();


        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);

        ImageLoader.getInstance().init(configuration);

        mMyApplication = this;

        this.mUser=initUser();

    }




}
