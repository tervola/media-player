package ui.mptab;

import core.MediaRecord;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/18/2017.
 */
public class OnlineTab extends AbstractTab {

    private final static String TEXT = "Online Media";
    private final static String TOOLTIP = "Online media from YouTube";
    private final ArrayList mediaOnlineRecords;
    private ObservableList<MediaRecord> onlineTableData;
    private TableView tableOnlineTableView;
    private Node tableView;


    public OnlineTab() {
        setText(TEXT);
        setTooltip(new Tooltip(TOOLTIP));
        this.mediaOnlineRecords = new ArrayList<>();
        this.onlineTableData = FXCollections.observableArrayList();
    }

    @Override
    protected void setHBox() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-border-color: black;");

        createOnlineTableView();
        this.setContent(tableOnlineTableView);
    }

    private void createOnlineTableView() {

        this.tableOnlineTableView = new TableView();
        this.tableOnlineTableView.setEditable(true);

        final TableColumn indexColumn = createIndexColumn();
        final TableColumn checkboxColumn = createChecboxColumn();
        final TableColumn<MediaRecord, String> descriptionColumn = createDescriptionColumn();
        final TableColumn<MediaRecord, String> uriColumn = createUriColumn();

        this.tableOnlineTableView.getColumns().addAll(indexColumn, checkboxColumn, descriptionColumn, uriColumn);
        this.tableOnlineTableView.setScaleShape(true);

    }

    private TableColumn<MediaRecord, String> createUriColumn() {
        final TableColumn<MediaRecord, String> uriColumn = new TableColumn(TITLE_URI);
        uriColumn.setPrefWidth(300);
        uriColumn.setCellValueFactory(
                new PropertyValueFactory<MediaRecord, String>("Uri")
        );
        uriColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        return uriColumn;
    }

    protected CheckBox createSelectAllCheckbox() {
        final CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                for (MediaRecord mediaRecord : OnlineTab.this.onlineTableData) {
                    changeMediaRecordsList(observable.getValue(), mediaRecord);
                }
                OnlineTab.this.tableOnlineTableView.refresh();
            }
        });
        return checkBox;
    }

    public void add() {
        updateTableData();
    }

    private void updateTableData() {
        this.controller.setCachedPlayList(ISONLINE);
        load();
    }

    public void load() {
        this.onlineTableData = getCachedPlayList();
        this.controller.setOnlineMediaRecords(this.onlineTableData);

        updateRowIndex();

        this.tableOnlineTableView.setItems(this.onlineTableData);
        this.tableOnlineTableView.refresh();
        setContent(this.tableOnlineTableView);
    }

    private ObservableList<MediaRecord> getCachedPlayList() {
        List<MediaRecord> records = this.controller.getCachedPlayList(true);
        return FXCollections.observableList(records);
    }

    public void loadFromSavedPlayList() {
        List<MediaRecord> mediaRecords = this.controller.getOnlineMediaRecord();
        this.onlineTableData = FXCollections.observableList(mediaRecords);
        this.tableOnlineTableView.setItems(this.onlineTableData);

        this.tableOnlineTableView.refresh();
        setContent(this.tableOnlineTableView);
    }

    public void cleanPlayList() {
        this.controller.getOnlineMediaRecord().clear();
        updateTableData();
    }

    private void updateRowIndex() {
        int index = 1;
        for (MediaRecord record : this.onlineTableData) {
            record.setId(index++);
        }
    }

    public void savePlayList(String pathToPlayList) {
        this.controller.saveProperties(PROPERTY_FIELD_ONLINE, pathToPlayList);
    }

    public void updateConfigFile(File newPlayList) {
        savePlayList(newPlayList.getAbsolutePath());
    }

    protected TableColumn createDescriptionColumn() {
        final TableColumn<MediaRecord, String> descriptionColumn = new TableColumn(TITLE_DESCRIPTION);
        descriptionColumn.setPrefWidth(100);
        descriptionColumn.setCellValueFactory(
                new PropertyValueFactory<MediaRecord, String>("displayName")
        );
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit((TableColumn.CellEditEvent<MediaRecord, String> event) -> {
                    TablePosition position = event.getTablePosition();
                    final String newVslue = event.getNewValue().toString();
                    final int row = position.getRow();
                    final MediaRecord mediaRecord = event.getTableView().getItems().get(row);
                    mediaRecord.setUri(newVslue);
                }
        );
        return descriptionColumn;
    }


    public void removeChecked() {
        List<MediaRecord> newList = new ArrayList<>();
        for (MediaRecord mediaRecord : this.controller.getOnlineMediaRecord()) {
            if (!mediaRecord.isSelected()) {
                newList.add(mediaRecord);
            }
        }
        this.controller.setOnlineMediaRecords(newList);
        updateTableData();
    }
}
