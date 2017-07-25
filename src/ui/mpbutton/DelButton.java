package ui.mpbutton;

import javafx.scene.control.Tooltip;

/**
 * Created by user on 7/18/2017.
 */
public class DelButton extends AbstractButton {

    private final static String TEXT = "Remove";
    private final static String TOOLTIP = "Remove from playlist";

    @Override
    protected void setOnAction() {
        super.setTooltip(new Tooltip(TOOLTIP));
        super.setText(TEXT);
    }
}
