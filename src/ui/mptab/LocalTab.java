package ui.mptab;

import core.MediaRecord;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/18/2017.
 */
public class LocalTab extends AbstractTab {

    private final static String TEXT = "Local Media";
    private final static String TOOLTIP = "Media from local device";
    private List<MediaRecord> mediaRecords;
    ObservableList<MediaRecord> tableData;

    public LocalTab() {
        setText(TEXT);
        setTooltip(new Tooltip(TOOLTIP));
        this.mediaRecords = new ArrayList<>();
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
        TableView tableView = new TableView();
        tableView.setEditable(true);

        TableColumn indexColumn = createIndexColumn();

        TableColumn checkboxColumn = createChecboxColumn();

        TableColumn<MediaRecord, String> descriptionColumn = createDescriptionColumn();

        tableView.getColumns().addAll(indexColumn, checkboxColumn, descriptionColumn);
        tableView.setScaleShape(true);

        tableData = createData();
        tableView.setItems(tableData);

        return tableView;
    }

    private TableColumn createDescriptionColumn() {
        TableColumn<MediaRecord, String> descriptionColumn = new TableColumn("Description");
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
        TableColumn<MediaRecord, Boolean> checkboxColumn = new TableColumn("");
        checkboxColumn.setPrefWidth(25);

        checkboxColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<MediaRecord, Boolean>, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<MediaRecord, Boolean> param) {
                        MediaRecord mediaRecord = param.getValue();
                        SimpleBooleanProperty booleanPoperty = new SimpleBooleanProperty(mediaRecord.isSelected());
                        booleanPoperty.addListener(new ChangeListener<Boolean>() {
                            @Override
                            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                                mediaRecord.setSelected(newValue);
                                if (newValue.booleanValue()) {
                                    mediaRecords.add(mediaRecord);
                                } else {
                                    mediaRecords.remove(mediaRecord);
                                }
                            }
                        });
                        return booleanPoperty;
                    }
                }
        );
        checkboxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkboxColumn));
        return checkboxColumn;
    }

    private TableColumn createIndexColumn() {
        TableColumn indexColumn = new TableColumn("");
        indexColumn.setPrefWidth(25);
        indexColumn.setCellValueFactory(
                new PropertyValueFactory<MediaRecord, String>("id")
        );
        return indexColumn;
    }

    private ObservableList<MediaRecord> createData() {
        return FXCollections.observableArrayList(
                new MediaRecord(1, "Sector"),
                new MediaRecord(2, "Manowar"),
                new MediaRecord(3, "Aria")
        );
    }
}
