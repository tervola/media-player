package ui.mptab;

import core.MediaRecord;
import core.controls.FileController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/18/2017.
 */
public class LocalTab extends AbstractTab {

    private final static String TITLE_DESCRIPTION = "Description";
    private final static String TITLE_INDEX = " ";
    private final static String TEXT = "Local Media";
    private final static String TOOLTIP = "Media from local device";
    private static String PROPERTY_FIELD = "playlist";
    private List<MediaRecord> mediaRecords;
    private List<MediaRecord> chechedMediaRecords;
    ObservableList<MediaRecord> tableData;
    private TableView tableView;
    private final FileController controller;

    public LocalTab() {
        setText(TEXT);
        setTooltip(new Tooltip(TOOLTIP));
        this.mediaRecords = new ArrayList<>();
        this.chechedMediaRecords = new ArrayList<>();
        this.controller = FileController.getInstance();
        this.tableData = FXCollections.observableArrayList();
    }

    @Override
    protected void setHBox() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-border-color: black;");

        TableView tableView = createTableView();
        this.setContent(tableView);
    }

    private TableView createTableView() {

        this.tableView = new TableView();
        this.tableView.setEditable(true);

        final TableColumn indexColumn = createIndexColumn();

        final TableColumn checkboxColumn = createChecboxColumn();

        final TableColumn<MediaRecord, String> descriptionColumn = createDescriptionColumn();

        this.tableView.getColumns().addAll(indexColumn, checkboxColumn, descriptionColumn);
        this.tableView.setScaleShape(true);

        return tableView;
    }

    private TableColumn createDescriptionColumn() {
        final TableColumn<MediaRecord, String> descriptionColumn = new TableColumn(TITLE_DESCRIPTION);
        descriptionColumn.setPrefWidth(300);
        descriptionColumn.setCellValueFactory(
                new PropertyValueFactory<MediaRecord, String>("displayName")
        );
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit((TableColumn.CellEditEvent<MediaRecord, String> event) -> {
                    TablePosition position = event.getTablePosition();
                    String newVslue = event.getNewValue().toString();
                    int row = position.getRow();
                    MediaRecord mediaRecord = event.getTableView().getItems().get(row);
                    mediaRecord.setDisplayName(newVslue);
                }
        );
        return descriptionColumn;
    }

    private TableColumn createChecboxColumn() {
        final TableColumn<MediaRecord, Boolean> checkboxColumn = new TableColumn();
        checkboxColumn.setPrefWidth(25);

        final CheckBox selectAllCkb = createSelectAllCheckbox();
        checkboxColumn.setGraphic(selectAllCkb);

        checkboxColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<MediaRecord, Boolean>, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<MediaRecord, Boolean> param) {
                        MediaRecord mediaRecord = param.getValue();
                        SimpleBooleanProperty booleanPoperty = new SimpleBooleanProperty(mediaRecord.isSelected());
                        booleanPoperty.addListener(new ChangeListener<Boolean>() {
                            @Override
                            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                                changeMediaRecordsList(observable.getValue(), mediaRecord);
                            }
                        });
                        return booleanPoperty;
                    }
                }
        );
        checkboxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkboxColumn));

        return checkboxColumn;
    }

    private CheckBox createSelectAllCheckbox() {
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

    private void changeMediaRecordsList(Boolean value, MediaRecord mediaRecord) {
        mediaRecord.setSelected(value);
        if (value) {
            System.out.println("here!!");
        } else {
            System.out.println("there!!");
        }

    }

    private TableColumn createIndexColumn() {
        final TableColumn indexColumn = new TableColumn(TITLE_INDEX);
        indexColumn.setPrefWidth(25);
        indexColumn.setCellValueFactory(
                new PropertyValueFactory<MediaRecord, String>("id")
        );
        return indexColumn;
    }

    private ObservableList<MediaRecord> createFakeData() {
        return FXCollections.observableArrayList(
                new MediaRecord(1, "Sector"),
                new MediaRecord(2, "Manowar"),
                new MediaRecord(3, "Aria")
        );
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
        List<MediaRecord> records = this.controller.getCachedPlayList();
        return FXCollections.observableList(records);
    }

    public void savePlayList(String pathToPlayList) {
        this.controller.saveProperties(PROPERTY_FIELD, pathToPlayList);
    }

    public ObservableList<MediaRecord> getRecords() {
        return this.tableData;
    }

    public void add() {
        this.controller.getMediaRecords().addAll(this.tableData);
        updateTableData(this.tableData);
    }

    private void updateTableData(ObservableList<MediaRecord> data) {
        try {
            this.controller.setCachedPlayList(data);
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
