package rtcp;

import util.BaseDataConvertByte;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author:qiu
 * @Description 组装rtcp包
 * @Date 14:02 2023/3/17
 **/
public class RtcpPacketEncoding {
    //rtcp版本 2bit
    private byte head_v;
    //填充 1bit
    private byte head_p;
    //接收报告计数器 5bit
    private byte head_rc;

    //报文类型 8bit
    private byte head_pt;
    //报头长度 8bit
    private short length;

    //同步源 32bit
    private int head_ssrc;

    //发送者信息
    //网络发送时间高位 32bit
    private int head_ntp_timestamp_most;
    //网络发送时间地位 32bit
    private int head_ntp_timestamp_least;
    //rtp时间戳 32bit
    private int head_rtp_timestamp;
    //发送包的数量 32bit
    private int head_sender_packet_count;
    //已发送的字节数32bit
    private int head_sender_octet_count;


    //接收端的源 32bit
    private int head_ssrc_n;
    //丢包率 8bit
    private byte head_fraction_lost;
    //累计丢包数 32bit
    private int head_packet_loss_sum;
    //期望序列号 32bit
    private int head_max_sequence_number;
    //到达时间间隔的统计方差 32bit
    private int head_inter_arrival_jitter;
    //上次发送sr的时间 32bit
    private int head_last_lsr;
    //上次发送 sr的 发送到接收的时延 32bit
    private int head_dlsr;

    //报头第一个字节
    byte head_byte_1;
    //报头第二个字节
    byte head_byte_2;

    //公共报头
    byte[] public_head_bytes = new byte[7];

    byte[] sender_bytes = new byte[20];
    //存放接收报告块
    private List<byte[]> rrList = new ArrayList<>();



    public boolean  init(byte type,int ssrc){

        head_v = (byte) 0B11000000;
        head_p = 0B00100000;
        //接收报告默认为2块
        head_rc = 0B00000010;

        head_byte_1 = 0;
        //高2位写入 2bit 版本信息 默认version 2
        head_byte_1 = (byte) (head_byte_1 |  head_v);
        //高3位写入 1bit 填充信息 默认有填充信息
        head_byte_1 = (byte) (head_byte_1 | head_p);
        //高4位写入 5bit 接收数据块数量
        head_byte_1 = (byte) (head_byte_1 | head_rc);

        head_byte_2 = type;

        head_ssrc = ssrc;

        length = 7;


        public_head_bytes[0] = head_byte_1;
        public_head_bytes[1] = head_byte_2;


        return true;
    }


    /**
     * 发送者编码
     * @param ntp_timestamp_most
     * @param ntp_timestamp_least
     * @param rtp_timestamp
     * @param sender_packet_count
     * @param head_sender_octet_count
     * @return
     */
    public boolean rtcpSREncoding(int ntp_timestamp_most,int ntp_timestamp_least,int rtp_timestamp,
                                  int sender_packet_count,int head_sender_octet_count){
        this.head_ntp_timestamp_most = ntp_timestamp_most;
        byte[] ntp_timestamp_most_bytes = BaseDataConvertByte.intToBytes(head_ntp_timestamp_most);
        System.arraycopy(ntp_timestamp_most_bytes,0,sender_bytes,0,4);

        this.head_ntp_timestamp_least = ntp_timestamp_least;
        byte[] head_ntp_timestamp_least_bytes = BaseDataConvertByte.intToBytes(head_ntp_timestamp_least);
        System.arraycopy(head_ntp_timestamp_least_bytes,0,sender_bytes,4,4);

        this.head_rtp_timestamp = rtp_timestamp;
        byte[] head_rtp_timestamp_bytes = BaseDataConvertByte.intToBytes(head_rtp_timestamp);
        System.arraycopy(head_rtp_timestamp_bytes,0,sender_bytes,8,4);

        this.head_sender_packet_count = sender_packet_count;
        byte[] head_sender_packet_count_bytes = BaseDataConvertByte.intToBytes(head_sender_packet_count);
        System.arraycopy(head_sender_packet_count_bytes,0,sender_bytes,12,4);

        this.head_sender_octet_count = head_sender_octet_count;
        byte[] head_sender_octet_count_bytes = BaseDataConvertByte.intToBytes(head_sender_octet_count);
        System.arraycopy(head_sender_octet_count_bytes,0,sender_bytes,16,4);

        return true;
    }

    /**
     * 添加接收者报告块
     * @param ssrc_n
     * @param fraction_lost
     * @param packet_loss_sum
     * @param max_sequence_number
     * @param inter_arrival_jitter
     * @param last_lsr
     * @param dlsr
     * @return
     */
    public boolean addRRBlock(int ssrc_n,byte fraction_lost,int packet_loss_sum,
                                  int max_sequence_number,int inter_arrival_jitter,
                                  int last_lsr,int dlsr){
        byte[] rrByte = new byte[25];

        this.head_ssrc_n = ssrc_n;
        byte[] ssrc_n_bytes = BaseDataConvertByte.intToBytes(this.head_ssrc_n);
        System.arraycopy(ssrc_n_bytes,0,rrByte,0,ssrc_n_bytes.length);

        this.head_fraction_lost = fraction_lost;
        byte[] fraction_lost_byte = BaseDataConvertByte.shortToBytes(this.head_fraction_lost);
        System.arraycopy(fraction_lost_byte,0,rrByte,4,fraction_lost_byte.length);

        this.head_packet_loss_sum = packet_loss_sum;
        byte[] packet_loss_sum_byte = BaseDataConvertByte.intToBytes(head_packet_loss_sum);
        System.arraycopy(packet_loss_sum_byte,0,rrByte,5,packet_loss_sum_byte.length);

        this.head_max_sequence_number = max_sequence_number;
        byte[] max_sequence_number_bytes = BaseDataConvertByte.intToBytes(head_max_sequence_number);
        System.arraycopy(max_sequence_number_bytes,0,rrByte,9,max_sequence_number_bytes.length);

        this.head_inter_arrival_jitter = inter_arrival_jitter;
        byte[] head_inter_arrival_jitter_bytes = BaseDataConvertByte.intToBytes(head_inter_arrival_jitter);
        System.arraycopy(head_inter_arrival_jitter_bytes,0,rrByte,13,head_inter_arrival_jitter_bytes.length);

        this.head_last_lsr = last_lsr;
        byte[] head_last_lsr_bytes = BaseDataConvertByte.intToBytes(head_last_lsr);
        System.arraycopy(head_last_lsr_bytes,0,rrByte,17,head_last_lsr_bytes.length);

        this.head_dlsr = dlsr;
        byte[] head_dlsr_bytes = BaseDataConvertByte.intToBytes(head_dlsr);
        System.arraycopy(head_dlsr_bytes,0,rrByte,21,head_dlsr_bytes.length);

        rrList.add(rrByte);

        return true;

    }

    public byte[] getSRPacket(){

        int length = 0;

        if (head_rc <= 0){

            length = 2 + 20;
        }

    }

}
