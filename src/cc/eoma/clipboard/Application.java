package cc.eoma.clipboard;

import cc.eoma.clipboard.monitor.SystemClipboardMonitor;
import cc.eoma.clipboard.synchronizer.SyncType;
import cc.eoma.clipboard.synchronizer.Synchronizer;

/**
 * @Description:
 * @Author goma
 * @Date 2021/8/11 下午4:25
 * @Version 1.0
 */
public class Application {
    public static void main(String[] args) {
        SystemClipboardMonitor monitor = new SystemClipboardMonitor();
        Synchronizer.receive(SyncType.Broadcast, monitor.getOwner());
    }
}
