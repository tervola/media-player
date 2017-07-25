package core;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ui.mpbutton.*;

/**
 * Created by user on 7/17/2017.
 */
public class PlayList {

    private static AbstractButton ADD = new AddButton();
    private static AbstractButton ADD_ONLINE = new AddOnlineButton();
    private static AbstractButton DEL = new DelButton();
    private static AbstractButton CLEAN_PL = new CleanPlayListButton();

    private TabController controller;

    private static PlayList instance;
    private boolean closed = true;
    private final Stage stage;
    private Text textTitle;

    private PlayList() {
        this.controller = new TabController();
        this.stage = createStage();
        this.textTitle = new Text();
    }

    public static PlayList getInstance() {
        if (instance == null) {
            instance = new PlayList();
        }
        return instance;
    }

    public void showPlayList() {
        final GridPane rootPlaylistPanel = createRootPlaylistGrid();
        rootPlaylistPanel.setPrefHeight(10000);

        BorderPane borderPane = new BorderPane();
        StackPane top = new StackPane();
        top.setPrefHeight(5);
        top.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        top.getChildren().add(this.textTitle);

        StackPane center = new StackPane();
        center.setPrefHeight(550);

        TabPane tabPane = createTabPanel();
        tabPane.setStyle("-fx-border-color: black; -fx-border-width: 5px;");
        center.getChildren().add(tabPane);

        StackPane bottom = new StackPane();
        bottom.setPrefHeight(20);
        bottom.setStyle("-fx-border-color: red; -fx-border-width: 2px;");

        final ButtonBar buttonBar = createButtonBar();
        bottom.getChildren().add(buttonBar);

        borderPane.setTop(top);
        borderPane.setCenter(center);
        borderPane.setBottom(bottom);

        rootPlaylistPanel.getChildren().add(borderPane);
        showScene(rootPlaylistPanel);
    }

    private BorderPane createBorderPanel(TabPane tabPane) {
        Scene scene = new Scene(new Group(), 400, 100, Color.AQUAMARINE);

        final BorderPane borderPane = new BorderPane();
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        borderPane.setCenter(tabPane);

        return borderPane;
    }

    private TabPane createTabPanel() {
        final TabPane tabPane = new TabPane();

        tabPane.getTabs().addAll(controller.getTabsList());
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                PlayList.this.textTitle.setText(newValue.getText());
            }
        });

        return tabPane;
    }

    private ButtonBar createButtonBar() {
        final ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(ADD, ADD_ONLINE, DEL, CLEAN_PL);
        return buttonBar;
    }

    public void closePlayList() {
        this.stage.hide();
        closed();
    }

    private void closed() {
        this.closed = true;
    }

    private void openned() {
        this.closed = false;
    }

    private GridPane createRootPlaylistGrid() {
        final GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.BOTTOM_LEFT);
//        gridPane.setHgap(100);
//        gridPane.setVgap(1);
        gridPane.setPadding(new Insets(25, 25, 5, 5));
        return gridPane;
    }

    private void showScene(GridPane grid) {
        final Scene scene = createScene(grid);
        this.stage.setScene(scene);
        this.stage.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                PlayList.this.textTitle.setText(PlayList.this.controller.getOppenedTab().getText());
            }
        });
        this.stage.show();
    }

    private Stage createStage() {
        Stage stage = new Stage();
        stage.setTitle("Play List");
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                closed();
            }
        });

        stage.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                openned();
            }
        });
        return stage;
    }

    private Scene createScene(final GridPane grid) {
        final Scene scene = new Scene(grid, 400, 600);
        //TODO: create size of playlist
//        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setX(primaryScreenBounds.getMaxX()/2 + 200);
//        stage.setY(primaryScreenBounds.getMinY()/2 );
//        scene.getStylesheets().add(Main.class.getResource(CSS_FILE).toExternalForm());
        return scene;
    }

    public boolean isClosed() {
        return this.closed;
    }
}
