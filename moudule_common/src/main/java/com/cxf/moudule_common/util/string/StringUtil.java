package com.cxf.moudule_common.util.string;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <p>Title: 常见的字符串操作和函数封装类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: 2011</p>
 *
 * <p>Company: GOTOP</p>
 *
 * @author phc
 *
 * @date 2011-3-4
 *
 * @version 1.0
 **/
public class StringUtil {
	/** 
     * 判断字符串是否为空 
     * @param str 
     * null、“ ”、“null”都返回true 
     * @return 
     */  
    public static boolean isNullString(String str) {
        return (null == str ||  str.length() <= 0 || str.trim()=="" ||str.trim().equals("") || "null".equals(str.trim().toLowerCase()) 
        		||  "undefined".equals(str.trim().toLowerCase()) ) ? true : false;  
    } 
	/**
	 * 去除字符串两端的空格，如果为null，则返回空字符串
	 * @param str 要去除空格的字符串
	 * @return 去掉两端空格的字符串
	 */
	public static String trim(String str){
		return (str == null ? "":str.trim()); 
	}
	/**
     * 取得系统当前日期的字符串形式
     * @param format 日期字符串的格式
     * @return 指定格式的日期字符串
     */
    public static String getCurrentDate(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String strNowDate = dateFormat.format(Calendar.getInstance().getTime());
		return strNowDate;
	}
	 /**
	 * 解析日期格式yyyy-MM-dd为yyyyMMdd，如转化日期2008-08-08为20080808
	 * @param strDate 格式为yyyy-MM-dd的日期字符串
	 * @return 格式为yyyyMMdd的日期字符串
	 */
	public static String parseDate(String strDate) {
		strDate = trim(strDate);
		
		if(strDate.length() == 10) {
			StringBuffer buffer = new StringBuffer("");
			buffer.append(strDate.substring(0, 4));
			buffer.append(strDate.substring(5, 7));
			buffer.append(strDate.substring(8, 10));
			strDate = buffer.toString();
		}
		
		return strDate;
    }
	/**
     * 解析时间格式HH:mm:ss为HHmmss，如转化时间08:08:08为080808
     * @param strTime 格式为HH:mm:ss的时间字符串
     * @return 格式为HHmmss的时间字符串
     */
    public static String parseTime(String strTime) {
        strTime = trim(strTime);
        if(strTime.length() == 8) {
			StringBuffer buffer = new StringBuffer("");
			buffer.append(strTime.substring(0, 2));
			buffer.append(strTime.substring(3, 5));
			buffer.append(strTime.substring(6, 8));
			strTime = buffer.toString();
		}
		return strTime;
    }
	
    /**
	 * 转化日期格式yyyyMMdd为yyyy-MM-dd，如转化日期20080808为2008-08-08
	 * @param strDate 格式为yyyyMMdd的日期字符串
	 * @return 格式为yyyy-MM-dd的日期字符串
	 */
	public static String parseDateEx(String strDate) {
		strDate = trim(strDate);
		
		if(strDate.length() == 8) {
			StringBuffer buffer = new StringBuffer("");
			buffer.append(strDate.substring(0, 4));
			buffer.append("-");
			buffer.append(strDate.substring(4, 6));
			buffer.append("-");
			buffer.append(strDate.substring(6, 8));
			
			strDate = buffer.toString();
		}
		
		return strDate;
	}
	   /**
	 * 转化日期格式yyyyMM为yyyy-MM，如转化日期200808为2008-08
	 * @param strDate 格式为yyyyMM的日期字符串
	 * @return 格式为yyyy-MM的日期字符串
	 */
	public static String parseyyyyMM(String strDate) {
		strDate = trim(strDate);
		
		if(strDate.length() == 8) {
			StringBuffer buffer = new StringBuffer("");
			buffer.append(strDate.substring(0, 4));
			buffer.append("-");
			buffer.append(strDate.substring(4, 6));
			
			strDate = buffer.toString();
		}
		
		return strDate;
	}
	
