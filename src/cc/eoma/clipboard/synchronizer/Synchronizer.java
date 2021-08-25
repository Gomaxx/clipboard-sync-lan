package cc.eoma.clipboard.synchronizer;

import cc.eoma.clipboard.synchronizer.receiver.BroadcastReceiver;
import cc.eoma.clipboard.synchronizer.receiver.MultiCastReceiver;
import cc.eoma.clipboard.synchronizer.receiver.MyHandler;
import cc.eoma.clipboard.synchronizer.sender.BroadcastSender;
import cc.eoma.clipboard.synchronizer.sender.MultiCastSender;
import java.awt.datatransfer.ClipboardOwner;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

public class Synchronizer {
    private static FixedSizeList<String> messages = new FixedSizeList<>();

    /**
     * 手机热点时，组播方式无法发送，因为此时手机相当于网关，跟其他接入设备不属于同一网段
     *
     * @param syncType .
     * @param message  .
     */
    public static void send(SyncType syncType, String message) {
        if (messages.contains(getMd5(message))) {
            return;
        }
        System.out.println("----------------------> send:" + message);
        if (syncType.equals(SyncType.Broadcast)) {
            BroadcastSender.send(message);
        } else if (syncType.equals(SyncType.Multicast)) {
            MultiCastSender.send("228.5.6.7", 11222, message);
        }
    }

    public static void receive(SyncType syncType, ClipboardOwner clipboardOwner) {
        MyHandler handler = new MyHandler(clipboardOwner);
        // 可以考虑 提炼接口  统一回调 处理
        if (syncType.equals(SyncType.Broadcast)) {
            BroadcastReceiver receiver = new BroadcastReceiver();
            receiver.receive(handler);
        } else if (syncType.equals(SyncType.Multicast)) {
            MultiCastReceiver receiver = new MultiCastReceiver();
            receiver.receive("228.5.6.7", 11222, handler);
        }
    }

    public static void addReceiveMessage(String message) {
        messages.add(getMd5(message));
    }

    private static String getMd5(String message) {
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(message.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return UUID.randomUUID().toString();
        }

    }
}