package com.cxf.moudule_common.Log;

import android.util.Log;

/**
 * log4j日志操作
 * @author Tianxiaobo
 *
 */
public class Loger{

	private boolean isDebug = false;	//日志开关
	private static Loger log = null;
	@SuppressWarnings("rawtypes")
	private static Class clss = null;

	/**
	 * 获取类实例
	 * @param cls 类
	 * @return loger实例
	 */
	@SuppressWarnings("rawtypes")
	public static Loger getLogger(Class cls){
		clss = cls;
		if(null == log){
			log = new Loger();
		}
		return log;
	}

	/**
	 * 打印debug级别的日志
	 * @param str 日志内容
	 */
	public void debug(String str){
		if(isDebug){
			Log.d(clss.getName().toString(),str);
		}
	}

	public void debug(String str1, Throwable e){
		if(isDebug){
			Log.d(clss.getName().toString(),str1 + e.getLocalizedMessage());
		}
	}

	public void error(String str){
		Log.e(clss.getName().toString(),str);
	}

	public void error(String str, Throwable e){
		Log.e(clss.getName().toString(),str + e.getLocalizedMessage());
	}

	public void info(String str){
		Log.i(clss.getName().toString(),str);
	}

	public void info(String str, Throwable e){
		Log.i(clss.getName().toString(),str + e.getLocalizedMessage());
	}

	public void warn(String str){
		Log.w(clss.getName().toString(),str);
	}

	public void warn(String str, Throwable e){
		Log.w(clss.getName().toString(),str + e.getLocalizedMessage());
	}
}
