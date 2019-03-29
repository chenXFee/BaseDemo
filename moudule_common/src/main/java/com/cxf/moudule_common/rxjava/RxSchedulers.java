package com.cxf.moudule_common.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulers {


        public static <T> ObservableTransformer<T,T> compose(){
            return new ObservableTransformer<T, T>() {
                @Override
                public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                    return upstream
                            .subscribeOn(Schedulers.io())
                            .doOnSubscribe(new Consumer<Disposable>() {
                                @Override
                                public void accept(@NonNull Disposable disposable) throws Exception {
                                    //订阅开始前调用，可以在此做一些网络获取数据环境的判断
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread());
                }
            };
        }

}
