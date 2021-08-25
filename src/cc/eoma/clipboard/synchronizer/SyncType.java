package cc.eoma.clipboard.synchronizer;

public enum SyncType {

    Broadcast("Broadcast"),
    Multicast("Multicast");

    private String description;

    SyncType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
