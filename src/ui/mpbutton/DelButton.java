package ui.mpbutton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;
import ui.mptab.LocalTab;

/**
 * Created by user on 7/18/2017.
 */
public class DelButton extends AbstractButton {

    private final static String TEXT = "Remove";
    private final static String TOOLTIP = "Remove from playlist";

    public DelButton() {
        super.setTooltip(new Tooltip(TOOLTIP));
        super.setText(TEXT);
    }

    @Override
    protected void setOnAction() {
        this.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle ( final ActionEvent event){
            LocalTab localTab = playList.getTabController().getLocalTab();
            localTab.removeChecked();
        }
    });
}
}
