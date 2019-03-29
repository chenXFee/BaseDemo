package com.cxf.moudule_common.Retrofit.RetrofitService;

import com.cxf.module_resource.IP_Config;
import com.cxf.moudule_common.rxjava.MyResponse;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRetrofitService {

    @POST
    Observable<Object> getConnect(@Query("userCode") String usercode, @Query("userPwd") String userPwd);

    @GET("top250")
    Observable<Object> getMovie(@Query("start") int start, @Query("count") int count);

}
