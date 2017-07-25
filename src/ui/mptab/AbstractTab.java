package ui.mptab;

import javafx.scene.control.Tab;

/**
 * Created by user on 7/18/2017.
 */
public abstract class AbstractTab extends Tab {
    public AbstractTab() {
        setHBox();
    }

    protected abstract void setHBox();
}
