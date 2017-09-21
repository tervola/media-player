package ui.mpbutton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import ui.mptab.LocalTab;
import ui.mptab.OnlineTab;

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
                if (SavePlayListButton.this.mediaResourcePicker.isClosed()) {

                    String context = "";
                    if (tabController.getOppenedTab() instanceof OnlineTab){
                        context = SavePlayListButton.this.controller.getContentToSave(IS_ONLINE);
                    } else if (tabController.getOppenedTab() instanceof LocalTab) {
                        context = SavePlayListButton.this.controller.getContentToSave(!IS_ONLINE);
                    }

                    final File newPlayList = SavePlayListButton.this.mediaResourcePicker.showSaveDialog(context);
                    if (newPlayList != null) {
                        Tab oppenedTab = SavePlayListButton.this.tabController.getOppenedTab();
                        if (oppenedTab instanceof LocalTab) {
                            ((LocalTab) oppenedTab).updateConfigFile(newPlayList);
                        } else if (oppenedTab instanceof OnlineTab) {
                            ((OnlineTab) oppenedTab).updateConfigFile(newPlayList);
                        }
                    }
                }
            }
        });
    }
}
