/**
 * @version 1.0
 * @Author:qiu
 * @Description
 * @Date 17:21 2023/3/16
 **/
public class Test1 {
    public static void main(String[] args) {
       byte head_byte_1 = 0;
        //高2位写入 2bit 版本信息 默认version 2
        head_byte_1 = (byte) (head_byte_1 | 0B10010000);
        //高3位写入 1bit 填充信息 默认有填充信息
        head_byte_1 = (byte) (head_byte_1 | 0B00100000);
        //高4位写入 1bit 扩展标志 默认有扩展
        head_byte_1 = (byte) (head_byte_1 | 0B00010000);
        //高5位写入 4bit CSRC计数 4bit
        head_byte_1 = (byte) (head_byte_1 | 0B00001111);
        System.out.println(head_byte_1);
        System.out.println((byte)(head_byte_1 & 0B11111111));

        byte head_byte_2 = 0;
        //高1位写入 1bit 帧是否结束 默认version 1
        head_byte_2 = (byte) (head_byte_1 | 0B10000000);
        //高2位写入 7bit 载荷类型
        head_byte_2 = (byte) (head_byte_1 | 0B01111111);

        System.out.println(head_byte_2);
        System.out.println((byte)(head_byte_2 & 0B11111111));
    }
}
