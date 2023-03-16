package rtp;

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

    public byte[] encoding(){
        if (!checkHeadParams()){
            new Exception("init head param error!");
        }



        return null;
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
