package cc.eoma.clipboard.synchronizer.receiver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @Description:
 * @Author goma
 * @Date 2021/8/25 下午12:29
 * @Version 1.0
 */
public class BroadcastReceiver implements Receiver {

    @Override
    public void receive(MyHandler handler) {
        try (DatagramSocket datagramSocket = new DatagramSocket(3001)) {
            byte[] buf = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
            while (true) {
                this.handler(datagramSocket, datagramPacket, handler);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void receive(String multiCastAddr, Integer port, MyHandler handler) {
        this.receive(handler);
    }

    private void handler(DatagramSocket datagramSocket, DatagramPacket datagramPacket, MyHandler handler) {
        try {
            datagramSocket.receive(datagramPacket);
            if (InetAddress.getLocalHost().getHostAddress().equals(datagramPacket.getAddress().getHostAddress())) {
                return;
            }

            // String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
            byte[] xxx = new byte[datagramPacket.getLength()];
            System.arraycopy(datagramPacket.getData(), 0, xxx, 0, datagramPacket.getLength());
            handler.process(xxx);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
