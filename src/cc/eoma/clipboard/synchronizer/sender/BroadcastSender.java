package cc.eoma.clipboard.synchronizer.sender;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * @Description:
 * @Author goma
 * @Date 2021/8/25 下午12:38
 * @Version 1.0
 */
public class BroadcastSender {
    public static void send(String message) {
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            InetAddress ip = InetAddress.getByName("255.255.255.255");

            byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, ip, 3001);
            datagramSocket.send(datagramPacket);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
