package com.cxf.moudule_common.rxjava;






import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver<T> implements Observer<MyResponse<T>> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(MyResponse<T> tMyResponse) {
        if(tMyResponse.getCode()==0){
            T data = tMyResponse.getData();
            getDatas(data);
        }else{
            //处理返回错误代码


        }

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }


    protected abstract void getDatas(T t);
}