	/**
     * 转化时间格式HHmmss为HH:mm:ss，如转化时间080808为08:08:08
     * @param strTime 格式为HHmmss的时间字符串
     * @return 格式为HH:mm:ss的时间字符串
     */
    public static String parseTimeEx(String strTime) {
        strTime = trim(strTime);
		if(strTime.length() == 6) {
			StringBuffer buffer = new StringBuffer("");
			buffer.append(strTime.substring(0, 2));
			buffer.append(":");
			buffer.append(strTime.substring(2, 4));
			buffer.append(":");
			buffer.append(strTime.substring(4, 6));
			
			strTime = buffer.toString();
		}
		
		return strTime;
    }
    /**
	 * 转化日期格式yyyyMMddHHmmss为yyyy-MM-dd HH:mm:ss，如转化日期20080808080808为2008-08-08 08:08:08
	 * @param strDate 格式为yyyyMMddHHmmss的日期字符串
	 * @return 格式为yyyy-MM-dd  HH:mm:ss的日期字符串
	 */
	public static String parseFullDateEx(String strDate) {
		strDate = trim(strDate);
		
		if(strDate.length() == 14) {
			StringBuffer buffer = new StringBuffer("");
			buffer.append(parseDateEx(strDate.substring(0, 8)));
			buffer.append(" ");
			buffer.append(parseTimeEx(strDate.substring(8, 14)));
			
			strDate = buffer.toString();
		}
		
		return strDate;
	}
	/**
	 * 转化日期格式yyyy-MM-dd HH:mm:ss为yyyyMMddHHmmss，如转化日期2008-08-08 08:08:08为20080808080808
	 * @param strDate
	 * @return
	 */
	public static String parseFullDate(String strDate){
		strDate=trim(strDate);
		if(strDate.length()==19){
			StringBuffer buffer=new StringBuffer("");
			buffer.append(parseDate(strDate.substring(0, 10)));
			buffer.append(parseTime(strDate.substring(11)));
			
			strDate=buffer.toString();
		}
		return strDate;
	}
    /**
     * md5散列加密
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String str) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                           	'a', 'b', 'c', 'd','e', 'f'};
        try {
            byte[] strTemp = str.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char tmp[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                tmp[k++] = hexDigits[byte0 >>> 4 & 0xf];
                tmp[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(tmp);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 转换Double数据类型精度(四舍五入)
     * @param value 要转换的数据
     * @param precision 小数点后的位数
     * @return double 转换的数据
     */
    public static double convertPrecision(double value, int precision) {
    	String s = "0."+getFillZero("", precision);
    	DecimalFormat df = new DecimalFormat(s);
    	return Double.parseDouble(df.format(value));
    }
    /**
	 * 将字符串中的小写字母转换为大写字母
	 * 
	 * @param s
	 *            输入字符串
	 * @return 返回转换为大写字母后的字符串
	 */
	public static byte[] byteUpper(byte[] s) {
		return (new String(s)).toUpperCase().getBytes();
	}
	
	/**
	 * 将字符串中的大写字母转换为小写字母
	 * 
	 * @param s
	 *            输入字符串
	 * @return 返回转换为小写字母后的字符串
	 */
	public static byte[] byteLower(byte[] s) {
		return (new String(s)).toLowerCase().getBytes();
	}
	/**
	 * 判断输入IP是否为合法IP
	 * 
	 * @param ip
	 *            输入ip
	 * @param flag
	 *            有无通配符(*)标志，true有，false则无
	 * @return 输入IP，合法返回true,反之则false
	 */
	public static boolean isIP(String ip, boolean flag) {
		String test = "";
		if (flag)
			test = "([1-9]|[1-9]\\d|1\\d{2}|2[0-1]\\d|22[0-3]|\\*)(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5]|\\*)){3}";
		else
			test = "([1-9]|[1-9]\\d|1\\d{2}|2[0-1]\\d|22[0-3])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pattern = Pattern.compile(test);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}
	/**
	 * 转换金额到人民币大写金额
	 * 
	 * @param value
	 *            输入金额
	 * @return 返回转换后的结果
	 */
	public static String amtConvUp(double value) {
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿', '万', }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (value * 100); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串
		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
		String rail = valStr.substring(valStr.length() - 2); // 取小数部分

		// 最大支持千万亿级别
		if (head.length() > 16) {
			return "";
		}
		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角"
					+ digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0') { // 标志
					zero = digit[0];
				}
				if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				// 特例,处理万亿整零以上的
				if (idx == 0 && vidx == 2 && zeroSerNum >= 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示
			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}

		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示

	}
	/**
	 * 计算字符串包括尾部的空格和制表符后的实际长度
	 * 
	 * @param s
	 *            输入字符串
	 * @return 直接返回实际的长度
	 */
	public static long realLen(byte[] s) {
		if (s == null)
			return 0;
		int i = s.length - 1;
		while (0 <= i && (32 == s[i] || 9 == s[i]))
			i--;
		return i + 1;
	}

