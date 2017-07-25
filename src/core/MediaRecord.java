package core;

/**
 * Created by user on 7/18/2017.
 */
public class MediaRecord {
    private int id;
    private boolean isSelected;
    private String displayName;
    private String originalName;
    private String path;
    private String duration;
    private boolean isOnline;

    public MediaRecord() {

    }

    public MediaRecord(int id, String displayName) {
        this.id = id;
        this.isSelected = false;
        this.displayName = displayName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getPath() {
        return path;
    }

    public String getDuration() {
        return duration;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
