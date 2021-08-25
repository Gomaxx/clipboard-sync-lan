package cc.eoma.clipboard.synchronizer;

import java.util.ArrayList;

public class FixedSizeList<T> extends ArrayList<T> {

    private int size = 5;

    public FixedSizeList() {
        super();
    }

    public FixedSizeList(int size) {
        this.size = size;
    }

    @Override
    public boolean add(T s) {
        if (super.size() > this.size) {
            super.remove(0);
        }
        return super.add(s);
    }
}