	/**
	 * 计算字符串包括尾部的空格和制表符后的实际长度
	 * @param s输入字符串
	 * @return 直接返回实际的长度
	 */
	public static long realLen(String s) {
		return s == null ? 0 : realLen(s.getBytes());
	}
	
	/**
	 * 计算字符串的实际长度，不包括尾部的文件换行符
	 * 
	 * @param s
	 *            输入字符串
	 * @return 直接返回实际的长度
	 */
	public static long fileLineLen(byte[] s) {
		if (s == null)
			return 0;
		int i = s.length - 1;
		while (0 <= i && (10 == s[i] || 13 == s[i]))
			i--;
		return i + 1;
	}
	/**
	 * 计算字符串的实际长度，不包括尾部的文件换行符
	 * 
	 * @param s
	 *            输入字符串
	 * @return 直接返回实际的长度
	 */
	public static long fileLineLen(String s) {
		return s == null ? 0 : fileLineLen(s.getBytes());
	}
	/**
	 * 去掉输入字符串尾部的换行符以及空格和制表符
	 * 
	 * @param s
	 *            输入字符串
	 * @return 直接返回去掉尾部空格、制表符、换行符后的结果串
	 */
	public static String trimCRLF(String s) {
		if (s == null)
			return null;
		char[] ch = s.toCharArray();
		int i = ch.length - 1;
		while (0 <= i
				&& (10 == ch[i] || 13 == ch[i] || 32 == ch[i] || 9 == ch[i]))
			i--;
		return s.substring(0, i + 1);
	}
	
	/**
	 * 去掉串尾部的指定字符
	 * 
	 * @param s
	 *            输入字符串
	 * @param c
	 *            指定删除的字符
	 * @return 返回去掉串尾部的指定字符
	 */
	public static String trimRightChar(String s, char c) {
		if (s == null)
			return null;
		char[] chr = s.toCharArray();
		int i = chr.length - 1;
		while (0 <= i && c == chr[i])
			i--;
		return new String(chr, 0, i + 1);
	}

	/**
	 * 去掉输入字符串尾部的空格和制表符的字符串
	 * 
	 * @param s
	 *            输入字符串
	 * @return 返回去掉串尾部的空格和制表符的字符串
	 */
	public static String trimRight(String s) {
		if (s == null)
			return null;
		char[] ch = s.toCharArray();
		int i = ch.length - 1;
		while (0 <= i && (32 == ch[i] || 9 == ch[i]))
			i--;
		return s.substring(0, i + 1);
	}

	/**
	 * 去掉输入字符串头部的空格和制表符的字符串
	 * 
	 * @param s
	 *            输入字符串
	 * @return 返回去掉串头部的空格和制表符的字符串
	 */
	public static String trimLeft(String s) {
		if (s == null)
			return null;
		char[] ch = s.toCharArray();
		int i = 0;
		while (i <= ch.length - 1 && (32 == ch[i] || 9 == ch[i]))
			i++;
		return s.substring(i);
	}

	/**
	 * 根据长度左补0
	 * @param str 原字符串
	 * @param length 需要长度
	 * @return 
	 */
	public static String getFillZero(String str, int length){
		str=trim(str);
		while(str.length()<length){
			str="0"+str;
		}
		return str;
	}
	/**
	 * 根据长度右补空格
	 * @param str原字符串
	 * @param length需要长度
	 * @return
	 */
	public static String getFillBlank(String str, int length){
		str=trim(str);
		while(str.length()<length){
			str=str+" ";
		}
		return str;
	}
	/**
	 * 将数字输入返回指定长度的16进制字符串，长度小于转换后字符串的返回-1
	 * @param i 输入的数字
	 * @param size 需要的长度
	 * @return
	 */
	public static String toHexString(int i, int size){
		String str = Integer.toHexString(i);
		return size<str.length()?"-1":getFillZero(str, size);
	}
	/**
	 * 将数字输入返回指定长度的16进制字符串，长度小于转换后字符串的返回-1
	 * @param i 输入的数字
	 * @param size 需要的长度
	 * @return
	 */
	public static String toHexString(long i, int size){
		String str = Long.toHexString(i);
		return size<str.length()?"-1":getFillZero(str, size);
	}
	
