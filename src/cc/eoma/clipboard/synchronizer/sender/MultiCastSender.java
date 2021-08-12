package cc.eoma.clipboard.synchronizer.sender;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;

/**
 * @Description:
 * @Author goma
 * @Date 2021/8/11 上午11:21
 * @Version 1.0
 */
public class MultiCastSender {
    public static void send(String multiCastAddr, Integer port, byte[] message) throws Exception {
        MulticastSocket multicastSocket = new MulticastSocket();
        multicastSocket.joinGroup(InetAddress.getByName(multiCastAddr));

        //在多播中设置了TTl值（Time to live），每一个ip数据报文中都包含一个TTL，
        //每当有路由器转发该报文时，TTL减1，知道减为0时，生命周期结束，报文即时没有到达目的地，
        //也立即宣布死亡。当然在Java中，ttl并不是十分准确的，
        //曾经在一本书中介绍过报文的传播距离是不会超过ttl所设置的值的。
        multicastSocket.setTimeToLive(1);
        //设置本MultcastSocket发送的数据报将被送到本身
        multicastSocket.setLoopbackMode(true);

        DatagramPacket hi = new DatagramPacket(message, message.length, InetAddress.getByName(multiCastAddr), port);
        multicastSocket.send(hi);
        /******************发送组播数据****************/
        multicastSocket.close();
    }

    public static void main(String[] args) throws Exception {
//        for (int i = 0; i < 100; i++) {
//            String xxxx = "clipboard-sync-lan:goma" + i;
//            System.out.println(xxxx);
//
//            MultiCastSender.send("228.5.6.7", 11222, xxxx.getBytes(StandardCharsets.UTF_8));
////            MultiCastSender.send("228.5.6.7", 11222, xxxx.getBytes(StandardCharsets.UTF_8));
//            Thread.sleep(1000);
//        }


//        MultiCastSender.send("228.5.6.7", 11222,"file-#-2021-08-12 11.png".getBytes(StandardCharsets.UTF_8));
//
//        String file = "/home/goma/Pictures/2021-08-12 11.png";
//        //读取文件(字节流)
//        InputStream in = new FileInputStream(file);
////        //写入相应的文件
////        OutputStream out = new FileOutputStream("d:\\2.txt");
////        //读取数据 //一次性取多少字节
//        byte[] bytes = new byte[1024 * 10];
//        //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
//        int i = 0;
//        int n = -1;
//        //循环取出数据
//        while ((n = in.read(bytes, 0, bytes.length)) != -1) {
//            byte[] xxx = new byte[n];
//            System.arraycopy(bytes, 0, xxx, 0, n);
//            MultiCastSender.send("228.5.6.7", 11222, xxx);
//            i++;
//            Thread.sleep(100);
////            //转换成字符串
////            String str = new String(bytes, 0, n, "GBK"); //这里可以实现字节到字符串的转换，比较实用
////            System.out.println(str);
////            //写入相关文件
////            out.write(bytes, 0, n);
//        }
//        //关闭流
//        in.close();
////        out.close();
//        System.out.println(i);
//        MultiCastSender.send("228.5.6.7", 11222, "endfile".getBytes(StandardCharsets.UTF_8));
    }
}
