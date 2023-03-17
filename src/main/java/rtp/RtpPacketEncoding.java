package rtp;

import util.BaseDataConvertByte;

/**
 * @version 1.0
 * @Author:qiu
 * @Description 组装rtp包
 * @Date 17:33 2023/3/16
 **/
public class RtpPacketEncoding {

    //协议版本 2bit
    private byte head_v;
    //填充标志 1bit
    private byte head_p;
    //扩展标志 1bit
    private byte head_x;
    //CSRC计数 4bit
    private byte head_cc;

    //一个视频帧结束标志 1bit
    private byte head_m;
    //载荷类型 7bit 表示传输什么类型的数据
    private byte head_pt;

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
    //实际载荷
    private byte[] body;

    private byte[] head_bytes;


    //实际载荷长度

    public RtpPacketEncoding() {
    }

    public RtpPacketEncoding(byte head_v, byte head_p, byte head_x, byte head_cc, byte head_m, byte head_pt, int head_timestamp, int head_sequence_number, int head_ssrc, int head_csrc) {
        this.head_v = head_v;
        this.head_p = head_p;
        this.head_x = head_x;
        this.head_cc = head_cc;
        this.head_m = head_m;
        this.head_pt = head_pt;
        this.head_timestamp = head_timestamp;
        this.head_sequence_number = head_sequence_number;
        this.head_ssrc = head_ssrc;
        this.head_csrc = head_csrc;
    }

    public byte[] headEncoding(int timestamp,int sequence_number,int ssrc,int csrc){

        head_timestamp = timestamp;
        head_sequence_number = sequence_number;
        head_ssrc = ssrc;
        head_csrc = csrc;

        head_v = (byte) 0B11000000;
        head_p = 0B00100000;
        head_x = 0B00010000;
        head_cc = 0B00001111;

        head_m = (byte) 0B10000000;
        head_pt = 0B01111111;




        byte head_byte_1 = 0;
        //高2位写入 2bit 版本信息 默认version 2
        head_byte_1 = (byte) (head_byte_1 |  head_v);
        //高3位写入 1bit 填充信息 默认有填充信息
        head_byte_1 = (byte) (head_byte_1 | head_p);
        //高4位写入 1bit 扩展标志 默认有扩展
        head_byte_1 = (byte) (head_byte_1 | head_x);
        //高5位写入 4bit CSRC计数 4bit
        head_byte_1 = (byte) (head_byte_1 | head_cc);

        byte head_byte_2 = 0;
        //高1位写入 1bit 帧是否结束 默认version 1
        head_byte_2 = (byte) (head_byte_1 | head_m);
        //高2位写入 7bit 载荷类型
        head_byte_2 = (byte) (head_byte_1 | head_pt);

        System.out.println(head_byte_1);
        System.out.println(head_byte_2);

        byte[] timestampBytes = BaseDataConvertByte.intToBytes(head_timestamp);
        byte[] sequenceNumberBytes = BaseDataConvertByte.intToBytes(head_sequence_number);
        byte[] ssrcBytes = BaseDataConvertByte.intToBytes(head_ssrc);
        byte[] csrcBytes = BaseDataConvertByte.intToBytes(head_csrc);

        byte[] headBytes = new byte[18];
        headBytes[0] = head_byte_1;
        headBytes[1] = head_byte_2;

        System.arraycopy(timestampBytes,0,headBytes,2,timestampBytes.length);
        System.arraycopy(sequenceNumberBytes,0,headBytes,6,sequenceNumberBytes.length);
        System.arraycopy(ssrcBytes,0,headBytes,10,ssrcBytes.length);
        System.arraycopy(csrcBytes,0,headBytes,14,csrcBytes.length);
        head_bytes = headBytes;
        return headBytes;
    }

    public void setRtpPayload(byte[] data,short length){
        this.body = data;
        this.length = length;
    }

    public byte[] getRtpPacket(){
        //20的头字节 暂时写死
        byte[] rtpPacketBytes = new byte[20 + length];
        byte[] lengthBytes = BaseDataConvertByte.shortToBytes(length);
        System.arraycopy(head_bytes,0,rtpPacketBytes,0,head_bytes.length);
        System.arraycopy(lengthBytes,0,rtpPacketBytes,18,lengthBytes.length);
        System.arraycopy(body,0,rtpPacketBytes,head_bytes.length + lengthBytes.length,body.length);

        return rtpPacketBytes;
    }

    public boolean checkHeadParams(){
        if (head_v <= 0){
            return false;
        }
        if (head_p <= 0){
            return false;
        }
        if (head_x <= 0){
            return false;
        }
        if (head_cc <= 0){
            return false;
        }
        if (head_m <= 0){
            return false;
        }
        if (head_pt <= 0){
            return false;
        }
        if (head_timestamp <= 0){
            return false;
        }
        if (head_sequence_number <= 0){
            return false;
        }
        if (head_ssrc <= 0){
            return false;
        }
        if (head_csrc <= 0){
            return false;
        }
        return true;
    }

    public byte getHead_v() {
        return head_v;
    }

    public void setHead_v(byte head_v) {
        this.head_v = head_v;
    }

    public byte getHead_p() {
        return head_p;
    }

    public void setHead_p(byte head_p) {
        this.head_p = head_p;
    }

    public byte getHead_x() {
        return head_x;
    }

    public void setHead_x(byte head_x) {
        this.head_x = head_x;
    }

    public byte getHead_cc() {
        return head_cc;
    }

    public void setHead_cc(byte head_cc) {
        this.head_cc = head_cc;
    }

    public byte getHead_m() {
        return head_m;
    }

    public void setHead_m(byte head_m) {
        this.head_m = head_m;
    }

    public byte getHead_pt() {
        return head_pt;
    }

    public void setHead_pt(byte head_pt) {
        this.head_pt = head_pt;
    }

    public int getHead_timestamp() {
        return head_timestamp;
    }

    public void setHead_timestamp(int head_timestamp) {
        this.head_timestamp = head_timestamp;
    }

    public int getHead_sequence_number() {
        return head_sequence_number;
    }

    public void setHead_sequence_number(int head_sequence_number) {
        this.head_sequence_number = head_sequence_number;
    }

    public int getHead_ssrc() {
        return head_ssrc;
    }

    public void setHead_ssrc(int head_ssrc) {
        this.head_ssrc = head_ssrc;
    }

    public int getHead_csrc() {
        return head_csrc;
    }

    public void setHead_csrc(int head_csrc) {
        this.head_csrc = head_csrc;
    }
}
