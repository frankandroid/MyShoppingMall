package com.example.frank.myshoppingmall.util;

import android.content.Context;
import android.text.TextUtils;

import com.example.frank.myshoppingmall.Constants;
import com.example.frank.myshoppingmall.bean.User;

/**
 * 创建者     Frank
 * 创建时间   2016/5/18 15:05
 * 描述	      ${这个是用于把user数据存入到本地的工具类，用于辅助登录功能}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class PutUserUtil {


    public static void putUser(Context context,User user){

        String userjson = JSONUtil.toJSON(user);

        PreferencesUtils.putString(context, Constants.USER_JSON,userjson);
    }

    public static User getUser(Context context){

        String userjson = PreferencesUtils.getString(context,Constants.USER_JSON, "");

        if (!TextUtils.isEmpty(userjson)){
            return  JSONUtil.fromJson(userjson,User.class);
        }
       return null;
    }

    public static void putToken(Context context,String token){

        PreferencesUtils.putString(context, Constants.TOKEN,token);
    }

    public static  String getToken(Context context){

        return  PreferencesUtils.getString( context,Constants.TOKEN);
    }


    public static void clearUser(Context context){
        PreferencesUtils.putString(context, Constants.USER_JSON,"");

    }

    public static void clearToken(Context context){
        PreferencesUtils.putString(context, Constants.TOKEN,"");
    }


}
