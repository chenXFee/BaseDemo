package com.cxf.moudule_common.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @Description:Toast统一管理类
 * @author: cjf
 * @date: 2015-7-13 上午9:23:15
 */
public final class ToastUtil {


    private ToastUtil() {
        throw new UnsupportedOperationException("不能被实例化");
    }


    public static  boolean isShowToast=true;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShowToast)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }


    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, String message) {
        if (isShowToast)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShowToast)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShowToast)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShowToast)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShowToast)
            Toast.makeText(context, message, duration).show();
    }

}
