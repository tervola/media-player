package ui.mpbutton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import ui.mptab.PlayListTab;

/**
 * Created by user on 9/8/2017.
 */
public class LoadPlayListButton extends AbstractButton {
    private final static String TEXT = "Load PL";
    private final static String TOOLTIP = "loading play list";

    public LoadPlayListButton() {
        super.setTooltip(new Tooltip(TOOLTIP));
        super.setText(TEXT);
    }

    @Override
    protected void setOnAction() {
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                if (LoadPlayListButton.this.mediaResourcePicker.isClosed()) {

                    final boolean isOnline = LoadPlayListButton.this.mediaResourcePicker.showLoadPlayListDialog();
                    LoadPlayListButton.this.mediaResourcePicker.setCachedPlayList(isOnline);

                    if (isOnline) {
                        loadAndUpdateTab(onlineTab);
                    } else {
                        loadAndUpdateTab(localTab);
                    }
                }
            }
        });
    }

    private void loadAndUpdateTab(PlayListTab tab) {
        tab.loadFromSavedPlayList();
        tab.load();
        setActiveTab((Tab) tab);
    }
}
