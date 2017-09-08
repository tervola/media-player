package core.controls;

import core.MediaRecord;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by user on 8/7/2017.
 */
public class FileController {

    private static FileController INSTANCE;
    private Properties properties;

    private static String PROPERTY_FILE_PROPERTY = "config.properties";
    private static String PROPERTY_FIELD_PLAYLIST = "playlist";
    private static String PROPERTY_FIELD_CACHED_FILE = "cache";
    private static String PROPERTY_FIELD_CACHED_FILE_VALUE = "pl.tmp";
    private List<MediaRecord> mediaRecords;

    private FileController() {
        this.properties = new Properties();
        loadProperties();
    }

    public static FileController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FileController();
        }
        return INSTANCE;
    }

    public List<MediaRecord> getPlayListFromConfig() {
        String path = this.properties.get(PROPERTY_FIELD_PLAYLIST).toString();
        return readFile(path);
    }

    private List<MediaRecord> readFile(String path)  {
        List<String> lines = new ArrayList<>();
        if (!path.isEmpty()) {
            try {
                lines = Files.readAllLines(Paths.get(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<MediaRecord> records = new ArrayList<>();
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                String[] split = line.split(";");
                MediaRecord m = new MediaRecord();
                m.setDisplayName(split[0]);
                m.setPath(split[1]);
                records.add(m);
            }
        }
        return records;

    }

    private void loadProperties() {
        InputStream inputStream = null;
        try {
            if (Paths.get(PROPERTY_FILE_PROPERTY).toFile().exists()) {
                inputStream = new FileInputStream(new File(PROPERTY_FILE_PROPERTY));
            } else {
                inputStream = FileController.this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_PROPERTY);

            }
            this.properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //TODO: generate default properties file
        if (this.properties.isEmpty()) {
            saveProperties(PROPERTY_FIELD_PLAYLIST, null);
        }

    }

    public void saveProperties(String key, String value) {
        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(new File(PROPERTY_FILE_PROPERTY));
            this.properties.setProperty(key, value);
            this.properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void setMediaRecords(List<MediaRecord> newRecords) {
        this.mediaRecords = newRecords;
    }

    public List<MediaRecord> getMediaRecords() {
        return this.mediaRecords;
    }

    public List<MediaRecord> getCachedPlayList() {
        String tmp = this.properties.get(PROPERTY_FIELD_CACHED_FILE).toString();
        return readFile(tmp);
    }

    public void setCachedPlayList(ObservableList<MediaRecord> tableData) throws IOException {
        String tmp = this.properties.get(PROPERTY_FIELD_CACHED_FILE).toString();
        Files.deleteIfExists(Paths.get(tmp));
        File cachedPlayListFile = new File(tmp);
        StringBuilder stringBuilder = new StringBuilder();
        for (MediaRecord mediaRecord : this.mediaRecords) {
            stringBuilder.append(mediaRecord.getDisplayName());
            stringBuilder.append(";");
            stringBuilder.append(mediaRecord.getPath());
            stringBuilder.append("\r\n");
        }
        Files.write(cachedPlayListFile.toPath(), stringBuilder.toString().getBytes(), StandardOpenOption.CREATE);
    }
}
