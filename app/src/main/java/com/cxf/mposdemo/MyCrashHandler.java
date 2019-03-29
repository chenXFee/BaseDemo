package com.cxf.mposdemo;

public class MyCrashHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;

    public MyCrashHandler() {
        mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        mUncaughtExceptionHandler.uncaughtException(t, e);



    }

    public static void register() {
        Thread.setDefaultUncaughtExceptionHandler(new MyCrashHandler());
    }
}
