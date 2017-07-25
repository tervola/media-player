package ui.mpbutton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * Created by user on 7/18/2017.
 */
public class AddButton extends AbstractButton {

    private final static String TEXT = "Add..";
    private final static String TOOLTIP = "Add items to play list";
    private final Stage stage;
    private final Desktop desktop;
    private final FileChooser fileChooser;

    public AddButton() {
        super.setTooltip(new Tooltip(TOOLTIP));
        super.setText(TEXT);
        this.stage = new Stage();
        this.desktop = Desktop.getDesktop();
        this.fileChooser = createFileChooser();
    }

    private FileChooser createFileChooser() {
        final FileChooser fileChooser = new FileChooser();
        final Button openButton = new Button("Open Media File...");
        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        List<File> files = AddButton.this.fileChooser.showOpenMultipleDialog(AddButton.this.stage);
                        if(files != null) {
                            for (File file : files) {
//                                openFile();
                            }

                        }
                    }
                }
        );
        return null;
    }

    @Override
    protected void setOnAction() {

    }


}
