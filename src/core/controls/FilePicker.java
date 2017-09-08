package core.controls;

import core.MediaRecord;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 8/7/2017.
 */
public class FilePicker {
    private static FilePicker instance;
    private final Stage stage;


    private boolean closed;
    private final FileController controller;

    public FilePicker() {
        this.stage = new Stage();
        this.closed = true;
        this.controller = FileController.getInstance();
    }

    public static FilePicker getInstance() {
        if (instance == null) {
            instance = new FilePicker();
        }
        return instance;
    }

    public void showLoadDialog() {
        openned();
        List<File> chosen = pickFiles();
        List<MediaRecord> newRecords = new ArrayList<>();
        for (File file : chosen) {
            MediaRecord m = new MediaRecord();
            m.setPath(file.getAbsolutePath());
            m.setDisplayName(file.getName());
            newRecords.add(m);
        }

        this.controller.setMediaRecords(newRecords);
        closed();
        stage.close();
    }

    private List<File> pickFiles() {
        final FileChooser fileChooser = new FileChooser();
        List<File> files = fileChooser.showOpenMultipleDialog(stage);

//        if (files != null) {
//            for (File file : files) {
//                files.add(file);
//            }
//        }
        if (files == null) {
            return new ArrayList<>();
        } else {
            return files;
        }
    }

    private void closed() {
        this.closed = true;
    }

    private void openned() {
        this.closed = false;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public File showSaveDialog(String content) {
        openned();
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(this.stage);

        if (file != null) {
            saveFile(content, file);
        }

        closed();
        stage.close();

        return file;
    }

    private void saveFile(String content, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            //TODO move all alerts!
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
