package com.serialport.serialport.util;

import java.io.UnsupportedEncodingException;

/**
 * 进制转换
 *
 */
public class RadixConvertUtil {
    /**
     * 10进制转16进制
     * @param dec
     * @return
     */
    public static String decToHex(Integer dec) {
        String hex = Integer.toHexString(dec);
        return hex;
    }

    /**
     * 将十六进制字符串转换成gbk编码
     * @param str
     * @return
     * @throws Exception
     */
    public static String hexToGbk(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < bytes.length; i ++){
            byte high = Byte.parseByte(str.substring(i * 2, i * 2 + 1), 16);
            byte low = Byte.parseByte(str.substring(i * 2 + 1, i * 2 + 2), 16);
            bytes[i] = (byte) (high << 4 | low);
        }
        try {
            String result = new String(bytes, "gbk");
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将gbk编码转成十六进制字符串
     * @param str
     * @return
     */
    public static String gbkToHex(String str) {
        byte[] bytes;
        try {
            bytes = str.getBytes("GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        // 一个byte为8位，可用两个十六进制位标识
        char[] buf = new char[bytes.length * 2];
        int a = 0;
        int index = 0;
        for(byte b : bytes) { // 使用除与取余进行转换
            if(b < 0) {
                a = 256 + b;
            } else {
                a = b;
            }
            buf[index++] = HEX_CHAR[a / 16];
            buf[index++] = HEX_CHAR[a % 16];
        }
        return new String(buf);
    }

}
