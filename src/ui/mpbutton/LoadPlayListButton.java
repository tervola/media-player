package ui.mpbutton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import ui.mptab.LocalTab;

/**
 * Created by user on 9/8/2017.
 */
public class LoadPlayListButton extends AbstractButton {
    private final static String TEXT = "Load PL";
    private final static String TOOLTIP = "loading play list";

    public LoadPlayListButton() {
        super.setTooltip(new Tooltip(TOOLTIP));
        super.setText(TEXT);
    }

    @Override
    protected void setOnAction() {
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                if (LoadPlayListButton.this.filePicker.isClosed()) {
                    boolean isPickedNewRecords = LoadPlayListButton.this.filePicker.showLoadPlayListDialog();
                    if (isPickedNewRecords) {
                        LocalTab localTab = playList.getTabController().getLocalTab();
                        localTab.loadFromSavedPlayList();
                    }
                }
            }
        });
    }
}
