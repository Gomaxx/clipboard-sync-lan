package cc.eoma.clipboard.synchronizer.receiver;

public interface Receiver {
    void receive(MyHandler handler);
    void receive(String multiCastAddr, Integer port, MyHandler handler);
}
