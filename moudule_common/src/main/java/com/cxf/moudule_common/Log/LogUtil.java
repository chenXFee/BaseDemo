package com.cxf.moudule_common.Log;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志通用接口
 */
public class LogUtil {
	public static final int VERBOSE = 0;
	public static final int DEBUG = 1;
	public static final int INFO = 2;
	public static final int WARNING = 3;
	public static final int ERROR = 4;
	public static final int NO_LOG = 5;

	private static int LOG_LEVEL = NO_LOG;
	public static boolean writeFile = false;
	private static LogUtil instance = null;
	public static String tag = "";

	@SuppressLint("SimpleDateFormat")
	private static String getTimeStr() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());
		return formatter.format(curDate);
	}

	/**
	 * 追加文件：使用FileWriter
	 *
	 * @param level 日志等级
	 * @param content 日志内容
	 */
	public static void write(String level, String content) {
		if (!writeFile) {
			return;
		}

		String fileName = Environment.getExternalStorageDirectory() + "/smartposDevice/log.txt";
		try {
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(getTimeStr() + ":" + level + ":" + content + "\n");
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	// public static final String LOGTAG = "wen";
	public static void setLogLevel(int level) {
		LOG_LEVEL = level;
	}

	private static LogUtil getInstance() {
		if (instance == null) {
			instance = new LogUtil();
		}
		return instance;
	}

	private String getFunctionName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();
		if (sts == null) {
			return null;
		}
		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}
			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}
			if (st.getClassName().equals(this.getClass().getName())) {
				continue;
			}
			tag = st.getClassName();
			return "[ " + Thread.currentThread().getName() + ": "
					+ st.getFileName() + ":" + st.getLineNumber() + " "
					+ st.getMethodName() + " ]";
		}
		return null;
	}

	// verbose
	public static void v(String tag, String msg) {
		if (VERBOSE < LOG_LEVEL)
			return;
		Log.v(tag, msg);
		write("VERBOSE", msg);
	}

	public static void v(String msg) {
		v(getInstance().getFunctionName(), msg);
	}

	// debug
	public static void d(String tag, String msg) {
		if (DEBUG < LOG_LEVEL)
			return;
		Log.d(tag, msg);
		write("DEBUG", msg);
	}

	public static void d(String msg) {
		d(getInstance().getFunctionName(), msg);
	}

	// info
	public static void i(String tag, String msg) {
		if (INFO < LOG_LEVEL)
			return;
		Log.i(tag, msg);
		write("INFO", msg);
	}

	public static void i(String msg) {
		i(getInstance().getFunctionName(), msg);
	}

	// warning
	public static void w(String tag, String msg) {
		if (WARNING < LOG_LEVEL)
			return;
		Log.w(tag, msg);
		write("WARNING", msg);
	}

	public static void w(String msg) {
		w(getInstance().getFunctionName(), msg);
	}

	public static void w(String msg, Throwable tr) {
		w(getInstance().getFunctionName(), msg + tr.getMessage());
	}

	public static void w(String tag, String msg, Throwable tr) {
		w(tag, msg + tr.getMessage());
	}

	public static void e(String tag, String msg, Throwable tr) {
		e(tag, msg + tr.getMessage());
	}

	// error
	public static void e(String tag, String msg) {
		if (ERROR < LOG_LEVEL)
			return;
		Log.e(tag, msg);
		write("ERROR", msg);
	}

	public static void e(String msg) {
		e(getInstance().getFunctionName(), msg);
	}

	public static void e(String msg, Throwable tr) {
		e(getInstance().getFunctionName(), msg + tr.getMessage());
	}
}
