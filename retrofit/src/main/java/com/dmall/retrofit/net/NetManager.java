package com.dmall.retrofit.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 服务器是用json-server搭建的
 * Created by eyetech on 16/5/20.
 * mail:354734713@qq.com
 */
public class NetManager {

    private static final String BASE_URL = "http://localhost:3000";
    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit mRetrofit;
    private ApiService mApiService;

    private static class NetManagerHolder {
        private static NetManager instance = new NetManager();
    }

    private NetManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static NetManager getInstance() {
        return NetManagerHolder.instance;
    }

    public synchronized ApiService getApiService() {
        if (mApiService == null) {
            mApiService = mRetrofit.create(ApiService.class);
        }
        return mApiService;
    }
}
