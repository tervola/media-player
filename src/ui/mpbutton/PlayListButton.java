package ui.mpbutton;

import core.PlayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tooltip;

/**
 * Created by user on 7/17/2017.
 */
public class PlayListButton extends AbstractButton {

    private final static String TEXT = "play list";
    private final static String TOOLTIP = "show / hide play list";

    private final PlayList playList;


    public PlayListButton() {
        super.setTooltip(new Tooltip(TOOLTIP));
        super.setText(TEXT);
        this.playList = PlayList.getInstance();
    }

    @Override
    protected void setOnAction() {
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                if (playList.isClosed()) {
                    playList.showPlayList();
                }
            }
        });
    }
}
