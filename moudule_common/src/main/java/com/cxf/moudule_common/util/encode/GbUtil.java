package com.cxf.moudule_common.util.encode;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;

/**
 * GB2312作为中文字符集，收录了绝大部分的常用汉字，覆盖率达到99.75%。作为母语，当然实际应用中经常会遇到GB编码集，因此对于编码集转换，
 * 主要集中在GB和Unicode的转换，我们在此进行封装。
 * 
 * @author PHC
 * @version 1.0
 * @date 2010-3-3
 */
public class GbUtil {

	private static CharsetDecoder gb = Charset.forName("gb2312").newDecoder();
	private static String[] base16 = {"0", "1" , "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

	/**
	 * gb2312编码的字符串转换为UNICODE16(Big Endian)编码
	 * 
	 * @param str
	 *            gb2312格式的字符串
	 * 
	 * @return utf-16(Big Endian)格式的字符串
	 */
	public static String gb_UTF16(String str) {
		if (str == null) {
			return null;
		}
		return UnicodeUtil.charsetChange(str, "utf-16be");
	}

	/**
	 * gb2312编码的字符串转换为UNICODE16(Little Endian)编码
	 * 
	 * @param str
	 *            gb2312格式的字符串
	 * @return utf-16(Little Endian)格式的字符串
	 */
	public static String gb_utf16(String str) {
		if (str == null) {
			return null;
		}
		return UnicodeUtil.charsetChange(str, "utf-16le");
	}

	/**
	 * gb2312编码的字符串转换为utf8编码
	 * 
	 * @param str
	 *            gb2312格式的字符串
	 * @return utf-8格式的字符串
	 */
	public static String gb_utf8(String str) {
		if (str == null) {
			return null;
		}
		return UnicodeUtil.charsetChange(str, "utf-8");
	}

	/**
	 * 判断指定字符串从0位开始到指定位置长度的字符串的编码是否为GB2312
	 * 
	 * @param len
	 *            指定位置
	 * @param str
	 *            指定字符串
	 * @return true-指定字符串是GB2312编码<br />
	 *         false-指定字符串不是GB2312编码，或者传入字符串为空
	 */
	public static boolean isAscii(int len, byte[] b) {
		if (b == null)
			return false;

		if (len <= 0 || len > b.length) {
			len = b.length;
		}
		ByteBuffer bb = ByteBuffer.wrap(b, 0, len);
		try {
			gb.decode(bb);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * @param b
	 * @param offset
	 *            要使用的子数组的偏移量；必须为非负且不大于 array.length。
	 * @param length
	 *            要使用的子数组的长度；必须为非负且不大于 array.length - offset。
	 * @return
	 */
	public static boolean isAscii(byte[] b, int offset, int length) {
		if (b == null || offset < 0 || length <= 0 || offset >= b.length)
			return true;

		if (offset + length > b.length) {
			length = b.length - offset;
		}

		ByteBuffer bb = ByteBuffer.wrap(b, offset, length);
		try {
			gb.decode(bb);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isAscii(byte[] b) {
		if (b == null)
			return false;
		ByteBuffer bb = ByteBuffer.wrap(b);
		try {
			gb.decode(bb);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean isGB2312(byte[] b) {
		if (b == null)
			return false;
		boolean ret = true;
		for (int i = 0; i < b.length; i++) {
			byte by = b[i];
			if (by >= 32 && by <= 127) {
				continue;
			} else if (i + 1 < b.length && by >= -80 && by <= -9
					&& b[i + 1] >= -95 && b[i + 1] <= 1) {
				i++;
				continue;
			}

			ret = false;
		}
		return ret;
	}

	private static boolean isGB2312(char c) {
		Character ch = new Character(c);
		String sCh = ch.toString();
		try {
			byte[] bb = sCh.getBytes("gb2312");
			if (bb.length == 1 && c >= 0 && c <= 127) {
				return true;
			} else if (bb.length > 1)
				return true;
		} catch (UnsupportedEncodingException ex) {
			return false;
		}
		return false;
	}

	/**
	 * 判断指定字符串从0位开始到指定位置长度的字符串的编码是否为GB2312
	 * 
	 * @param len
	 * @param str
	 * @return
	 */
	public static boolean isVisual(int len, byte[] b) {
		if (b == null)
			return false;

		if (len > b.length )
			len = b.length;

		for (int i=0;i<len;i++) {
			if (b[i] == 0) // byte数组中包含 0x00 字符，则不可见
				return false;
		}

		return isAscii(len, b);
	}
	public static boolean isVisual(byte[] b){
		if (b == null)
			return false;
		return isVisual(b.length, b);
	}

	/**
	 * 判断输入的串是否可在日志中输出的GB2312的字符串。而且串的字节数不为超过240个字节
	 * 
	 * @param len
	 * @param str
	 * @return
	 */
	public static boolean isLog(int len, byte[] b) {
		if (b == null || len != b.length)
			return false;
		if (ByteArrayUtil.isBinary(b))
			return false;
		if (isAscii(len, b) && b.length < 240)
			return true;
		return false;
	}

	/**
	 * 该方法是对{@link #fixString(byte[])}的伪装
	 * 
	 * @param str
	 * @return
	 */
	public static String fixString(String str) {
		if (str == null)
			return null;
		return new String(fixString(str.getBytes()));
	}

	/**
	 * 将输入串中非GB2312字符去掉
	 * 
	 * @param str
	 *            待处理的字符串
	 * @return 处理后了字符串
	 */
	public static byte[] fixString(byte[] b) {

		if (b == null)
			return null;

		byte[] nb = new byte[b.length];
		int nbIdx = 0;

		for (int i = 0; i < b.length; i++) {
			byte by = b[i];
			if (by >= 32 && by <= 127) {
				nb[nbIdx] = by;
				nbIdx++;
			} else if (i + 1 < b.length && by >= -80 && by <= -9
					&& b[i + 1] >= -95 && b[i + 1] <= 1) {
				nb[nbIdx] = by;
				nb[nbIdx + 1] = b[i + 1];
				nbIdx += 2;
				i++;
			}
		}
		byte[] ret = new byte[nbIdx];
		System.arraycopy(nb, 0, ret, 0, nbIdx);
		return ret;
	}

	/**
	 * 将一个串按照GB2312的编码方式输出。输出的字符串中不可见的字符用.代替
	 * 
	 * @param str
	 * @return 处理后了字符串
	 */
	public static String vPrint(int len, byte[] b) {
		if (b == null)
			return null;
		if (len <= 0 || len > b.length)
			len = b.length;

		byte[] nb = new byte[b.length];
		int nbIdx = 0;

		for (int i = 0; i < len; i++) {
			byte by = b[i];
			if (by >= 32 && by <= 127) {
				nb[nbIdx] = by;
				nbIdx++;
			} else if (i + 1 < len && by >= -80 && by <= -9 && b[i + 1] >= -95
					&& b[i + 1] <= 1) {
				nb[nbIdx] = by;
				nb[nbIdx + 1] = b[i + 1];
				nbIdx += 2;
				i++;
			} else {
				nb[nbIdx] = '.';
				nbIdx++;
			}
		}
		byte[] ret = new byte[nbIdx];
		System.arraycopy(nb, 0, ret, 0, nbIdx);

		return new String(nb, 0, nbIdx);
	}

	/**
	 * 将一个串按照可视的编码方式输出
	 * <p>
	 * 直接返回结果串，如果输入的字符串为空，则输出NULL。
	 * 
	 * @param str
	 * @return 处理后了字符串
	 */
	public static String sPrint(long len, byte[] b) {
		if (b == null)
			return null;
		if (len <= 0 || len > b.length)
			len = b.length;

		byte[] nb = new byte[b.length * 3];
		int nbIdx = 0;

		for (int i = 0; i < len; i++) {
			byte by = b[i];
			if (by >= 32 && by <= 127) {
				nb[nbIdx] = by;
				nbIdx++;
			} else if (i + 1 < len && by >= -80 && by <= -9 && b[i + 1] >= -95
					&& b[i + 1] <= 1) {
				nb[nbIdx] = by;
				nb[nbIdx + 1] = b[i + 1];
				nbIdx += 2;
				i++;
			} else {
				byte[] tmpB = bytesToHexString(new byte[] { b[i] }).getBytes();
				nb[nbIdx] = tmpB[0];
				nbIdx++;
				nb[nbIdx] = tmpB[1];
				nbIdx++;
				nb[nbIdx] = tmpB[2];
				nbIdx++;
			}
		}
		byte[] ret = new byte[nbIdx];
		System.arraycopy(nb, 0, ret, 0, nbIdx);

		return new String(nb, 0, nbIdx);
	}
	public static String sPrint(byte[] b) {
		if (b == null)
			return "";
		return sPrint(b.length, b);
	}

	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		int high, low;
		String hv = null;
		for (int i = 0; i < src.length; i++) {
//			int v = src[i] & 0xFF;
//			String hv = Integer.toHexString(v);
            high = (src[i] >> 4) & 0x0F;
            low = src[i] & 0x0F;
            hv = base16[high] + base16[low];
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append("\\" + hv);
		}
		return stringBuilder.toString();
	}

	// /**
	// * 将输入的串按照BCD和ASCII两种方式显示出来。实际上更多的使用在Tstring。<font
	// color="red">暂时没有实现</font>
	// *
	// * @param wide
	// * @param word
	// * @param str
	// * @param line
	// * @return
	// */
	// public static int outString(long wide, long word, String str,
	// StringBuffer line) {
	// return 0;
	// }

	/**
	 * 返回RC_SUCCESS 表示转换成功，返回RC_FAILURE表示转换失败。未直接实现
	 * <p>
	 * 已经通过{@link #getLimitByGb2312(String, int)}和
	 * {@link #getLimitByUtf16(String, int)进行实现}
	 * 
	 * @param in_pszIn
	 *            待转换的字符串
	 * @param in_lMax
	 *            最大长度限制
	 * @param out_plGB2312
	 *            GB2312字符集的最大长度
	 * @param out_plUnicode
	 *            采用UNICODE 字符集时的最大长度
	 * @return
	 */
	public static long getLimit(String in_pszIn, long in_lMax,
                                long out_plGB2312, long out_plUnicode) {
		return 0;
	}

	/**
	 * 此函数主要在某些中文字的低位加上\转义符，比如弢字的低8位是|符，加上转义符\后，可以在数据库中进行处理。
	 * <p>
	 * <font color="red">此函数尽量不应该被调用，在Java的字符串处理中，不会对汉字编码中的'|'作为一个分隔的符号</font>
	 * 
	 * @param str
	 *            待处理的字符串
	 * @param ch
	 *            需要加上转义符的字符
	 * @return 返回处理后得到的串。
	 * 
	 * @deprecated
	 */
	public static String transKeyInChn(int len, String str, char ch) {
		if (str == null || str.length() == 0)
			return null;
		byte[] b = str.getBytes();
		byte[] nb = new byte[b.length * 2];
		int nbIdx = 0;
		if (len <= 0 || len > str.getBytes().length)
			len = str.length();
		str = new String(b, 0, len);
		char[] c = str.toCharArray();
		for (int i = 0; i < c.length; i++) {
			byte[] tmpB = new Character(c[i]).toString().getBytes();
			if (tmpB.length == 2 && (char) tmpB[1] == ch) {
				nb[nbIdx] = tmpB[0];
				nb[nbIdx + 1] = 92;
				nb[nbIdx + 2] = tmpB[1];
				nbIdx += 3;
			} else {
				for (int j = 0; j < tmpB.length; j++) {
					nb[nbIdx] = tmpB[j];
					nbIdx++;
				}
			}
		}
		return new String(nb, 0, nbIdx);
	}

	/**
	 * 将一个串按照GB2312的编码输出，避免在指定行宽位置出现汉字断行
	 * 
	 * @param wide
	 * @param str
	 * @return
	 */
	public static String visioPrint(int wide, byte[] b) {
		if (b == null) {
			return null;
		}
		b = fixString(b);// 不可见字符不输出
		if (0 >= wide)
			wide = 80;
		byte[] nb = new byte[b.length + b.length / wide + 1];
		int len = 0;
		int i = 0;
		for (i = 0; i < b.length; i += wide) {
			if (i + wide > b.length) {
				System.arraycopy(b, i, nb, len, b.length - i);
				len += b.length - i;
			} else {
				String tmpS = new String(b, i, wide);
				if (!isGB2312(tmpS.charAt(tmpS.length() - 1))) {
					System.arraycopy(b, i, nb, len, wide - 1);
					nb[len + wide - 1] = 32;
					i--;
				} else {
					System.arraycopy(b, i, nb, len, wide);
				}

				len += wide;
			}
		}
		return new String(nb, 0, len);
	}

	public static String visioPrint(int wide, String b) {
		return visioPrint(wide, b.getBytes());
	}

	/**
	 * 得到限制长度情况下字符集GB2312字符集的位置。
	 * 
	 * <p>
	 * 此方法是对原Tienon库的gb函数
	 * <code>long gbl_GetLimit(const char* in_pszIn, long in_lMax, long* out_plGB2312, long* out_plUnicode)</code>
	 * 的实现之一，另一个实现是{@link #getLimitByUtf16(String, int)}。
	 * 
	 * @param str
	 *            待转换的字符串
	 * @param max
	 *            最大长度限制
	 * @return
	 */
	public static int getLimitByGb2312(byte[] b, int max) {
		if (max <= 0)
			return 0;
		if (max >= b.length) {
			return b.length;
		}

		if (isAscii(b, 0, max)) {
			return max;
		} else
			return max - 1;
	}

	/**
	 * 得到限制长度情况下字符集UTF16字符集的位置。
	 * 
	 * <p>
	 * 此方法是对原Tienon库的gb函数
	 * <code>long gbl_GetLimit(const char* in_pszIn, long in_lMax, long* out_plGB2312, long* out_plUnicode)</code>
	 * 的实现之一，另一个实现是{@link #getLimitByGb2312(String, int)}。
	 * 
	 * @param str
	 *            待转换的字符串
	 * @param max
	 *            最大长度限制
	 * @return
	 */
	public static int getLimitByUtf16(byte[] b, int max) {
		String str = "";
		if (max <= 1)
			return 0;
		if (max < b.length) {
			str = new String(b, 0, max);
			if (!isGB2312(str.charAt(str.length() - 1))) {
				str = new String(b, 0, max - 1);
			}
		} else {
			str = new String(b);
		}
		int len = 0;
		try {
			len = str.getBytes("utf-16le").length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (len > max) {
			return len - 2;
		}
		return len;
	}

	/**
	 * 将输入的串按照BCD和ASCII两种方式显示出来。
	 * 
	 * <p>
	 * 如果字符串不能通过{@link #isAscii(String)}验证，以如下的形式输出内容：
	 * 
	 * <pre>
	 * String szTest = &quot;?阊侵薇戳礁鲈碌故? 世界杯场地轻松过关&quot;;
	 * StringBuffer sb = new StringBuffer();
	 * GbUtil.outString(80, 16, szTest, sb);
	 * System.out.println(sb.toString());
	 * </pre>
	 * 
	 * <pre>
	 * 00000000  3F AE D7 E3 D1 C7 D6 DE-B1 AD D3 AD C0 B4 C1 BD  ?.足亚洲杯迎来两
	 * 00000016  B8 F6 D4 C2 B5 B9 CA 3F-20 CA C0 BD E7 B1 AD     个月倒.? 世界杯
	 * 00000031  B3 A1 B5 D8 C7 E1 CB C9-B9 FD B9 D8              场地轻松过关
	 * </pre>
	 * 
	 * 即：“已输出字节数+16进制字节+文本输出” ，其中文本内容中不可见字符用“.”代替
	 * 
	 * <p>
	 * 如果字符串通过{@link #isAscii(String)}验证，以如下形式输出内容：
	 * 
	 * <pre>
	 * szTest = &quot;女足亚洲杯迎来两个月倒时,世界杯场地轻松过关 女足亚洲杯迎来两个月倒时,世界杯场地轻松过关&quot;;
	 * sb = new StringBuffer();
	 * GbUtil.outString(80, 16, szTest, sb);
	 * System.out.println(sb.toString());
	 * </pre>
	 * 
	 * <pre>
	 * 00000000  女足亚洲杯迎来两个月倒时,世界杯场地轻松过关 女足亚洲杯迎来两个月倒时,
	 * 00000069  世界杯场地轻松过关
	 * </pre>
	 * 
	 * 即：“已输出字节数+文本输出” ，其中文本内容中不可见字符用“.”代替
	 * 
	 * @param wide
	 *            每行的宽度（字节数）
	 * @param count
	 *            每行要显示的内容的字节数，如：每行要显示8个汉字，则count的值为16
	 * @param str
	 *            待输出的字符串
	 * @param line
	 *            处理后的字符串将被写到这个变量中（StringBuffer）
	 * @return 实际输出的行数
	 */
	public static String[] outString(int wide, int count, byte[] b) {
		if (wide < 40)
			wide = 80;
		if (count < 1 || count > (wide - 11) / 4) {
			count = (wide - 11) / 4;
		}
		ArrayList<String> list = new ArrayList<String>();
//		StringBuffer line = new StringBuffer();
		StringBuilder line = new StringBuilder();
		// int row = 0;
//		DecimalFormat df = new DecimalFormat("00000000");
//      df.setMaximumFractionDigits(0);
		StringBuilder df = new StringBuilder("00000000");
		int size = 0;
		String sSize = null;
		if ((!ByteArrayUtil.isBinary(b)) && isAscii(b)) {
			int len = wide - 10;
			for (int i = 0; i < b.length; i += len) {
				if (i + len > b.length) {
					len = b.length - i;
				}
				String s = new String(b, i, len);
				if (isGB2312(s.charAt(s.length() - 1))) {
//					line.append(df.format(size));
				    sSize = String.valueOf(size);
				    line.append(df.replace(df.length() - sSize.length(), df.length(), sSize));
					line.append("  ");
//					line.append(StringUtil.fill(Tienon.TEALI_LEFT, wide, s, " "));
					line.append(s);
					for(int j = s.length(); j < wide; j++)
					    line.append(" ");
					size += len;
				} else {
//					line.append(df.format(size));
                    sSize = String.valueOf(size);
                    line.append(df.replace(df.length() - sSize.length(), df.length(), sSize));
					line.append("  ");
//					line.append(StringUtil.fill(Tienon.TEALI_LEFT, wide - 10,
//							new String(b, i, len - 1), " "));
					line.append(new String(b, i, len - 1));
					for(int j = s.length(); j < wide - 10; j++)
					    line.append(" ");
					i--;
					size += len - 1;
				}
				list.add(line.toString());
				line.setLength(0);
			}
		} else {
			for (int i = 0; i < b.length; i += count) {
				String tmpS = "";
//              tmpS += df.format(size);// 打印已经输出字符串长度
                sSize = String.valueOf(size);
                tmpS += df.replace(df.length() - sSize.length(), df.length(), sSize);

				int len = count;
				if (i + count > b.length) {
					len = b.length - i;
				}

				String hex = "";
				byte[] tmpB = new byte[len];
				int high, low; 
				String hv = null;
				for (int j = 0; j < tmpB.length; j++) {
					high = (b[i + j] >> 4) & 0x0F;
				    low = b[i + j] & 0x0F;
				    hv = base16[high] + base16[low];
					if (j + 1 == count / 2)
					    hex += hv + "-";
					else
					    hex += hv + " ";
				}
//				hex = StringUtil.fill(Tienon.TEALI_LEFT, count * 3, hex, " ");
                for(int j = hex.length(); j < count * 3; j++)
                    hex += " ";
                
				boolean duan = false;// 是否汉字断行
				if (len == count && len + i + 1 <= b.length) {
					if (isGB2312(new byte[] { b[len + i - 2], b[len + i - 1] })) {
						;
					} else if (isGB2312(new byte[] { b[len + i - 1], b[len + i] })) {
						duan = true;
						hex = hex.substring(0, hex.length() - 3) + "   ";
					}
				}
				size += count;
				tmpS += ("  ");// 加上两个空格(32)
				tmpS += (hex);
				tmpS += (" ");
				byte tb[] = null;
				if (duan) {// 汉字
					tb = new byte[len - 1];
					System.arraycopy(b, i, tb, 0, len - 1);
					i--;
					size--;
				} else {
					tb = new byte[len];
					System.arraycopy(b, i, tb, 0, len);
				}
				tmpS += vPrint(0, tb);
//				line.append(StringUtil.fill(Tienon.TEALI_LEFT, 80, tmpS, " "));
                line.append(tmpS);
                for(int j = tmpS.length(); j < 80; j++)
                    line.append(" ");
				list.add(line.toString());
				line.setLength(0);
			}
		}
		String[] s = new String[list.size()];
//		for (int i = 0; i < s.length; i++) {
//			s[i] = list.get(i);
//		}
		return list.toArray(s);
	}
}