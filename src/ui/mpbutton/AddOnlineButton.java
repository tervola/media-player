package ui.mpbutton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import ui.mptab.OnlineTab;

/**
 * Created by user on 7/18/2017.
 */
public class AddOnlineButton extends AbstractButton {

    private final static String TEXT = "Add Online..";
    private final static String TOOLTIP = "Add from YouTube link to play list";

    public AddOnlineButton() {
        super.setTooltip(new Tooltip(TOOLTIP));
        super.setText(TEXT);
    }

    @Override
    protected void setOnAction() {
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                if (AddOnlineButton.this.mediaResourcePicker.isClosed()) {
                    boolean isPickedNewRecords = AddOnlineButton.this.mediaResourcePicker.showLoadOnlineDialog();
                    if (isPickedNewRecords) {
                        OnlineTab onlineTab = playList.getTabController().getOnlineTab();
                        onlineTab.add();
                    }
                }
            }
        });
    }
}
