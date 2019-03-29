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
public final class Settings {

  private int methodCount = 2;
  private boolean showThreadInfo = true;
  private int methodOffset = 0;
  private LogTool logTool;

  private LogLevel logLevel = LogLevel.FULL;

  public Settings hideThreadInfo() {
    showThreadInfo = false;
    return this;
  }


  @Deprecated
  public Settings setMethodCount(int methodCount) {
    return methodCount(methodCount);
  }

  public Settings methodCount(int methodCount) {
    if (methodCount < 0) {
      methodCount = 0;
    }
    this.methodCount = methodCount;
    return this;
  }


  @Deprecated
  public Settings setLogLevel(LogLevel logLevel) {
    return logLevel(logLevel);
  }

  public Settings logLevel(LogLevel logLevel) {
    this.logLevel = logLevel;
    return this;
  }


  @Deprecated
  public Settings setMethodOffset(int offset) {
    return methodOffset(offset);
  }

  public Settings methodOffset(int offset) {
    this.methodOffset = offset;
    return this;
  }

  public Settings logTool(LogTool logTool) {
    this.logTool = logTool;
    return this;
  }

  public int getMethodCount() {
    return methodCount;
  }

  public boolean isShowThreadInfo() {
    return showThreadInfo;
  }

  public LogLevel getLogLevel() {
    return logLevel;
  }

  public int getMethodOffset() {
    return methodOffset;
  }

  public LogTool getLogTool() {
    if (logTool == null) {
      logTool = new AndroidLogTool();
    }
    return logTool;
  }
}
