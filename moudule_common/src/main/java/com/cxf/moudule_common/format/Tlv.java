package com.cxf.moudule_common.format;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;

public class Tlv {
	
	private static final String TAG = "Tlv";

	public static int[] parseLen(byte[] data, int offset) {
		int[] ret = new int[2];
		if ((data[offset] & 0x80) == 0) {
			ret[0] = data[offset];
			ret[1] = 1;
			return ret;
		}

		int bytes = data[offset] & 0x7F;

		++offset;
	
		int len = 0;
		int i = 0;
		while (i < bytes) {
			len <<= 8;
			len += (data[offset] & 0xFF);

			++i;
			++offset;
		}

		ret[0] = len;
		ret[1] = (bytes + 1);

		return ret;
	}

	public static byte[] genLen(int len) {
		if (len <= 127) {
			byte[] ret = new byte[1];
			ret[0] = (byte) len;
			return ret;
		}

		int tmp = len;
		int b = 0;
		while (tmp != 0) {
			++b;
			tmp >>= 8;
		}

		byte[] ret = new byte[b + 1];
		ret[0] = (byte) (128 + b);
		byte[] lenBytes = new byte[4];
		Convert.int2ByteArray(len, lenBytes, 0);
		System.arraycopy(lenBytes, 4 - b, ret, 1, b);

		return ret;
	}

	public static ArrayList<TlvItem> parse(byte[] data) {
		int offset = 0;
		ArrayList<TlvItem> list = new ArrayList<TlvItem>();
		while (offset < data.length) {
			TlvItem item = new TlvItem();

			int len = item.setTag(data, offset);
			offset += len;

			int[] lenOffset = parseLen(data, offset);
			if ((lenOffset[1] > 3) || (lenOffset[1] < 0)) {
				return null;
			}
			
			offset += lenOffset[1];
			item.value = new byte[lenOffset[0]];
			System.arraycopy(data, offset, item.value, 0, lenOffset[0]);
			offset += lenOffset[0];

			list.add(item);
		}

		return list;
	}

	public static byte[] serialize(TlvItem item) {
		ByteBuffer bb = ByteBuffer.allocate(1024);
		bb.clear();
		bb.order(ByteOrder.BIG_ENDIAN);

		bb.put(item.tag);

		byte[] len = genLen(item.value.length);
		bb.put(len);
		bb.put(item.value);

		bb.flip();
		byte[] ret = new byte[bb.limit()];
		bb.get(ret);
		return ret;
	}

	public static byte[] serialize(ArrayList<TlvItem> list) {
		ByteBuffer bb = ByteBuffer.allocate(1024);
		bb.clear();
		bb.order(ByteOrder.BIG_ENDIAN);

		for (int i = 0; i < list.size(); ++i) {
			TlvItem item = (TlvItem) list.get(i);
			bb.put(item.tag);

			byte[] len = genLen(item.value.length);
			bb.put(len);
			bb.put(item.value);
		}

		bb.flip();
		byte[] ret = new byte[bb.limit()];
		bb.get(ret);
		return ret;
	}

	public static TlvItem getItem(byte[] tag, ArrayList<TlvItem> list) {
		if ((tag == null) || (list == null)) {
			return null;
		}

		for (int i = 0; i < list.size(); ++i) {
			TlvItem tlv = new TlvItem();

			tlv = (TlvItem) list.get(i);
			if (Arrays.equals(tlv.tag, tag)) {
				return tlv;
			}
		}

		return null;
	}

	public static int getItemIndex(byte[] tag, ArrayList<TlvItem> list) {
		if (list == null) {
			return -1;
		}

		for (int i = 0; i < list.size(); ++i) {
			TlvItem tlv = new TlvItem();

			tlv = (TlvItem) list.get(i);
			if (Arrays.equals(tlv.tag, tag)) {
				return i;
			}
		}
		return -1;
	}

	public static int updateTagValue(byte[] tag, ArrayList<TlvItem> list, byte[] value) {
		
		if ((tag == null) || (list == null) || (value == null)) {
			return -1;
		}
		
		TlvItem tlv = new TlvItem();
		tlv = getItem(tag, list);
		list.remove(getItem(tag, list));
		tlv.setValue(value);
		list.add(tlv);
		return 0;
	}

	public static final void removeTag(byte[] tag, ArrayList<TlvItem> list) {
		if ((tag == null) || (list == null)) {
			return;
		}

		for (int i = 0; i < list.size(); ++i) {
			TlvItem tlv = new TlvItem();

			tlv = (TlvItem) list.get(i);
			if (Arrays.equals(tlv.tag, tag)) {
				list.remove(i);
				return;
			}
		}
	}

	public static byte[] getTagValue(byte[] tag, ArrayList<TlvItem> list) {
		if (list == null) {
			return null;
		}

		for (int i = 0; i < list.size(); ++i) {
			TlvItem tlv = new TlvItem();
			tlv = (TlvItem) list.get(i);
			if (Arrays.equals(tlv.tag, tag)) {
				return tlv.value;
			}
		}
		return null;
	}

	public static class TlvItem {
		private byte[] tag;
		private byte[] value;

		/**
		 * 设置tag：tag长度两个字节或一个字节
		 * @param data  tlv数据
		 * @param offset 当前tag位置
		 * @return tag长度
		 */
		public int setTag(byte[] data, int offset) {
			int len = 0;
			if (data[offset] == 0) {
				return 0;
			}

			//解析TAG长度
			if ((data[offset] & 0x1F) == 31) { 
				if ((data[(offset + 1)] & 0x80) == 128) {
					len = 3;
				}
				else {
					len = 2;
				}
			}
			else {
				len = 1;
			}

			this.tag = new byte[len];
			System.arraycopy(data, offset, this.tag, 0, len);
			return len;
		}

		public int setTag(byte[] tag) {
			if ((tag == null) || (tag.length == 0)) {
				return 0;
			}
			this.tag = new byte[tag.length];
			System.arraycopy(tag, 0, this.tag, 0, this.tag.length);
			return this.tag.length;
		}

		public void setValue(byte value) {
			this.value = new byte[1];
			this.value[0] = value;
		}

		public void setValue(byte[] value) {
			
			if ((value == null) || (value.length == 0)) {
				return;
			}

			this.value = new byte[value.length];
			System.arraycopy(value, 0, this.value, 0, value.length);
		}

		public byte[] getValue() {
			return this.value;
		}

		public byte[] getTag() {
			return this.tag;
		}
	}
}