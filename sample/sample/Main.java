package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    private Text actionText;
    private static String ROOT_SCC = "../css/";

    public Main() {
        this.actionText = setActionText();
    }

    private Text setActionText() {
        Text text = new Text();
        text.setId("action-text");
        return text;
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        final GridPane grid = getGrid();
        showScene(primaryStage, grid);
    }

    private void showScene(Stage primaryStage, GridPane grid) {
        Scene scene = createScene(grid);
        primaryStage.setTitle("JavaFX Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private Scene createScene(GridPane grid) {
        Scene scene = new Scene(grid, 300, 275);
        scene.getStylesheets().add(Main.class.getResource(ROOT_SCC + "login.css").toExternalForm());
        return scene;
    }

    private GridPane getGrid() {
        GridPane rootGridPanel = defineGrid();

        rootGridPanel.add(getSceneTitle(), 0, 0, 2, 1);
        rootGridPanel.add(getLabel("User Name:"), 0, 1);
        rootGridPanel.add(getTextField(), 1, 1);
        rootGridPanel.add(getLabel("Password:"), 0, 3);
        rootGridPanel.add(getTextField(), 1, 3);
        rootGridPanel.add(getHBoxWithButton(), 1, 4);
        rootGridPanel.add(getActionText(), 1, 6);

        return rootGridPanel;
    }

    private Node getActionText() {
        return this.actionText;
    }

    private HBox getHBoxWithButton() {
        Button button = new Button("Sing in");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                actionText.setText(String.valueOf(System.currentTimeMillis()));
            }
        });
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().add(button);
        return hBox;
    }

    private Node getTextField() {
        return new TextField();
    }

    private Node getLabel(final String label) {
        return new Label(label);
    }

    private Text getSceneTitle() {
        final Text scenetitle = new Text("Welcome");
        scenetitle.setId("scene-title");
        return scenetitle;
    }

    private GridPane defineGrid() {
        final GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        return gridPane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
