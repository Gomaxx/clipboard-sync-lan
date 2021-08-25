package cc.eoma.clipboard.synchronizer.receiver;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MultiCastReceiver implements Receiver {
    @Override
    public void receive(MyHandler handler) {
        this.receive("228.5.6.7", 11222, handler);
    }

    @Override
    public void receive(String multiCastAddr, Integer port, MyHandler handler) {
        try (MulticastSocket multicastSocket = new MulticastSocket(port)) {
            multicastSocket.joinGroup(InetAddress.getByName(multiCastAddr));
            byte[] bytes = new byte[1024 * 10];
            DatagramPacket datagramPacket = new DatagramPacket(bytes, 0, bytes.length);

            while (true) {
                this.handler(multicastSocket, datagramPacket, handler);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handler(MulticastSocket multicastSocket, DatagramPacket datagramPacket, MyHandler handler) {
        try {
            multicastSocket.receive(datagramPacket);
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