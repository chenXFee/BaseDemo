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
public interface LogTool {
  void d(String tag, String message);

  void e(String tag, String message);

  void w(String tag, String message);

  void i(String tag, String message);

  void v(String tag, String message);

  void wtf(String tag, String message);
}