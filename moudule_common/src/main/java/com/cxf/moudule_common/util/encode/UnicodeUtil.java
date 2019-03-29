package com.cxf.moudule_common.util.encode;
import java.io.UnsupportedEncodingException;

/**
 * 
 * 
 * @author phc
 * @version 1.0
 * @date 2010-3-3
 */
public class UnicodeUtil {

	public static String charsetChange(String str, String target) {
		if (str == null || str.trim().equals("") || target == null
				|| target.trim().equals("")) {
			return str;
		}

		try {
			str = new String(str.getBytes(), target);
		} catch (UnsupportedEncodingException e) {
			System.err.println(e.getMessage());
		}

		return str;
	}
	
	/**
	 * 输入UNICODE8编码的字符串转换为UNICODE16编码(Little Endian)编码
	 * 
	 * @param str
	 *            utf-8格式的字符串
	 * @return utf-16(Little Endian)格式的字符串
	 */
	public static String utf8_utf16(String str) {
		if (str == null) {
			return null;
		}
		return charsetChange(str, "utf-16le");
	}

	/**
	 * 输入UNICODE8编码的字符串转换为UNICODE16编码(Big Endian)编码
	 * 
	 * @param str
	 *            utf-8格式的字符串
	 * @return utf-16(Big Endian)格式的字符串
	 */
	public static String utf8_UTF16(String str) {
		if (str == null) {
			return null;
		}
		return charsetChange(str, "utf-16be");
	}

	/**
	 * UNICODE16(Little Endian)编码的字符串转换为输入utf-8编码
	 * 
	 * @param str
	 *            utf-16(Little Endian)格式的字符串
	 * @return utf-8格式的字符串
	 */
	public static String utf16_utf8(String str) {
		if (str == null) {
			return null;
		}
		return charsetChange(str, "utf-8");
	}

	/**
	 * UNICODE16(Big Endian)编码的字符串转换为utf-8编码
	 * 
	 * @param str
	 *            utf-16(Big Endian)格式的字符串
	 * @return utf-8格式的字符串
	 */
	public static String UTF16_utf8(String str) {
		if (str == null) {
			return null;
		}
		return charsetChange(str, "utf-8");
	}

	/**
	 * UNICODE16(Big Endian)编码的字符串转换为gb2312编码
	 * 
	 * @param str
	 *            utf-16(Big Endian)格式的字符串
	 * @return gb2312格式的字符串
	 */
	public static String UTF16_gb(String str) {
		if (str == null) {
			return null;
		}
		return charsetChange(str, "gb2312");
	}

	/**
	 * UNICODE16(Little Endian)编码的字符串转换为gb2312编码
	 * 
	 * @param str
	 *            utf-16(Little Endian)格式的字符串
	 * @return gb2312格式的字符串
	 */
	public static String utf16_gb(String str) {
		if (str == null) {
			return null;
		}
		return charsetChange(str, "gb2312");
	}

	/**
	 * utf8编码的字符串转换为gb2312编码
	 * 
	 * @param str
	 *            utf-8格式的字符串
	 * @return gb2312格式的字符串
	 */
	public static String utf8_gb(String str) {
		if (str == null) {
			return null;
		}
		return charsetChange(str, "gb2312");
	}

	/**
	 * 计算一个字符串的实际长度
	 * 
	 * @param str
	 *            字符串
	 * @return 字符串所占字节数
	 */
	public static long strlen(String str) {
		return str.getBytes().length;
	}
}