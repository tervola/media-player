package core.controls;

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
import ui.mptab.LocalTab;
import ui.mptab.OnlineTab;

/**
 * Created by user on 7/17/2017.
 */
public class PlayList {

    private static AbstractButton ADD = new AddButton();
    private static AbstractButton ADD_ONLINE = new AddOnlineButton();
    private static AbstractButton DEL = new DelButton();
    private static AbstractButton CLEAN_PL = new CleanPlayListButton();
    private static AbstractButton SAVE_PL = new SavePlayListButton();
    private static AbstractButton LOAD_PL = new LoadPlayListButton();

    private TabController tabController;

    private static PlayList instance;
    private boolean closed = true;
    private final Stage stage;
    private Text textTitle;
    private TabPane tabPane;

    private PlayList() {
        this.tabController = TabController.getInstance();
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


        this.tabPane = createTabPanel();
        tabPane.setStyle("-fx-border-color: black; -fx-border-width: 5px;");
        center.getChildren().add(this.tabPane);

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

        tabPane.getTabs().addAll(tabController.getTabsList());
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                PlayList.this.textTitle.setText(newValue.getText());
                if (newValue instanceof LocalTab) {
                    ((LocalTab) newValue).load();
                } else if (newValue instanceof OnlineTab) {
                    ((OnlineTab) newValue).load();
                }
            }
        });

        return tabPane;
    }

    private ButtonBar createButtonBar() {
        final ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(ADD, ADD_ONLINE, DEL, CLEAN_PL, SAVE_PL, LOAD_PL);
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
        gridPane.setPadding(new Insets(25, 25, 5, 5));
        return gridPane;
    }

    private void showScene(GridPane grid) {
        final Scene scene = createScene(grid);
        this.stage.setScene(scene);
        this.stage.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Tab oppenedTab = PlayList.this.tabController.getOppenedTab();
                PlayList.this.textTitle.setText(oppenedTab.getText());

                if (oppenedTab instanceof LocalTab) {
                    ((LocalTab) oppenedTab).load();
                } else if (oppenedTab instanceof OnlineTab) {
                    ((OnlineTab) oppenedTab).load();
                }
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

        stage.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                openned();
            }
        });
        stage.setWidth(560);
        return stage;
    }

    private Scene createScene(final GridPane grid) {
        final Scene scene = new Scene(grid, 400, 600);
        //TODO: create size of playlist
        return scene;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public TabController getTabController() {
        return this.tabController;
    }

    public TabPane getTabPane() {
        return this.tabPane;
    }
}
