package com.cxf.moudule_common.util.encode;

public class ByteArrayUtil {
	/**
     * 判断 byte-array 是否为二进制数据
     * @param b
     * @return 如果 b 中包含二进制数据，返回 true , 否则 b是字符串，返回false
     */
    public static boolean isBinary(byte[] b, int off, int len) {
        for (int i=off; i < len; i++) {
            if (b[i] == 0) // 根据buf中是否包含 0 作为判断依据
                return true;            
        }
        return false;
    }
    public static boolean isBinary(byte[] b) {
        for (byte x : b) {
            if (x == 0) // 根据buf中是否包含 0 作为判断依据
                return true;
        }
        return false;
    }
}
