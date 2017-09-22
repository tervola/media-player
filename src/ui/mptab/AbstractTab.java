package ui.mptab;

import core.MediaRecord;
import core.controls.FileController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * Created by user on 7/18/2017.
 */
public abstract class AbstractTab extends Tab {

    protected final FileController controller;
    protected final static String TITLE_INDEX = " ";
    protected final static String TITLE_DESCRIPTION = "Description";
    protected final static String TITLE_URI = "URI";
    protected final static String PROPERTY_FIELD_ONLINE = "playlistlocal";
    protected final static String PROPERTY_FIELD_LOCAL = "playlistonline";
    protected final static boolean ISONLINE = true;


    public AbstractTab() {
        setHBox();
        this.controller = FileController.getInstance();
    }

    protected abstract void setHBox();

    protected TableColumn createIndexColumn() {
        final TableColumn indexColumn = new TableColumn(TITLE_INDEX);
        indexColumn.setPrefWidth(25);
        indexColumn.setCellValueFactory(
                new PropertyValueFactory<MediaRecord, String>("id")
        );
        return indexColumn;
    }

    protected TableColumn createChecboxColumn() {
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

    protected abstract CheckBox createSelectAllCheckbox();

    protected void changeMediaRecordsList(Boolean value, MediaRecord mediaRecord) {
        mediaRecord.setSelected(value);
        if (value) {
            System.out.println("here!!");
        } else {
            System.out.println("there!!");
        }
    }

}
