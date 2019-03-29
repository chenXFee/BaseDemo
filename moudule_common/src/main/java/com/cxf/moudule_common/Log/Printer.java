package com.cxf.moudule_common.Log;

/**
 * 类作用描述: 输出工作台的格式
 * 来源:https://github.com/orhanobut/logger(提取满足调试开发)
 * 作者:chenjianfan
 * 创建时间:2016-04-20:09:41
 * 修改人:
 * 修改时间:
 * 修改内容:
 */
public interface Printer {


    Printer t(String tag, int methodCount);

    Settings init(String tag);

    Settings getSettings();

    void d(String message, Object... args);

    void e(String message, Object... args);

    void e(Throwable throwable, String message, Object... args);

    void w(String message, Object... args);

    void i(String message, Object... args);

    void v(String message, Object... args);

    void wtf(String message, Object... args);

    void json(String json);

    void xml(String xml);

    void clear();


}
