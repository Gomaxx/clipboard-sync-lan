package cc.eoma.clipboard.synchronizer.sender;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;

/**
 * @Description:
 * @Author goma
 * @Date 2021/8/11 上午11:21
 * @Version 1.0
 */
public class MultiCastSender {
    public static void send(String multiCastAddr, Integer port, String message) {
        try (MulticastSocket multicastSocket = new MulticastSocket()) {
            // 加入组
            multicastSocket.joinGroup(InetAddress.getByName(multiCastAddr));

            //在多播中设置了TTl值（Time to live），每一个ip数据报文中都包含一个TTL，
            //每当有路由器转发该报文时，TTL减1，知道减为0时，生命周期结束，报文即时没有到达目的地，
            //也立即宣布死亡。当然在Java中，ttl并不是十分准确的，
            //曾经在一本书中介绍过报文的传播距离是不会超过ttl所设置的值的。
            multicastSocket.setTimeToLive(1);
            //设置本MultcastSocket发送的数据报将被送到本身
            multicastSocket.setLoopbackMode(true);

            byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
            /******************发送组播数据****************/
            DatagramPacket hi = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(multiCastAddr), port);
            multicastSocket.send(hi);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
