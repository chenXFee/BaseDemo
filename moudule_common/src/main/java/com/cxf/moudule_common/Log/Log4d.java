package com.cxf.moudule_common.Log;


import android.util.Log;

import java.util.HashSet;
import java.util.Set;

/**
 * 日志接口封装
 */
public class Log4d {
	/**
	 * Tag之间分隔符
	 */
	private final static String TAG_SEPERATION = "_";
	/**
	 * 全局Tag，用来标识应用
	 */
	private static String globalTag = "Log4d";
	/**
	 * Log工具实例
	 */
	private static Log4d instance = new Log4d();
	/**
	 * Log总开关
	 */
	private static boolean toggleFlag = true;

	/**
	 * 标签过滤器
	 */
	private static Set<String> tagFilter;

	private Log4d() {
	}

	/**
	 * 设置全局标签，用来标识应用，默认为"Log4d";
	 * 
	 * @param globalTag 标签名称
	 */
	public static void setGlobalTag(String globalTag) {
		Log4d.globalTag = globalTag;
	}

	/**
	 * 获取实例
	 * 
	 * @return log4d实例
	 */
	public static Log4d getInstance() {
		return instance;
	}

	/**
	 * 设置标签过滤器。只有符合数组中的标签Log才会被打印出来。 每一次设置过滤器都会覆盖掉之前的过滤器。
	 * 
	 * @param tags
	 *            需要打印出来的标签数组。
	 */
	public static void setTagFilter(String[] tags) {
		if (null == tags || tags.length == 0) {
			tagFilter = null;
			return;
		}
		int len = tags.length;
		tagFilter = new HashSet<String>();
		for (int i = 0; i < len; i++) {
			String tag = tags[i];
			if (tag == null || tag.trim().isEmpty()) {
				continue;
			}
			tagFilter.add(tag);
		}
		if (tagFilter.size() == 0) {
			tagFilter = null;
		}
	}

	private String combination(String localTag) {
		localTag = localTag == null ? "" : localTag;
		return globalTag + TAG_SEPERATION + localTag;

	}

	public static void toggle(boolean b) {
		Log4d.toggleFlag = b;
	}

	public static boolean isDebug() {
		return toggleFlag;
	}

	public void verbose(String localTag, String content) {
		log(Level.VERBOSE, localTag, content);
	}

	@SuppressWarnings("rawtypes")
	public void verbose(Class clss, String content) {
		String localTag = clss.getSimpleName();
		log(Level.VERBOSE, localTag, content);
	}

	public void debug(String localTag, String content) {
		log(Level.DEBUG, localTag, content);
	}

	@SuppressWarnings("rawtypes")
	public void debug(Class clss, String content) {
		String localTag = clss.getSimpleName();
		log(Level.DEBUG, localTag, content);
	}

	public void info(String localTag, String content) {
		log(Level.INFO, localTag, content);
	}

	@SuppressWarnings("rawtypes")
	public void info(Class clss, String content) {
		String localTag = clss.getSimpleName();
		log(Level.INFO, localTag, content);
	}

	public void warn(String localTag, String content) {
		log(Level.WARN, localTag, content);
	}

	@SuppressWarnings("rawtypes")
	public void warn(Class clss, String content) {
		String localTag = clss.getSimpleName();
		log(Level.WARN, localTag, content);
	}

	public void error(String localTag, String content) {
		log(Level.ERROR, localTag, content);
	}

	@SuppressWarnings("rawtypes")
	public void error(Class clss, String content) {
		String localTag = clss.getSimpleName();
		log(Level.ERROR, localTag, content);
	}

	public boolean log(Level level, String localTag, String content) {
		String tag = combination(localTag);
		if (tag == null || !toggleFlag) {
			return false;
		}
		if (tagFilter != null) {
			if (!tagFilter.contains(localTag)) {
				return false;
			}
		}

		switch (level) {
		case VERBOSE:
			Log.v(tag, content);
			break;

		case DEBUG:
			Log.d(tag, content);
			break;

		case INFO:
			Log.i(tag, content);
			break;

		case WARN:
			Log.w(tag, content);
			break;

		case ERROR:
			Log.e(tag, content);
			break;
		}
		return true;
	}

	public enum Level {
		VERBOSE, DEBUG, INFO, WARN, ERROR;
	}

}
