package cc.eoma.clipboard;

import cc.eoma.clipboard.monitor.SystemClipboardMonitor;
import cc.eoma.clipboard.synchronizer.receiver.MultiCastReceiver;
import java.awt.datatransfer.Clipboard;

/**
 * @Description:
 * @Author goma
 * @Date 2021/8/11 下午4:25
 * @Version 1.0
 */
public class Application {
    public static void main(String[] args) throws Exception {
        SystemClipboardMonitor monitor = new SystemClipboardMonitor();

        MultiCastReceiver multiCastReceiver = new MultiCastReceiver(monitor.getOwner());
        multiCastReceiver.listen("228.5.6.7", 11222);
    }
}
