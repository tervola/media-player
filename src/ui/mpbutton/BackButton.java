package ui.mpbutton;

/**
 * Created by user on 7/14/2017.
 */
public class BackButton extends AbstractButton {

    private final static char IMAGE_CODE = 9194;
    private final String text;

    public BackButton() {
        setImagedButtonTooltip();
        this.text = getText(new char[]{IMAGE_CODE});
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
