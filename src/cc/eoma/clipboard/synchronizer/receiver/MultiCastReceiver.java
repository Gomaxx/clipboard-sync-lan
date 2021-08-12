package cc.eoma.clipboard.synchronizer.receiver;

import cc.eoma.clipboard.monitor.ImageSelection;
import cc.eoma.clipboard.monitor.MyBufferedImage;
import cc.eoma.clipboard.monitor.SystemClipboardMonitor;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;

/**
 * @Description:
 * @Author goma
 * @Date 2021/8/11 上午11:11
 * @Version 1.0
 */
public class MultiCastReceiver {

//    private FileOutputStream fos;
//    private File file;

    private ClipboardOwner clipboardOwner;

    public MultiCastReceiver(ClipboardOwner clipboardOwner) {
        this.clipboardOwner = clipboardOwner;
    }

    public void listen(String multiCastAddr, Integer port) throws Exception {
        MulticastSocket multicastSocket = new MulticastSocket(port);
        multicastSocket.joinGroup(InetAddress.getByName(multiCastAddr));
        while (true) {
            this.handler(multicastSocket);
        }
    }

    private void handler(MulticastSocket multicastSocket) {
        try {
            byte[] bytes = new byte[1024 * 10];
            DatagramPacket datagramPacket = new DatagramPacket(bytes, 0, bytes.length);
            multicastSocket.receive(datagramPacket);
            // String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
            byte[] xxx = new byte[datagramPacket.getLength()];
            System.arraycopy(bytes, 0, xxx, 0, datagramPacket.getLength());

//            String message = new String(xxx, 0, xxx.length);
//            this.processMessage(message, xxx);
            this.processMessage(xxx);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void processMessage(byte[] bytes) throws IOException {
        String message = new String(bytes, 0, bytes.length, StandardCharsets.UTF_8);
        System.out.println("receive message:" + message);
        // 文件处理
//        if (message.startsWith("file")) {
//            String fileName = message.split("-#-")[1];
//            file = new File("/home/goma/Downloads/clipboard/" + fileName);
//            this.fos = new FileOutputStream(file);
//        } else if ("endfile".equalsIgnoreCase(message)) {
//            this.fos.close();
//            this.file = null;
//        } else if (this.fos != null) {
//            fos.write(bytes);
//        } else {
//            System.out.println(message);
//            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//            StringSelection tmp = new StringSelection(message);
//            clipboard.setContents(tmp, null);
//        }


//        // 图片处理 创建输入流
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
//        BufferedImage image = ImageIO.read(byteArrayInputStream);
//        MyBufferedImage bufferedImage = new MyBufferedImage(image.getWidth(), image.getHeight(), image.getType());
//        bufferedImage.setData(image.getData());
//        bufferedImage.setUid("xxxx");
//        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//        ImageSelection imageSelection = new ImageSelection(bufferedImage);
//        clipboard.setContents(imageSelection, this.clipboardOwner);

        // 字符串 处理
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection transferable = new StringSelection(message.replaceFirst("clipboard-sync-lan", ""));
        clipboard.setContents(transferable, this.clipboardOwner);
    }

//    public static void main(String[] args) {
//        MultiCastReceiver multiCastReceiver = new MultiCastReceiver();
//        new Thread(() -> {
//            try {
//                multiCastReceiver.listen("228.5.6.7", 11222);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
}
