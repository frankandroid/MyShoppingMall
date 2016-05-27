package com.example.frank.myshoppingmall.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.example.frank.myshoppingmall.MyApplication;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 创建者     Frank
 * 创建时间   2016/4/17 15:34
 * 描述	      ${对网络请求的封装}
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */
public class HttpHelper {

    public static final String TAG="HttpHelper";

    private OkHttpClient mOkHttpClient;
    private Gson         mGson;
    private Handler      mHandler;

    private static HttpHelper mInstance;

    public static final int TOKEN_MISSING = 401;//丢失
    public static final int TOKEN_ERROR   = 402;//错误
    public static final int TOKEN_EXPIRE  = 403;//过期

    static {
        mInstance = new HttpHelper();
    }

    private final MyApplication mMyApplication;

    private HttpHelper() {

        mOkHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit
                .SECONDS).readTimeout(10, TimeUnit.SECONDS).build();
        mGson = new Gson();
        mHandler = new Handler(Looper.getMainLooper());
        mMyApplication = MyApplication.getInstance();
    }

    public static HttpHelper getInstance() {
        return mInstance;
    }


    public void get(String url, Map<String, Object> params, BaseCallBack baseCallBack) {

        Request request = buildRequest(url, params, HttpMethodType.GET);

        doRequest(request, baseCallBack);

    }


    public void get(String url, BaseCallBack baseCallBack) {
        get(url, null, baseCallBack);
    }

    public void post(String url, Map<String, Object> params, BaseCallBack baseCallBack) {

        Request request = buildRequest(url, params, HttpMethodType.POST);
        doRequest(request, baseCallBack);
    }


    public void doRequest(final Request request, final BaseCallBack baseCallBack) {

        baseCallBack.onBeforeRequest(request);

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBackOnFailure(request, baseCallBack, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //这个操作主要是方便于网络请求成功的时候对UI进行操作。
                callBackOnResponse(response, baseCallBack);

                if (response.isSuccessful()) {

                    String resultString = response.body().string();

                    if (baseCallBack.mType == String.class) {
                        callBackOnSuccess(baseCallBack, response, resultString);
                    } else {
                        try {
                            //Log.d(TAG,"走到了onsuccess");
                            Object obj = mGson.fromJson(resultString, baseCallBack.mType);
                            callBackOnSuccess(baseCallBack, response, obj);
                        } catch (JsonSyntaxException e) {
                            callBackOnError(baseCallBack, response, e);
                        }
                    }

                } else if (response.code() == TOKEN_ERROR || response.code() == TOKEN_MISSING || response.code() ==
                        TOKEN_EXPIRE) {
                            callBackOnTokenError(baseCallBack,response);
                } else {
                    callBackOnError(baseCallBack, response, null);
                }
            }
        });

    }


    public void callBackOnTokenError(final BaseCallBack baseCallBack, final Response response){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
              baseCallBack.onTokenError(response,response.code());
            }
        });
    }


    private void callBackOnFailure(final Request request, final BaseCallBack baseCallBack, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                baseCallBack.onFailure(request, e);
            }
        });
    }

    private void callBackOnSuccess(final BaseCallBack baseCallBack, final Response response, final Object object) {


        mHandler.post(new Runnable() {
            @Override
            public void run() {
                baseCallBack.onSuccess(response, object);
            }
        });
    }


    private void callBackOnError(final BaseCallBack baseCallBack, final Response response, final Exception e) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                baseCallBack.onError(response, response.code(), e);
            }
        });

    }


    private void callBackOnResponse(final Response response, final BaseCallBack baseCallBack) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                baseCallBack.onResponse(response);
            }
        });
    }


    public Request buildRequest(String url, Map<String, Object> params, HttpMethodType httpMethodType) {

        Request.Builder request = new Request.Builder();

        if (httpMethodType == HttpMethodType.GET) {

            url = buildUrlParams(url, params);

            //Log.d(HotFragment.TAG,url);

            request.url(url);

            request.get();


        } else if (httpMethodType == httpMethodType.POST) {

            RequestBody requestBody = buildeFormData(params);

            request.url(url);
            request.post(requestBody);

        }

        return request.build();
    }


    public String buildUrlParams(String url, Map<String, Object> params) {

        if (params != null) {
            StringBuilder sb = new StringBuilder();

            for (Map.Entry<String, Object> entry : params.entrySet()) {

                sb.append(entry.getKey() + "=" + (entry.getValue() == null ? "" : entry.getValue().toString()));
                sb.append("&");
            }

            String token = mMyApplication.getToken();

            if (!TextUtils.isEmpty(token)){
                sb.append("token="+token);
            }


            String s = sb.toString();

            if (s.endsWith("&")) {
                s = s.substring(0, s.length() - 1);
            }

            if (url.indexOf("?") > 0) {
                url = url + "&" + s;
            } else {
                url = url + "?" + s;
            }

        }
        return url;
    }

    public RequestBody buildeFormData(Map<String, Object> params) {


        FormBody.Builder builder = new FormBody.Builder();

        if (params != null) {

            for (Map.Entry<String, Object> entry : params.entrySet()) {

                builder.add(entry.getKey(), (entry.getValue() == null ? "" : entry.getValue().toString()));
            }

        }
        String token = mMyApplication.getToken();

        if (!TextUtils.isEmpty(token)){
           builder.add("token",token);
        }

        return builder.build();

    }

    enum HttpMethodType {
        GET,
        POST,
    }


}
