package ui.mptab;

/**
 * Created by user on 9/22/2017.
 */
public interface PlayListTab {
    /**
     * Open Windows dialog for choose playlist file.
     * Read saved play list from file.
     * update cache
     */
    void loadFromSavedPlayList();

    /**
     * load and show tab content
     */
    void load();

    /**
     * add records to record list
     */
    void add();

    /**
     * remove selected item from play list
     */
    void removeChecked();
}
