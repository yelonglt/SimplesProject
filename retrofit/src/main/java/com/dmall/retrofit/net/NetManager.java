package com.dmall.retrofit.net;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 服务器是用json-server搭建的
 * Created by eyetech on 16/5/20.
 * mail:354734713@qq.com
 */
public class NetManager {

    private static ApiService sService;
    private static OkHttpClient sOkHttpClient = new OkHttpClient();
    private static Converter.Factory sGsonFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory sRxJavaFactory = RxJavaCallAdapterFactory.create();

    public static ApiService getApiService() {
        if (sService == null) {
            synchronized (ApiService.class) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:3000")
                        .client(sOkHttpClient).addConverterFactory(sGsonFactory)
                        .addCallAdapterFactory(sRxJavaFactory).build();
                sService = retrofit.create(ApiService.class);
            }
        }
        return sService;
    }
}
