/**
 * copyright(c) 2014 FuJian start Information Corp.Ltd
 *
 * @File name:  BCDUtil.java
 * @Version : 1.0
 * @Create on:  2014-01-18
 * @Author   :  Administrator
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 *
 */
package com.cxf.moudule_common.format;


import com.cxf.moudule_common.Log.Loger;

/**
 * 功能描述：这个类封装了进行BCD压缩编码的相关操作
 * @author Administrator
 *
 */
public class BCDUtil {
	
	private static final Loger log = Loger.getLogger(BCDUtil.class);

	/**
	 * 功能描述：检查其数据是否能进行BCD
	 * 
	 * @param val 
	 * 			待检查的数据
	 * @return 都在 0x00 ~ 0x0F, 0x30 ~ 0x39的范围中，则返回true， 否则false
	 */
	public static boolean canbeBCD(byte [] val) {
		for(int i = 0; i < val.length; i++) {
			boolean flag1 = (val[i] > -1 && val[i] < 0x10);
			boolean flag2 = (val[i] > 0x2F && val[i] < 0x3A);
			boolean flag3 = (val[i] > 0x40 && val[i] < 0x47);
			boolean flag4 = (val[i] > 0x60 && val[i] < 0x67);
			if(!(flag1 || flag2 || flag3 || flag4)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 功能描述：对给定的数据进行BCD转换，如果长度为奇数，则在最前端补零
	 * @param val 
	 * 			待转换数据，需满足canbeBCD()。
	 * @return 以字节数组的形式返回压缩后的内容
	 */
	public static byte[] doBCD(byte[] val) {
		if(val == null) {	// 检查参数是否为null
			log.info("不能进行BCD压缩, 传入的参数为null");
			return null;
		}
		if( !canbeBCD(val) ) {  // 检查参数的内容是否合法
			log.info("不能进行BCD, 传入的参数非法：含有 不在[0x00~0x0F],[0x30 ~ 0x39], [0x41 ~ 0x46],[0x61~0x66]的范围中的数据");
			return val;
		}
		for(int i = 0;i < val.length; i++){
			if(val[i] > -1 && val[i] < 0x0A){		//将不可显示字符转化为可显示字符
				val[i] = (byte)(val[i] + 0x30);
			}
			if(val[i] > 0x09 && val[i] < 0x10){		//将不可显示字符转化为可显示字符
				val[i] = (byte)(val[i] + 0x37);
			}
		}
		byte[] bcdData = null;
		String bcdStr = new String(val);
		if(bcdStr.length() % 2 == 0){
			bcdData = strToBcd(bcdStr);
		}else{
			bcdStr = "0" + bcdStr;
			bcdData = strToBcd(bcdStr);	//长度为奇数在最前端补零
		}
		return bcdData;
	}
	/**
	 * 功能描述：这个函数实现了左靠压缩功能，如果长度为奇数，在内容末尾补零
	 * @param val 
	 * 			需要压缩的内容，类型为byte[]
	 * @return 以字节数组的方式返回压缩后的内容
	 */
	public static byte[] doBCDLEFT(byte val[]) {
		if(val == null) {	// 检查参数是否为null
			log.info("不能进行BCD压缩, 传入的参数为null");
			return null;
		}
		if( !canbeBCD(val) ) {  // 检查参数的内容是否合法
			log.info("不能进行BCD, 传入的参数非法：含有 不在[0x00~0x0F],[0x30 ~ 0x39], [0x41 ~ 0x46],[0x61~0x66]的范围中的数据");
			return val;
		}
		for(int i = 0;i < val.length; i++){
			if(val[i] > -1 && val[i] < 0x0A){		//将不可显示字符转化为可显示字符
				val[i] = (byte)(val[i] + 0x30);
			}
			if(val[i] > 0x09 && val[i] < 0x10){		//将不可显示字符转化为可显示字符
				val[i] = (byte)(val[i] + 0x37);
			}
		}
		byte[] bcdData = null;
		String bcdStr = new String(val);
		if(bcdStr.length() % 2 == 0){
			bcdData = strToBcd(bcdStr);
		}
		else{
			bcdStr = bcdStr + "0";
			bcdData = strToBcd(bcdStr);	//长度为奇数在末尾补零
		}
		return bcdData;
	}
	
//    /** *//**
//     * @函数功能: BCD码转为10进制串(阿拉伯数据)
//     * @输入参数: BCD码
//     * @输出结果: 10进制串
//     */
//	public static String bcd2Str(byte[] bytes) {
//		StringBuffer temp = new StringBuffer(bytes.length * 2);
//		for (int i = 0; i < bytes.length; i++) {
//			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
//			temp.append((byte) (bytes[i] & 0x0f));
//		}
//		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp.toString().substring(1) : temp.toString();
//	}
	
	/**
	 * BCD码转为10进制串(阿拉伯数据)
	 * @param bcds 
	 * 			BCD码 
	 * @return 10进制串
	 */
	public static String bcdToStr(byte[] bcds) {
		char[] ascii = "0123456789abcdef".toCharArray();
		byte[] temp = new byte[bcds.length * 2];
		for (int i = 0; i < bcds.length; i++) {
			temp[i * 2] = (byte) ((bcds[i] >> 4) & 0x0f);
			temp[i * 2 + 1] = (byte) (bcds[i] & 0x0f);
		}
		StringBuffer res = new StringBuffer();

		for (int i = 0; i < temp.length; i++) {
			res.append(ascii[temp[i]]);
		}
		return res.toString().toUpperCase();
	}
	
	/**
	 * 功能描述：10进制字符串转化为BCD压缩码，例如字符串str = "12345678",压缩之后的字节数组内容为{0x12,0x34,0x56,0x78}；
	 * @param  asc 
	 * 			需要进行压缩的ASCII码表示的字符串
	 * @return 以字节数组返回压缩后的内容
	 */
	public static byte[] strToBcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length()/2; p++) {
			if ( (abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} 
			else if ( (abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} 
			else {
				j = abt[2 * p] - 'A' + 0x0a;
			}
			if ( (abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			}
			else if ( (abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			}
			else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}
			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}
	
	/**
	 * ASCII byte 转 BCD byte
	 * @param asc 
	 * 			ASCII byte 值
	 * @return  返回BCD byte值
	 */
    private static byte ascToBcd(byte asc) {  
        byte bcd;  
  
        if ((asc >= '0') && (asc <= '9')) {
        	bcd = (byte) (asc - '0');  
        }
        else if ((asc >= 'A') && (asc <= 'F')) {
        	bcd = (byte) (asc - 'A' + 10);  
        }
        else if ((asc >= 'a') && (asc <= 'f')) {
        	bcd = (byte) (asc - 'a' + 10);  
        }
        else {
        	bcd = (byte) (asc - 48);  
        }
        return bcd;  
    }  
    
    /**
     * ASCII byte数组转BCD byte数组
     * @param ascii 
     * 			byte数组
     * @param asc_len 
     * 			数组长度
     * @return bcd数组
     */
    public static byte[] ascToBcd(byte[] ascii, int asc_len) {  
        byte[] bcd = new byte[asc_len / 2];  
        int j = 0;  
        for (int i = 0; i < (asc_len + 1) / 2; i++) {  
            bcd[i] = ascToBcd(ascii[j++]);  
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : ascToBcd(ascii[j++])) + (bcd[i] << 4));  
        }  
        return bcd;  
    }  

}