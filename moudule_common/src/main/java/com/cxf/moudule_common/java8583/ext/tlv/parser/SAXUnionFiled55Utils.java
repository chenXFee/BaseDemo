package com.cxf.moudule_common.java8583.ext.tlv.parser;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SAXUnionFiled55Utils
{

    /**
     * 银联55域
     *
     * 本域将根据不同的交易种类包含不同的子域。银联处理中心仅在受理方和发卡方之间传递这些适用于IC卡交易的特有数据，而不对它们进行任何修改和处理。
     * 为适应该子域需要不断变化的情况
     * ，本域采用TLV（tag-length-value）的表示方式，即每个子域由tag标签(T)，子域取值的长度(L)和子域取值(V)构成。
     * tag标签的属性为bit
     * ，由16进制表示，占1～2个字节长度。例如，"9F33"为一个占用两个字节的tag标签。而"95"为一个占用一个字节的tag标签
     * 。若tag标签的第一个字节
     * （注：字节排序方向为从左往右数，第一个字节即为最左边的字节。bit排序规则同理。）的后五个bit为"11111"，则说明该tag占两个字节
     * ，例如"9F33"；否则占一个字节，例如"95"。 子域长度（即L本身）的属性也为bit，占1～3个字节长度。具体编码规则如下： a)
     * 当L字段最左边字节的最左bit位（即bit8）为0，表示该L字段占一个字节，它的后续7个bit位（即bit7～bit1）表示子域取值的长度，
     * 采用二进制数表示子域取值长度的十进制数
     * 。例如，某个域取值占3个字节，那么其子域取值长度表示为"00000011"。所以，若子域取值的长度在1～127
     * 字节之间，那么该L字段本身仅占一个字节。 b)
     * 当L字段最左边字节的最左bit位（即bit8）为1，表示该L字段不止占一个字节，那么它到底占几个字节由该最左字节的后续7个bit位
     * （即bit7～bit1）的十进制取值表示。例如，若最左字节为10000010，表示L字段除该字节外，后面还有两个字节。其后续字节
     * 的十进制取值表示子域取值的长度。例如，若L字段为"1000 0001 1111 1111"，表示该子域取值占255个字节。
     * 所以，若子域取值的长度在128～255字节之间，那么该L字段本身需占两个字节
     *
     * @return tlv list
     */
    public static List<TLV> saxUnionField55_2List(String hexfiled55)
    {

        if (null == hexfiled55)
        {
            throw new IllegalArgumentException("55域的值不能为空!");
        }

        return builderTLV(hexfiled55);
    }

    private static List<TLV> builderTLV(String hexString)
    {
        List<TLV> tlvs = new ArrayList<TLV>();

        int position = 0;
        while (position != hexString.length())
        {
            String _hexTag = getUnionTag(hexString, position);
            position += _hexTag.length();

            LPositon l_position = getUnionLAndPosition(hexString, position);
            int _vl = l_position.get_vL();

            position = l_position.get_position();

            String _value = hexString.substring(position, position + _vl * 2);

            position = position + _value.length();

            tlvs.add(new TLV(_hexTag, _vl, _value));
        }
        return tlvs;
    }

    /**
     * 银联55域
     *
     * 本域将根据不同的交易种类包含不同的子域。银联处理中心仅在受理方和发卡方之间传递这些适用于IC卡交易的特有数据，而不对它们进行任何修改和处理。
     * 为适应该子域需要不断变化的情况
     * ，本域采用TLV（tag-length-value）的表示方式，即每个子域由tag标签(T)，子域取值的长度(L)和子域取值(V)构成。
     * tag标签的属性为bit
     * ，由16进制表示，占1～2个字节长度。例如，"9F33"为一个占用两个字节的tag标签。而"95"为一个占用一个字节的tag标签
     * 。若tag标签的第一个字节
     * （注：字节排序方向为从左往右数，第一个字节即为最左边的字节。bit排序规则同理。）的后五个bit为"11111"，则说明该tag占两个字节
     * ，例如"9F33"；否则占一个字节，例如"95"。 子域长度（即L本身）的属性也为bit，占1～3个字节长度。具体编码规则如下： a)
     * 当L字段最左边字节的最左bit位（即bit8）为0，表示该L字段占一个字节，它的后续7个bit位（即bit7～bit1）表示子域取值的长度，
     * 采用二进制数表示子域取值长度的十进制数
     * 。例如，某个域取值占3个字节，那么其子域取值长度表示为"00000011"。所以，若子域取值的长度在1～127
     * 字节之间，那么该L字段本身仅占一个字节。 b)
     * 当L字段最左边字节的最左bit位（即bit8）为1，表示该L字段不止占一个字节，那么它到底占几个字节由该最左字节的后续7个bit位
     * （即bit7～bit1）的十进制取值表示。例如，若最左字节为10000010，表示L字段除该字节外，后面还有两个字节。其后续字节
     * 的十进制取值表示子域取值的长度。例如，若L字段为"1000 0001 1111 1111"，表示该子域取值占255个字节。
     * 所以，若子域取值的长度在128～255字节之间，那么该L字段本身需占两个字节
     *
     * @return tlv map
     */
    public static Map<String, TLV> saxUnionField55_2Map(String hexfiled55)
    {

        if (null == hexfiled55)
        {
            throw new IllegalArgumentException("55域的值不能为空!");
        }

        return builderKeyAndTLV(hexfiled55);
    }

    public static Map<String, TLV> builderKeyAndTLV(String hexString)
    {

        Map<String, TLV> tlvs = new HashMap<String, TLV>();

        int position = 0;
        while (position != hexString.length())
        {
            String _hexTag = getUnionTag(hexString, position);
            position += _hexTag.length();
            LPositon l_position = getUnionLAndPosition(hexString, position);
            int _vl = l_position.get_vL();
            position = l_position.get_position();
            String _value = hexString.substring(position, position + _vl * 2);
            position = position + _value.length();
            tlvs.put(_hexTag, new TLV(_hexTag, _vl, _value));
        }
        return tlvs;
    }

    /**
     * 返回最后的Value的长度
     *
     * @param hexString
     * @param position
     * @return
     */
    private static LPositon getUnionLAndPosition(String hexString, int position)
    {

        String firstByteString = hexString.substring(position, position + 2);
        int i = Integer.parseInt(firstByteString, 16);
        String hexLength = "";

        if (((i >>> 7) & 1) == 0)
        {
            hexLength = hexString.substring(position, position + 2);
            position = position + 2;

        } else
        {
            // 当最左侧的bit位为1的时候，取得后7bit的值，
            int _L_Len = i & 127;
            position = position + 2;
            hexLength = hexString.substring(position, position + _L_Len * 2);
            // position表示第一个字节，后面的表示有多少个字节来表示后面的Value值
            position = position + _L_Len * 2;

        }
        return new LPositon(Integer.parseInt(hexLength, 16), position);

    }

    private static String getUnionTag(String hexString, int position)
    {
        String firstByte = hexString.substring(position, position + 2);
        int i = Integer.parseInt(firstByte, 16);
        if ((i & 0x1f) == 0x1f)
        {
            return hexString.substring(position, position + 4);

        } else
        {
            return hexString.substring(position, position + 2);
        }

    }

    static class LPositon
    {
        private int _vL;
        private int _position;

        public LPositon(int _vL, int position)
        {
            this._vL = _vL;
            this._position = position;
        }

        public int get_vL()
        {
            return _vL;
        }

        public void set_vL(int _vL)
        {
            this._vL = _vL;
        }

        public int get_position()
        {
            return _position;
        }

        public void set_position(int _position)
        {
            this._position = _position;
        }

    }


    public static List<TLV> field55_toList(String field55){
        return SAXUnionFiled55Utils.saxUnionField55_2List(field55);
    }

}
