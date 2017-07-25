package ui.mpbutton;

import javafx.scene.control.Tooltip;

/**
 * Created by user on 7/18/2017.
 */
public class AddOnlineButton extends AbstractButton {

    private final static String TEXT = "Add Online..";
    private final static String TOOLTIP = "Add from YouTube link to play list";

    @Override
    protected void setOnAction() {
        super.setTooltip(new Tooltip(TOOLTIP));
        super.setText(TEXT);
    }
}
