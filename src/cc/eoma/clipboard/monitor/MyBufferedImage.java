package cc.eoma.clipboard.monitor;

import java.awt.image.BufferedImage;

/**
 * @Description:
 * @Author goma
 * @Date 2021/8/12 下午12:07
 * @Version 1.0
 */
public class MyBufferedImage extends BufferedImage {

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid;

    public MyBufferedImage(int width, int height, int imageType) {
        super(width, height, imageType);
    }

}
