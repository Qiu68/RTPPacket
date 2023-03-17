package rtp;

import util.BaseDataConvertByte;

/**
 * @version 1.0
 * @Author:qiu
 * @Description rtp包解析
 * @Date 10:54 2023/3/17
 **/
public class RtpPacketDecode {
    //协议版本 2bit
    private int head_v;
    //填充标志 1bit
    private int head_p;
    //扩展标志 1bit
    private int head_x;
    //CSRC计数 4bit
    private int head_cc;

    //一个视频帧结束标志 1bit
    private int head_m;
    //载荷类型 7bit 表示传输什么类型的数据
    private int head_pt;

    //发送时间戳 32bit
    private int  head_timestamp;

    //数据包的序列号 32bit
    private int head_sequence_number;

    //同步源 32bit
    private int head_ssrc;

    //贡献源 32bit
    private int head_csrc;

    //实际载荷长度 16bit
    private short length;

    //rtp实际载荷
    private byte[] body;

    //rtp头部
    private byte[] head_bytes;

    public boolean decode(byte[] rtpPacketBytes) {
        if (rtpPacketBytes == null || rtpPacketBytes.length <= 0) {
            return false;
        }
        byte head_byte_1 = rtpPacketBytes[0];
        //1. head_byte & ob11000000 的结果为int 可以解决byte有符号 导致最后结果不正确
        //2. 转换成int类型后 右移6位 就可以得到高位 2个bit的值
        head_v = (head_byte_1 & 0b11000000) >> 6;
        head_p = (head_byte_1 & 0b00100000) >> 5;
        head_x = (head_byte_1 & 0b00010000) >> 4;
        //这个不能右移 (⊙o⊙)…
        head_cc = head_byte_1 & 0b00001111;


        byte head_byte_2 = rtpPacketBytes[1];
        //右移7位 得到最高位的值
        head_m = (head_byte_2 & 0b10000000) >> 7;
        head_pt = (head_byte_2 & 0b01111111);

        byte[] timestampBytes = new byte[4];
        System.arraycopy(rtpPacketBytes,2,timestampBytes,0,timestampBytes.length);
        head_timestamp = BaseDataConvertByte.bytesToInt(timestampBytes);

        byte[] sequenceNumberBytes = new byte[4];
        System.arraycopy(rtpPacketBytes,6,sequenceNumberBytes,0,sequenceNumberBytes.length);
        head_sequence_number = BaseDataConvertByte.bytesToInt(sequenceNumberBytes);

        byte[] ssrcBytes = new byte[4];
        System.arraycopy(rtpPacketBytes,10,ssrcBytes,0,ssrcBytes.length);
        head_ssrc = BaseDataConvertByte.bytesToInt(ssrcBytes);

        byte[] csrcBytes = new byte[4];
        System.arraycopy(rtpPacketBytes,14,csrcBytes,0,csrcBytes.length);
        head_csrc = BaseDataConvertByte.bytesToInt(csrcBytes);

        byte[] length = new byte[2];
        System.arraycopy(rtpPacketBytes,18,length,0,length.length);

        this.length = BaseDataConvertByte.bytesToShort(length);
        byte [] bytes = new byte[this.length];
        body = new byte[this.length];

        System.arraycopy(rtpPacketBytes,20,bytes,0,this.length);
        System.arraycopy(bytes,0,body,0,this.length);


        return true;
    }

    public int getHead_v() {
        return head_v;
    }

    public int getHead_p() {
        return head_p;
    }

    public int getHead_x() {
        return head_x;
    }

    public int getHead_cc() {
        return head_cc;
    }

    public int getHead_m() {
        return head_m;
    }

    public int getHead_pt() {
        return head_pt;
    }

    public int getHead_timestamp() {
        return head_timestamp;
    }

    public int getHead_sequence_number() {
        return head_sequence_number;
    }

    public int getHead_ssrc() {
        return head_ssrc;
    }

    public int getHead_csrc() {
        return head_csrc;
    }

    public short getLength() {
        return length;
    }

    public byte[] getBody() {
        return body;
    }
}
