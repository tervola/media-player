package ui.mpbutton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import ui.mptab.LocalTab;

import java.io.IOException;

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
                if (AddButton.this.filePicker.isClosed()) {
                    AddButton.this.filePicker.showLoadDialog();
                    LocalTab localTab = playList.getTabController().getLocalTab();
                    try {
                        localTab.add();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
