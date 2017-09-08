package ui.mpbutton;

import core.MediaRecord;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import ui.mptab.LocalTab;

import java.io.File;

/**
 * Created by user on 8/30/2017.
 */
public class SavePlayListButton extends AbstractButton {
    private final static String TEXT = "Save PL";
    private final static String TOOLTIP = "saving play list";


    public SavePlayListButton() {
        super.setTooltip(new Tooltip(TOOLTIP));
        super.setText(TEXT);
    }

    @Override
    protected void setOnAction() {
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                if (SavePlayListButton.this.filePicker.isClosed()) {
                    String context = getRecords();
                    File newPlayList = SavePlayListButton.this.filePicker.showSaveDialog(context);
                    if (newPlayList != null) {
                        SavePlayListButton.this.updateConfigFile(newPlayList);
                    }
                }
            }
        });
    }

    private String getRecords() {
        StringBuilder stringBuilder = new StringBuilder();
        LocalTab localTab = this.playList.getTabController().getLocalTab();
        for (MediaRecord mediaRecord : localTab.getRecords()) {
            stringBuilder.append(mediaRecord.getDisplayName());
            stringBuilder.append(";");
            stringBuilder.append(mediaRecord.getPath());
            stringBuilder.append("\r\n");

        }

        return stringBuilder.toString();
    }

//    public void savePlayList(String pathToPlayList) {
//        controller.saveProperties(PROPERTY_FIELD, pathToPlayList);
//    }
}
