package ui.mpbutton;

/**
 * Created by user on 7/14/2017.
 */
public class PauseButton extends AbstractButton {

    private final static char IMAGE_CODE = 10073;
    private final String text;

    public PauseButton() {
        setImagedButtonTooltip();
        this.text = getText(new char[]{IMAGE_CODE, IMAGE_CODE});
        setText(this.text);
    }

    @Override
    public void setImagedButtonTooltip() {
        super.setImagedButtonTooltip();
    }

    @Override
    protected void setOnAction() {

    }
}
