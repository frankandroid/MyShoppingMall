package com.example.frank.myshoppingmall.http;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者     Frank
 * 创建时间   2016/4/17 16:27
 * 描述	      ${TODO}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public abstract class BaseCallBack<T> {

    public Type mType;

    public BaseCallBack(){
        mType = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }


    public abstract void onFailure(Request request,Exception exception);

    public abstract void onSuccess(Response response,T t);

    public abstract void onError(Response response,int code ,Exception e);

    public abstract void onResponse(Response response);

    public abstract void onBeforeRequest(Request request);

    public abstract void onTokenError(Response response,int code);
}
