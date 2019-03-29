package com.cxf.moudule_common.ARoute;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;


public class ARouterUtils {


    public static void goRouter(String path, Context context) {
        ARouter.getInstance().build(path).navigation(context);
    }

    public static void goRouter(String path,String key,String value) {

        ARouter.getInstance().build(path)
                .withString(key,value).navigation();
    }
    public static void goRouter(String path,String key,String value,Bundle bundle) {

        ARouter.getInstance().build(path)
                .withString(key,value)
                .with(bundle).navigation();
    }

    public static void goRouter(String path, Context context, Bundle bundle) {
        ARouter.getInstance()
                .build(path)
                .with(bundle)
                .navigation();
    }

    /***
     *
     * @param path
     * @param context
     * @param data
     * @param reuestCode
     */
    public static void goRouterStartActivityForResult(String path, Activity context, String  data, int reuestCode) {
        //使用activityResult，相当于 startActivityForResult
        ARouter.getInstance()
                .build(path)
                .withString("data",data)
                .navigation(context, reuestCode);
    }
}