	/**
	 * 检查输入字符串中的指定位置的字符是否是汉字
	 * @param s 输入字符串
	 * @param in_lPos 当前字符的位置，从0开始记数
	 * @return 返回true，表示当前字符是汉字；false，表示当前字符不是汉字
	 */
	public static boolean isChineseChar(String s, int in_lPos){
		if(trim(s)==null||trim(s).equals(""))
			return false;
		if(String.valueOf(s.toCharArray()[in_lPos]).matches("[\\u4E00-\\u9FA5]"))
			return true;
		else
			return false;
	}

	/**
	 * 判断一个输入的字符串是否是一个数字字符串，可以包含+或-符号，以及小数点
	 * 
	 * @param s
	 *            输入的字符串
	 * @return 如果输入串是数字串，返回true；不是则返回false
	 */
	public static boolean isDigit(String s){
		String reg = "^[-|+]?([0-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";
		return s.matches(reg);
	}

	/**
	 * 判断一个输入的字符串是否是一个只包含’0’～’9’的字符串
	 * 
	 * @param s
	 *            输入字符串
	 * @return 如果输入串是整数串，返回true；不是则返回false
	 */
	public static boolean isNumber(String s) {
		if (s == null)
			return false;
		try {
			Long.parseLong(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 判断一个String是否16进制数字串
	 * 
	 * @param s输入的字符串
	 * @return 如果输入串是，是返回true；不是则返回false
	 */
	public static boolean isXDigit(String s) {
		return s.matches("^([0-9]|[A-F]|[a-f])*$");
	}



	/**
	 * 判断输入指定长度的字符串中是否包含中文字符
	 * 
	 * @param slen
	 *            希望检查输入字符串中的前多少长度的字符
	 * @param s
	 *            输入的字符串
	 * @return 判断输入串s中是否包含中文字符，包含则返回true；不是则返回false
	 */
	public static boolean isInclChinese(int slen, String s) {
		String reg = ".*[\\u4E00-\\u9FA5]+.*";
		String str = s.substring(0,slen);
		return str.matches(reg);
	}

	/**
	 * 判断输入的字符串中是否包含中文字符
	 * 
	 * @param s
	 *            输入的字符串
	 * @return 判断输入串s中是否包含中文字符，包含则返回true；不是则返回false
	 */
	public static boolean isInclChinese(String s) {
		return s == null ? false : isInclChinese(s.length(), s);
	}

	/**
	 * 查询一个字符串中子串出现的次数
	 * 
	 * @param in_pszMain
	 *            输入的查询字符串
	 * @param in_pszSubstr
	 *            子串
	 * @return 返回子串在源字符串中出现的次数
	 */
	public static int countSubstr(String in_pszMain, String in_pszSubstr) {
		if (in_pszMain == null || in_pszSubstr == null)
			return 0;
		int i = 0;
		int j = 0;
		while (1 == 1) {
			j = in_pszMain.indexOf(in_pszSubstr, j);
			if (j > -1) {
				i++;
				j = j + in_pszSubstr.length();
			} else
				break;
		}
		return i;
	}
	/**
	 * 在一个IP范围字符串内，检测特定的IP是否在这个IP范围内
	 * 
	 * @param in_pszList
	 *            指定IP列表字符串。IP范围可以使用~作为范围值，例如：192.168.0.1~192.169.3.23。
	 *            或者使用*作为通配符 ，例如：139.139.*.*或者139.139.*.1。IP范围字符串以;作为分隔符。
	 *            例子：192.168.0.1~192.169.3.23; 139.139.*.*;10.10.2.3;
	 * @param in_pszIP
	 *            特定的合法IP
	 * @return 在范围内返回true,反之则false
	 */
	public static boolean checkIP(String in_pszList, String in_pszIP) {
		// TODO:
		boolean result = true;
		if (in_pszList == null || in_pszIP == null)
			return false;
		if (!isIP(in_pszIP, false))
			return false;
		String ipList[] = in_pszList.split(";");
		String ipPszIp[] = in_pszIP.split("\\.");
		for (int i = 0; i < ipList.length; i++) {
			// 每次循环初始化结果为真
			result = true;
			String ip_str = ipList[i];
			if (ip_str.indexOf("~") > 0) {
				String ip_begstr = ip_str.substring(0, ip_str.indexOf("~"));
				String ip_endstr = ip_str.substring(ip_str.indexOf("~") + 1);
				if (!isIP(ip_begstr, true) || !isIP(ip_endstr, true))
					result = false;
				String ip_beg[] = ip_begstr.split("\\.");
				String ip_end[] = ip_endstr.split("\\.");
				for (int j = 0; j < 4; j++) {
					if ("*".equals(ip_beg[j]) && "*".equals(ip_end[j])) {
						if (j == 0) {
							if (Integer.parseInt(ipPszIp[j]) > 255
									|| Integer.parseInt(ipPszIp[j]) <= 0)
								result = false;
						} else {
							if (Integer.parseInt(ipPszIp[j]) > 255
									|| Integer.parseInt(ipPszIp[j]) < 0)
								result = false;
						}
					} else if ("*".equals(ip_beg[j]) && !"*".equals(ip_end[j])) {
						if (j == 0) {
							if (Integer.parseInt(ipPszIp[j]) > Integer
									.parseInt(ip_end[j])
									|| Integer.parseInt(ipPszIp[j]) <= 0)
								result = false;
						} else {
							if (Integer.parseInt(ipPszIp[j]) > Integer
									.parseInt(ip_end[j])
									|| Integer.parseInt(ipPszIp[j]) < 0)
								result = false;
						}

					} else if (!"*".equals(ip_beg[j]) && "*".equals(ip_end[j])) {
						if (Integer.parseInt(ipPszIp[j]) > 255
								|| Integer.parseInt(ipPszIp[j]) < Integer
										.parseInt(ip_beg[j]))
							result = false;
					} else {
						if (Integer.parseInt(ipPszIp[j]) > Integer
								.parseInt(ip_end[j])
								|| Integer.parseInt(ipPszIp[j]) < Integer
										.parseInt(ip_beg[j]))
							result = false;
					}
				}
			} else {
				if (!isIP(ip_str, true))
					result = false;
				String ip_column1 = ip_str.substring(0, ip_str.indexOf("."));
				ip_str = ip_str.substring(ip_str.indexOf(".") + 1);
				if ("*".equals(ip_column1)) {
					if (Integer.parseInt(ipPszIp[0]) > 255
							|| Integer.parseInt(ipPszIp[0]) <= 0)
						result = false;
				} else {
					if (!ipPszIp[0].equals(ip_column1))
						result = false;
				}
				String ip_column2 = ip_str.substring(0, ip_str.indexOf("."));
				ip_str = ip_str.substring(ip_str.indexOf(".") + 1);
				if ("*".equals(ip_column2)) {
					if (Integer.parseInt(ipPszIp[1]) > 255
							|| Integer.parseInt(ipPszIp[1]) < 0)
						result = false;
				} else {
					if (!ipPszIp[1].equals(ip_column2))
						result = false;
				}
				String ip_column3 = ip_str.substring(0, ip_str.indexOf("."));
				ip_str = ip_str.substring(ip_str.indexOf(".") + 1);
				if ("*".equals(ip_column3)) {
					if (Integer.parseInt(ipPszIp[2]) > 255
							|| Integer.parseInt(ipPszIp[2]) < 0)
						result = false;
				} else {
					if (!ipPszIp[2].equals(ip_column3))
						result = false;
				}
				String ip_column4 = ip_str;
				if ("*".equals(ip_column4)) {
					if (Integer.parseInt(ipPszIp[3]) > 255
							|| Integer.parseInt(ipPszIp[3]) < 0)
						result = false;
				} else {
					if (!ipPszIp[3].equals(ip_column4))
						result = false;
				}
			}
			if (result)
				break;
		}
		return result;
	}
	/**
	 * 判断字符串s是否与字符串wcc匹配，其中wcc为可通配（通配符*）的字符串
	 * 
	 * ? *
	 * 
	 * @param s
	 *            被比较字符串
	 * @param wcc
	 *            比较字符串
	 * @return 匹配则返回true，否则返回false
	 */
	public static boolean isMatch(String s, String wcc) {
		if (s == null || wcc == null)
			return false;
		wcc = wcc.replace("*", ".*");
		wcc = wcc.replace("?", ".{1}?");
		boolean b = Pattern.matches(wcc, s);
		return b;
	}
	/**
	 * 将错误转为String格式返回
	 * @param e
	 * @return
	 */
	public static String stringifyException(Throwable e){
		StringWriter stm = new StringWriter();
	    PrintWriter wrt = new PrintWriter(stm);
	    e.printStackTrace(wrt);
	    wrt.close();
	    return stm.toString();
	}
}
