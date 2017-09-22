package core.controls;

import core.MediaRecord;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by user on 8/7/2017.
 */
public class MediaResourcePicker {
    private static MediaResourcePicker instance;
    private final Stage stage;

    private boolean closed;
    private final FileController controller;

    public MediaResourcePicker() {
        this.stage = new Stage();
        this.closed = true;
        this.controller = FileController.getInstance();
    }

    public static MediaResourcePicker getInstance() {
        if (instance == null) {
            instance = new MediaResourcePicker();
        }
        return instance;
    }

    public boolean showLoadDialog() {
        openned();
        boolean success = false;
        List<File> chosen = pickFiles();

        if (chosen.size() > 0) {
            List<MediaRecord> newRecords = new ArrayList<>();
            for (File file : chosen) {
                MediaRecord m = new MediaRecord();
                m.setPath(file.getAbsolutePath());
                m.setDisplayName(file.getName());
                m.setOnline(false);
                newRecords.add(m);
            }

            this.controller.setMediaRecords(newRecords);
            success = true;
        }
        closed();
        stage.close();
        return success;
    }

    public boolean showLoadPlayListDialog() {
        openned();
        boolean isOnline = false;
        File chosen = pickFile();

        if (chosen != null) {
            List<MediaRecord> loadedRecords = this.controller.getPlayListFromSavedPL(chosen.getPath());
            if(loadedRecords.size() > 0) {
                isOnline = loadedRecords.get(0).isOnline();
                if (isOnline){
                    this.controller.setOnlineMediaRecords(loadedRecords);
                } else {
                    this.controller.setMediaRecords(loadedRecords);
                }
            }
        }
        closed();
        stage.close();
        return isOnline;
    }

    public void setCachedPlayList(final boolean isOnline){
        this.controller.setCachedPlayList(isOnline);
    }

    private File pickFile() {
        final FileChooser fileChooser = getSingleFileChooser();
        return fileChooser.showOpenDialog(this.stage);
    }

    private List<File> pickFiles() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3", "*.mp3"));
        List<File> files = fileChooser.showOpenMultipleDialog(stage);

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
        final FileChooser fileChooser = getSingleFileChooser();
        File file = fileChooser.showSaveDialog(this.stage);

        if (file != null) {
            saveFile(content, file);
        }

        closed();
        stage.close();

        return file;
    }

    private FileChooser getSingleFileChooser() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PL", "*.pl"));
        return fileChooser;
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

    public boolean showLoadOnlineDialog() {
        openned();
        boolean success = false;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add URL");
        dialog.setHeaderText("Adding Online Resource URI");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            MediaRecord m = new MediaRecord();
            m.setDisplayName(result.get().trim());
            m.setUri(result.get().trim());
            m.setOnline(true);
            this.controller.addOnlineMediaRecord(m);
            success = true;
        }
        closed();
        stage.close();
        return success;
    }
}
