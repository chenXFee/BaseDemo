package com.cxf.moudule_common.rxjava;


import android.view.View;


import com.jakewharton.rxbinding3.view.RxView;


import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * 类作用描述:rxview各种绑定的公共代码
 * 作者: chenjf(316031139@qq.com)
 * 创建时间：2018/4/9 14:15
 * 创建时间: 2018/4/9 14:15
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class RxBindUtils {


    /****
     * 点击事件，防止重复点击
     * @param view
     * @param lifecycleOwner
     * @return
     */
//    public static void rxViewClicks(final View view, LifecycleOwner lifecycleOwner, final View.OnClickListener onClickListener) {
//        ////设置5s内丢弃新点击事件，按钮去抖动
//        RxView.clicks(view)
//                .throttleFirst(3, TimeUnit.SECONDS)
//                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)))
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//                        onClickListener.onClick(view);
//                    }
//                });
//
//    }

    public static void rxViewClicks(final View view, final View.OnClickListener onClickListener) {
        ////设置5s内丢弃新点击事件，按钮去抖动
        RxView.clicks(view)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        onClickListener.onClick(view);
                    }
                });

    }


    /****
     * 点击事件，防止重复点击
     * @param view
     * @param lifecycleOwner
     * @return
     */
//    public static void rxViewNoPauseClicks(final View view, LifecycleOwner lifecycleOwner, final View.OnClickListener onClickListener) {
//        ////设置5s内丢弃新点击事件，按钮去抖动
//        RxView.clicks(view)
//                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)))
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//                        onClickListener.onClick(view);
//                    }
//                });
//
//    }



}
