package ui.mptab;

import core.MediaRecord;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/18/2017.
 */
public class LocalTab extends AbstractTab {


    private final static String TEXT = "Local Media";
    private final static String TOOLTIP = "Media from local device";
    private ObservableList<MediaRecord> tableData;
    private TableView tableView;

    public LocalTab() {
        setText(TEXT);
        setTooltip(new Tooltip(TOOLTIP));
        this.tableData = FXCollections.observableArrayList();
    }

    @Override
    protected void setHBox() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-border-color: black;");

        createTableView();
        this.setContent(this.tableView);
    }

    private void createTableView() {

        this.tableView = new TableView();
        this.tableView.setEditable(true);

        final TableColumn indexColumn = createIndexColumn();
        final TableColumn checkboxColumn = createChecboxColumn();
        final TableColumn<MediaRecord, String> descriptionColumn = createDescriptionColumn();

        this.tableView.getColumns().addAll(indexColumn, checkboxColumn, descriptionColumn);
        this.tableView.setScaleShape(true);
    }

    protected CheckBox createSelectAllCheckbox() {
        final CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                for (MediaRecord mediaRecord : LocalTab.this.tableData) {
                    changeMediaRecordsList(observable.getValue(), mediaRecord);
                }
                LocalTab.this.tableView.refresh();
            }
        });
        return checkBox;
    }

    public void loadFromSavedPlayList() {

        List<MediaRecord> mediaRecords = this.controller.getMediaRecords();

        this.tableData = FXCollections.observableList(mediaRecords);
        this.tableView.setItems(this.tableData);
        this.tableView.refresh();
        setContent(this.tableView);
    }

    public void load() {

        this.tableData = getCachedPlayList();
        this.controller.setMediaRecords(this.tableData);

        updateRowIndex();

        this.tableView.setItems(this.tableData);
        this.tableView.refresh();
        setContent(this.tableView);
    }

    private void updateRowIndex() {
        int index = 1;
        for (MediaRecord record : this.tableData) {
            record.setId(index++);
        }
    }

    private ObservableList<MediaRecord> getCachedPlayList() {
        List<MediaRecord> records = this.controller.getCachedPlayList(false);
        return FXCollections.observableList(records);
    }

    public void savePlayList(String pathToPlayList) {
        this.controller.saveProperties(PROPERTY_FIELD_LOCAL, pathToPlayList);
    }

    public ObservableList<MediaRecord> getRecords() {
        return this.tableData;
    }

    public void add() {
        this.controller.getMediaRecords().addAll(this.tableData);
        updateTableData(this.tableData);
    }

    private void updateTableData(ObservableList<MediaRecord> data) {
        this.controller.setCachedLocalPlayList();
        load();
    }

    public void cleanPlayList() {
        this.controller.getMediaRecords().clear();
        updateTableData(FXCollections.emptyObservableList());
    }

    public void removeChecked() {
        List<MediaRecord> newList = new ArrayList<>();
        for (MediaRecord mediaRecord : this.controller.getMediaRecords()) {
            if (!mediaRecord.isSelected()) {
                newList.add(mediaRecord);
            }
        }
        this.controller.setMediaRecords(newList);
        updateTableData(FXCollections.observableArrayList(newList));
    }

    public void updateConfigFile(File newPlayList) {
        savePlayList(newPlayList.getAbsolutePath());
    }

    private TableColumn createDescriptionColumn() {
        final TableColumn<MediaRecord, String> descriptionColumn = new TableColumn(TITLE_DESCRIPTION);
        descriptionColumn.setPrefWidth(300);
        descriptionColumn.setCellValueFactory(
                new PropertyValueFactory<MediaRecord, String>("displayName")
        );
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit((TableColumn.CellEditEvent<MediaRecord, String> event) -> {
                    final TablePosition position = event.getTablePosition();
                    final String newVslue = event.getNewValue().toString();
                    final int row = position.getRow();
                    final MediaRecord mediaRecord = event.getTableView().getItems().get(row);
                    mediaRecord.setDisplayName(newVslue);
                }
        );
        return descriptionColumn;
    }
}
