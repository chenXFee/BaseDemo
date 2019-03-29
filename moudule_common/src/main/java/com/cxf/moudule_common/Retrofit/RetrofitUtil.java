package com.cxf.moudule_common.Retrofit;

import com.cxf.module_resource.IP_Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {


    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;

    private Retrofit retrofit;

    public RetrofitUtil() {
        this.init();
    }

    public static class SingletonHolder{
        private static final RetrofitUtil INSTENCE = new RetrofitUtil();
    }

    public static RetrofitUtil getInstance(){
        return  SingletonHolder.INSTENCE;
    }



    public void init(){

        //OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//读操作超时时间
        retrofit = new Retrofit.Builder()
                .baseUrl(IP_Config.ip)
                .client(builder.build())
                //  添加RxJava引用
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //  Gson解析
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public <T> T create(Class<T> service){
        return retrofit.create(service);
    }

}
