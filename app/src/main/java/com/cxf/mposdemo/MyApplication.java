package com.cxf.mposdemo;

import android.app.Application;
import android.content.Context;



import com.zxy.recovery.callback.RecoveryCallback;
import com.zxy.recovery.core.Recovery;

public class MyApplication extends Application {


    private static Context mContext;



    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                initCash();
            }
        }).start();

    }

    public static Context getmContext() {
        return mContext;
    }



    private void initCash() {
        if (BuildConfig.DEBUG) {
            //测试环境
            Recovery.getInstance()
                    .debug(false)
                    .recoverInBackground(false)
                    .recoverStack(true)
                    .mainPage(MainActivity.class)
                    .recoverEnabled(true)
                    .callback(new MyCrashCallback())
                    .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                    .init(this);

        } else {
            //生产环境
            Recovery.getInstance()
                    .debug(false)
                    .recoverInBackground(false)
                    .recoverStack(true)
                    .mainPage(MainActivity.class)
                    .recoverEnabled(false)
                    .callback(new MyCrashCallback())
                    .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                    .init(this);
        }

        MyCrashHandler.register();

    }

    private class MyCrashCallback implements RecoveryCallback {


        @Override
        public void stackTrace(String stackTrace) {


        }

        @Override
        public void cause(String cause) {


        }

        @Override
        public void exception(String throwExceptionType, String throwClassName, String throwMethodName, int throwLineNumber) {


        }

        @Override
        public void throwable(Throwable throwable) {


        }
    }

}
