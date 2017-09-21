package ui.mpbutton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import ui.mptab.LocalTab;
import ui.mptab.OnlineTab;

import java.util.Optional;

/**
 * Created by user on 7/18/2017.
 */
public class CleanPlayListButton extends AbstractButton {

    private static String TEXT = "Clean PL";
    private static String TOOLTIP = "Clean play list";
    private static String WARNING_MESSAGE = "Are you sure to cleaning play list?";

    public CleanPlayListButton() {
        super.setTooltip(new Tooltip(TOOLTIP));
        super.setText(TEXT);
    }

    @Override
    protected void setOnAction() {
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setContentText(WARNING_MESSAGE);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Tab oppenedTab = tabController.getOppenedTab();
                    if(oppenedTab instanceof LocalTab){
                        ((LocalTab) oppenedTab).cleanPlayList();

                    } else if (oppenedTab instanceof OnlineTab) {
                        ((OnlineTab) oppenedTab).cleanPlayList();
                    }
                }

            }
        });
    }
}
