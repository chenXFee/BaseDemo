package com.cxf.moudule_common.rxjava;

import com.cxf.moudule_common.Retrofit.RetrofitService.MyResult;
import com.cxf.moudule_common.Retrofit.RetrofitUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class RxJavaUtil{

    public static void netWorks(Observable service, final callBackRetrofitWorks callBackRetrofitWorks){
        try {
            service.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object o) throws Exception {
                            callBackRetrofitWorks.callBackResponse(o);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            callBackRetrofitWorks.callBackResponseError("50001",throwable.getMessage());
                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public interface callBackRetrofitWorks<T>{

        void callBackResponse(T response);

        void callBackResponseError(Object errorCode, Object errorMessage);

    }



}
