package ui.mpbutton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import ui.mptab.LocalTab;
import ui.mptab.OnlineTab;

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
                if (LoadPlayListButton.this.mediaResourcePicker.isClosed()) {

                    Tab oppenedTab = LoadPlayListButton.this.tabController.getOppenedTab();
                    boolean isLocalTab = oppenedTab instanceof LocalTab;

                    
//                    LoadPlayListButton.this.mediaResourcePicker.showLoadPlayListDialog(isLocalTab);
                    if (oppenedTab instanceof LocalTab) {
                        boolean isPickedNewRecords = LoadPlayListButton.this.mediaResourcePicker.showLoadPlayListDialog(false);
                        LocalTab tab = (LocalTab) oppenedTab;
                        tab.loadFromSavedPlayList();
                        controller.setCachedLocalPlayList();
                    } else if (oppenedTab instanceof OnlineTab) {
                        boolean isPickedNewRecords = LoadPlayListButton.this.mediaResourcePicker.showLoadPlayListDialog(true);
                        OnlineTab tab = (OnlineTab) oppenedTab;
                        tab.loadFromSavedPlayList();
                        controller.setCachedOnlinePlayList();
                    }

                }
            }
        });
    }
}
