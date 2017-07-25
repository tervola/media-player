package ui.mpbutton;

import javafx.scene.control.Tooltip;

/**
 * Created by user on 7/18/2017.
 */
public class CleanPlayListButton extends AbstractButton {

    private final static String TEXT = "Clean PL";
    private final static String TOOLTIP = "Clean play list";

    public CleanPlayListButton() {
        super.setTooltip(new Tooltip(TOOLTIP));
        super.setText(TEXT);
    }

    @Override
    protected void setOnAction() {

    }
}
