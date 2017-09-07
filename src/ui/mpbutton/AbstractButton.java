package ui.mpbutton;

import core.controls.FilePicker;
import core.controls.PlayList;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import ui.mptab.LocalTab;

import java.io.File;

/**
 * Created by user on 7/14/2017.
 */
public abstract class AbstractButton extends Button {


    protected final PlayList playList;
    protected final FilePicker filePicker;
    protected final LocalTab localTab;

    public AbstractButton() {
        this.playList = PlayList.getInstance();
        this.filePicker = FilePicker.getInstance();
        this.localTab = playList.getTabController().getLocalTab();
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

    public void updateConfigFile(File newPlayList) {
        this.localTab.savePlayList(newPlayList.getAbsolutePath());
    }
}
