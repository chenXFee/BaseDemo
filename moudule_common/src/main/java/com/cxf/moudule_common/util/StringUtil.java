package com.cxf.moudule_common.util;

import android.text.TextUtils;

/**
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                           O\  =  /O
 * //                        ____/`---'\____
 * //                      .'  \\|     |//  `.
 * //                     /  \\|||  :  |||//  \
 * //                    /  _||||| -:- |||||-  \
 * //                    |   | \\\  -  /// |   |
 * //                    | \_|  ''\---/''  |   |
 * //                    \  .-\__  `-`  ___/-. /
 * //                  ___`. .'  /--.--\  `. . __
 * //               ."" '<  `.___\_<|>_/___.'  >'"".
 * //              | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * //              \  \ `-.   \_ __\ /__ _/   .-` /  /
 * //         ======`-.____`-.___\_____/___.-`____.-'======
 * //                            `=---='
 * 类作用描述:
 * 作者: chenjf(316031139@qq.com)
 * 创建时间：2017/9/22 23:52
 * 创建时间: 2017/9/22 23:52
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StringUtil {


    /***
     * 拼接字符串
     * @param str
     * @return
     */
    public static String spliceString(String... str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : str) {
            stringBuffer.append(s);
        }
        return stringBuffer.toString();
    }

    public static String spliceString(Object... str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Object s : str) {
            stringBuffer.append(s);
        }
        return stringBuffer.toString();
    }

    /***
     * 判断是否为空
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        boolean isaa = false;
        if(TextUtils.isEmpty(string)){
            isaa=true;
            return isaa;
        }
        string = string.trim();
        if (string == null || string.length() <= 0 || "null".equalsIgnoreCase(string)) {
            isaa = true;
        }
        return isaa;
    }
}
