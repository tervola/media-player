package ui.mpbutton;

import core.controls.FileController;
import core.controls.MediaResourcePicker;
import core.controls.PlayList;
import core.controls.TabController;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import ui.mptab.LocalTab;
import ui.mptab.OnlineTab;

/**
 * Created by user on 7/14/2017.
 */
public abstract class AbstractButton extends Button {


    protected final static boolean IS_ONLINE = true;
    protected final PlayList playList;
    protected final MediaResourcePicker mediaResourcePicker;
    protected final LocalTab localTab;
    protected final OnlineTab onlineTab;
    protected final TabController tabController;
    protected FileController controller;

    public AbstractButton() {
        this.playList = PlayList.getInstance();
        this.mediaResourcePicker = MediaResourcePicker.getInstance();
        this.localTab = playList.getTabController().getLocalTab();
        onlineTab = playList.getTabController().getOnlineTab();
        this.controller = FileController.getInstance();
        this.tabController = TabController.getInstance();
        setOnAction();
    }

    protected void setImagedButtonTooltip() {
        final String simpleName = this.getClass().getSimpleName().replace("Button","");
        this.setTooltip(new Tooltip(simpleName));
    }

    protected abstract void setOnAction();

    protected String getText(char[] arg) {
        StringBuilder bulder = new StringBuilder();
        for (char c : arg) {
            bulder.append(c);
        }

        return bulder.toString();
    }

    protected void setActiveTab(Tab tab) {
        final SingleSelectionModel<Tab> selectionModel = playList.getTabPane().getSelectionModel();
        selectionModel.select(tab);
    }
}
