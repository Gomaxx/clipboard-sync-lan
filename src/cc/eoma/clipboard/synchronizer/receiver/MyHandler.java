package cc.eoma.clipboard.synchronizer.receiver;

import cc.eoma.clipboard.synchronizer.Synchronizer;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.nio.charset.StandardCharsets;

public class MyHandler {

    private ClipboardOwner clipboardOwner;

    public MyHandler(ClipboardOwner clipboardOwner) {
        this.clipboardOwner = clipboardOwner;
    }

    public void process(byte[] bytes) {
        String message = new String(bytes, 0, bytes.length, StandardCharsets.UTF_8);
        Synchronizer.addReceiveMessage(message);
        System.out.println("-----------receive:" + message);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection transferable = new StringSelection(message);
        clipboard.setContents(transferable, this.clipboardOwner);
    }
}
