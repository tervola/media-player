package ui.mpbutton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import ui.mptab.PlayListTab;

/**
 * Created by user on 7/18/2017.
 */
public class AddButton extends AbstractButton {

    private final static String TEXT = "Add..";
    private final static String TOOLTIP = "Add items to play list";

    public AddButton() {
        super.setTooltip(new Tooltip(TOOLTIP));
        super.setText(TEXT);
    }

    @Override
    protected void setOnAction() {
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                if (AddButton.this.mediaResourcePicker.isClosed()) {
                    boolean isPickedNewRecords = AddButton.this.mediaResourcePicker.showLoadDialog();
                    if (isPickedNewRecords) {
                        PlayListTab tab = playList.getTabController().getLocalTab();
                        tab.add();
                        setActiveTab((Tab) tab);
                    }
                }
            }
        });
    }
}
