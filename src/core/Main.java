package core;

import ui.mpbutton.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    private static final String CSS_FILE = "../res/style.css";
    private static final AbstractButton PLAY = new PlayButton();
    private static final AbstractButton BACK = new BackButton();
    private static final AbstractButton PAUSE = new PauseButton();
    private static final AbstractButton STOP = new StopButton();
    private static final AbstractButton PLAY_LIST = new PlayListButton();

    private static final String TITLE = "Advanced Media Player";
    private final PlayList playList = PlayList.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        final GridPane grid = createRootGrid();
        showScene(primaryStage, grid);
    }

    private void showScene(Stage primaryStage, GridPane grid) {
        final Scene scene = createScene(grid);
        primaryStage.setTitle("Media Player");
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(!playList.isClosed()) {
                    playList.closePlayList();
                }
            }
        });

        primaryStage.show();
    }

    private GridPane defineMainPanel() {
        final GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.BOTTOM_CENTER);
        gridPane.setHgap(15);
        gridPane.setVgap(25);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        return gridPane;
    }

    private GridPane createRootGrid() {
        final GridPane rootMainPanel = defineMainPanel();

        final Text title = new Text(TITLE);
        rootMainPanel.add(title, 0, 0);


        final Separator topSeparator = getSeparator();
        rootMainPanel.getChildren().add(topSeparator);
        setPosition(topSeparator, 0, 2, 3);

        ButtonBar buttonBar = getButtonBar();
        rootMainPanel.getChildren().add(buttonBar);

        final VBox vBox = getVbox(buttonBar);
        rootMainPanel.getChildren().add(vBox);
        setPosition(vBox, 0, 3, 0);

        return rootMainPanel;
    }

    private VBox getVbox(final ButtonBar buttonBar) {
        final VBox vBox = new VBox(10);
        vBox.getChildren().addAll(buttonBar);
        vBox.setStyle("-fx-background-color: lightcoral; -fx-padding: 10;");
        return vBox;
    }

    private void setPosition(final Node separator, int columIndex, int rowIndex, int span) {
        GridPane.setConstraints(separator, columIndex, rowIndex);
        if (span > 0) {
            GridPane.setColumnSpan(separator, span);
        }
    }

    private ButtonBar getButtonBar() {
        final ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(PLAY, PAUSE, STOP, BACK, PLAY_LIST);
        return buttonBar;
    }

    private Separator getSeparator() {
        final Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        separator.setValignment(VPos.CENTER);
        separator.setStyle("-fx-border-style: solid; -fx-border-width: 1px; -fx-background-color: red");
        return separator;
    }

    private Scene createScene(final GridPane grid) {
        final Scene scene = new Scene(grid, 500, 175);
        scene.getStylesheets().add(Main.class.getResource(CSS_FILE).toExternalForm());
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
