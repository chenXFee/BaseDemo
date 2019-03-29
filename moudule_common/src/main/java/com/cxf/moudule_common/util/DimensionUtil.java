package com.cxf.moudule_common.util;


import android.content.Context;

/****
 * 尺度相关的方法
 */
public class DimensionUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
