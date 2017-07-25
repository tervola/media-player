package ui.mpbutton;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

/**
 * Created by user on 7/14/2017.
 */
public abstract class AbstractButton extends Button {

    public AbstractButton() {
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
}
