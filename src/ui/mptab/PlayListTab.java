package ui.mptab;

import core.MediaRecord;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

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

    /**
     *
     * @return TRUE if tab is online
     */
    boolean isOnline();

    /**
     * @return table view
     */
    TableView getTableView();

    /**
     * @return table data
     */
    ObservableList<MediaRecord> getTableData();
}
