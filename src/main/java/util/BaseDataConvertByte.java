package util;

/**
 * @version 1.0
 * @Author:qiu
 * @Description
 * @Date 11:50 2023/3/2
 **/
public class BaseDataConvertByte {

    public static byte[] shortToBytes(int value ) {
        byte[] src = new byte[2];
        src[0] = (byte) (value & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        return src;
    }

    public static short bytesToShort(byte[] src) {
        short value;
        value = (short) ((src[0] & 0xFF)
                | ((src[1] & 0xFF) << 8));
        return value;
    }

    public static byte[] intToBytes(int value ) {
        byte[] src = new byte[4];
        src[0] = (byte) (value & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[3] = (byte) ((value >> 24) & 0xFF);
        return src;
    }


    public static int bytesToInt(byte[] src) {
        int value;
        value = (int) ((src[0] & 0xFF)
                | ((src[1] & 0xFF) << 8)
                | ((src[2] & 0xFF) << 16)
                | ((src[3] & 0xFF) << 24));
        return value;
    }


    public static byte[] longToBytes(long values) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte) ((values >> offset) & 0xff);
        }
        return buffer;
    }

    public static long bytesToLong(byte[] buffer) {
        long  values = 0;
        for (int i = 0; i < 8; i++) {
            values <<= 8; values|= (buffer[i] & 0xff);
        }
        return values;
    }


}
