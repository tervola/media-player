package ui.mptab;

import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 * Created by user on 7/18/2017.
 */
public class OnlineTab extends AbstractTab {

    private final static String TEXT = "Online Media";
    private final static String TOOLTIP = "Online media from YouTube";


    public OnlineTab() {
        setText(TEXT);
        setTooltip(new Tooltip(TOOLTIP));
    }

    @Override
    protected void setHBox() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        this.setContent(hBox);
    }
}
