package com.cxf.moudule_common.util;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/****
 * rxjava2 定时器的实现
 */
public class RxTimerUtil {

    private static String Tag = "RxTimerUtil";

    private static Disposable mDisposable;

    /**
     * milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public static void timer(long milliseconds, final IRxNext next) {
        Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                        cancel(null);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //取消订阅
                        cancel(e);
                    }

                    @Override
                    public void onComplete() {
                        //取消订阅
                        cancel(null);
                    }
                });
    }

    private static CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public static void interval(final IRxNext next) {
        mCompositeDisposable.add(Observable.interval(2, 10, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .take(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Object>() {

                    @Override
                    public void onNext(Object o) {
                        next.doNext((Long) o);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));

    }

    public static void clearmCompositeDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }


    /**
     * 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param next
     */
    public static void interval(long milliseconds, final IRxNext next) {
        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        if (next != null) {
                            next.doNext(number);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        cancel(e);

                    }

                    @Override
                    public void onComplete() {
                        cancel(null);

                    }
                });
    }


    /**
     * 取消订阅
     */
    public static void cancel(Throwable e) {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            Log.e(Tag, "====定时器取消======");
        }
    }

    /**
     * 取消订阅
     */
    public static void cancel() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            Log.e(Tag, "====定时器取消======");
        }
    }

    public interface IRxNext {
        void doNext(long number);
    }
}
