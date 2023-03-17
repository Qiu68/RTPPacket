package rtp;

import util.BaseDataConvertByte;

import java.nio.charset.StandardCharsets;

/**
 * @version 1.0
 * @Author:qiu
 * @Description
 * @Date 10:30 2023/3/17
 **/
public class RtpTest {
    public static void main(String[] args) {
        byte[] data = "11111111111111111111111111111111111111111111111111111111111111111111111".getBytes(StandardCharsets.UTF_8);
        System.out.println(data.length);
        RtpPacketEncoding rtpPacketEncoding = new RtpPacketEncoding();
        rtpPacketEncoding.headEncoding(1500,1500,1500,1500);
        rtpPacketEncoding.setRtpPayload(data, (short) data.length);

        byte[] rtpPacket = rtpPacketEncoding.getRtpPacket();
        System.out.println(rtpPacket.length);
        System.out.println(new String(rtpPacket));

        RtpPacketDecode rtpPacketDecode = new RtpPacketDecode();

        if (rtpPacketDecode.decode(rtpPacket)) {
            System.out.println("success!");
            System.out.println(rtpPacketDecode.getHead_v());
            System.out.println(rtpPacketDecode.getHead_cc());
            System.out.println(rtpPacketDecode.getHead_x());
            System.out.println(rtpPacketDecode.getHead_m());
            System.out.println(rtpPacketDecode.getHead_pt());
            System.out.println(rtpPacketDecode.getHead_sequence_number());
            System.out.println(rtpPacketDecode.getHead_timestamp());
            System.out.println(rtpPacketDecode.getLength());
            System.out.println(new String(rtpPacketDecode.getBody()));
        }
        else {
            System.out.println("error!");
        }


    }
}
