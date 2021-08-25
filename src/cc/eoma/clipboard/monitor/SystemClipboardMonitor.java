package cc.eoma.clipboard.monitor;

import cc.eoma.clipboard.synchronizer.SyncType;
import cc.eoma.clipboard.synchronizer.Synchronizer;
import cc.eoma.clipboard.synchronizer.receiver.BroadcastReceiver;
import cc.eoma.clipboard.synchronizer.sender.BroadcastSender;
import cc.eoma.clipboard.synchronizer.sender.MultiCastSender;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SystemClipboardMonitor implements ClipboardOwner {

    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public ClipboardOwner getOwner() {
        return this;
    }

    public SystemClipboardMonitor() {
        StringSelection selection = new StringSelection("");
        clipboard.setContents(selection, this);
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        String dataFlavor = "";
        StringBuilder copy = new StringBuilder();
        try {
            /**
             * 不加休眠时间有时会报  java.lang.IllegalStateException: cannot open system clipboard
             * 具体原因见： https://coderanch.com/t/377833/java/listen-clipboard
             */
            Thread.sleep(10);

            // this.processContents(clipboard.getContents(this));

            dataFlavor = getDataFlavor(clipboard);
            if (clipboard.isDataFlavorAvailable(DataFlavor.javaFileListFlavor)) {
                List data = (List)clipboard.getData(DataFlavor.javaFileListFlavor);
                for (Object it : data) {
                    copy.append("\n").append(it);
                }
                clipboard.setContents(clipboard.getContents(DataFlavor.javaFileListFlavor), this);
            } else if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                String text = (String)clipboard.getData(DataFlavor.stringFlavor);
                Synchronizer.send(SyncType.Broadcast, text);

                copy.append(text);
                StringSelection transferable = new StringSelection(text);
                clipboard.setContents(transferable, this);
            } else if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
                BufferedImage bufferedImage = (BufferedImage)clipboard.getData(DataFlavor.imageFlavor);

//                Object obj = clipboard.getData(DataFlavor.imageFlavor);
//                MyBufferedImage bufferedImage = null;
//                if( obj instanceof MyBufferedImage){ // 如果是 MyBufferedImage,说明是 Receiver 接受到的 不需要再次 发送组播消息
//                    System.out.println("mybufferedimage");
//                    bufferedImage = (MyBufferedImage) obj;
//                } else { // 系统直接 截图的，组播 发送 图片
//                    BufferedImage bi = (BufferedImage)obj;
//                    MyBufferedImage myBufferedImage = new MyBufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
//                    myBufferedImage.setData(bi.getData());
//                    myBufferedImage.setUid("xxxxxxx");
//
//                    // TODO SEND
//                }

                ImageSelection imageSelection = new ImageSelection(bufferedImage);
                clipboard.setContents(imageSelection, this);
            } else {
                clipboard.setContents(contents, this);
            }
        } catch (Exception ex) {
//            ex.printStackTrace();
            clipboard.setContents(contents, this);
        }
//        System.out.println("DataFlavor=" + dataFlavor + "clipboard data:" + copy);
    }

    private String getDataFlavor(Clipboard clipboard) {
        if (clipboard.isDataFlavorAvailable(DataFlavor.javaFileListFlavor)) {
            return "javaFileListFlavor";
        }
        if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
            return "stringFlavor";
        }
        if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
            return "imageFlavor";
        }
        if (clipboard.isDataFlavorAvailable(DataFlavor.plainTextFlavor)) {
            return "plainTextFlavor";
        }
        if (clipboard.isDataFlavorAvailable(DataFlavor.selectionHtmlFlavor)) {
            return "selectionHtmlFlavor";
        }
        if (clipboard.isDataFlavorAvailable(DataFlavor.fragmentHtmlFlavor)) {
            return "fragmentHtmlFlavor";
        }
        if (clipboard.isDataFlavorAvailable(DataFlavor.allHtmlFlavor)) {
            return "allHtmlFlavor";
        }
        return "";
    }
}
