package com.cxf.moudule_common.format;
/**
 *  Copyright 2014, Fujian start Information Co.,Ltd.  All right reserved.
 *  THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF  FUJIAN start PAY CO.,
 *  LTD.  THE CONTENTS OF THIS FILE MAY NOT BE DISCLOSED TO THIRD
 *  PARTIES, COPIED OR DUPLICATED IN ANY FORM, IN WHOLE OR IN PART,
 *  WITHOUT THE PRIOR WRITTEN PERMISSION OF  FUJIAN start PAY CO., LTD.
 *
 *  TLV函数
 *  Edit History:
 *
 *    2014/09/11 - Created by Xrh.
 *    
 *  Edit History：
 *  
 *   2014/10/22 - Modified by Xrh.
 *   L字段长度改为无符号整型
 */




import com.cxf.moudule_common.Log.Loger;

import java.util.HashMap;
import java.util.Map;

/**
 * tlv工具类
 *
 */
public class TlvUtil {
	
	private static final Loger log = Loger.getLogger(TlvUtil.class);
	
	/**
	 * tlv格式字符串解析成MAP对象
	 * @param tlv tlv格式数据
	 * @return 转换后的MAP对象的tlv数据
	 */
	public static Map<String, String> tlvToMap(String tlv){
		return tlvToMap(HexUtil.hexStringToByte(tlv));
	}
	
	/**
	 * 若tag标签的第一个字节后四个bit为“1111”,则说明该tag占两个字节
	 * 例如“9F33”;否则占一个字节，例如“95”
	 * @param tlv tlv格式数据
	 * @return 转换后的MAP对象的tlv数据
	 */
	public static Map<String, String> tlvToMap(byte[] tlv){
		Map<String, String> map = new HashMap<String, String>();
		int index = 0;
		while(index < tlv.length){
			if( (tlv[index]&0x1F)== 0x1F){ //tag双字节
				byte[] tag = new byte[2];
				System.arraycopy(tlv, index, tag, 0, 2);
				index+=2;
				
				int length = 0;
				if(tlv[index]>>7 == 0){	 //表示该L字段占一个字节
					length = tlv[index];	//value字段长度
					index++;
				}
				else { //表示该L字段不止占一个字节
					
					int lenlen = tlv[index]&0x7F; //获取该L字段占字节长度
					index++;
					
					for (int i = 0; i < lenlen; i++) {
						length =length<<8;
						length += tlv[index]&0xff;  //value字段长度 &ff转为无符号整型
						index++;
					}
				}
				
				byte[] value =  new byte[length];
				System.arraycopy(tlv, index, value, 0, length);
				index += length;
				map.put(BCDUtil.bcdToStr(tag), BCDUtil.bcdToStr(value));
			}
			else{ //tag单字节
				byte[] tag = new byte[1];
				System.arraycopy(tlv, index, tag,0 , 1);
				index++;

				int length = 0;
				if(tlv[index]>>7 == 0){	//表示该L字段占一个字节
					length = tlv[index]; //value字段长度
					index++;
				}
				else { //表示该L字段不止占一个字节

					int lenlen = tlv[index]&0x7F; //获取该L字段占字节长度
					index++;

					for (int i = 0; i < lenlen; i++) {
						length =length<<8;
						length += tlv[index]&0xff;  //value字段长度&ff转为无符号整型
						index++;
					}
				}
				byte[] value =  new byte[length];
				System.arraycopy(tlv, index, value, 0, length);
				index += length;
				map.put(BCDUtil.bcdToStr(tag), BCDUtil.bcdToStr(value));
			}
		}
		
		return map;
	}
	
}