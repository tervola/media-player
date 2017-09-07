package core.controls;

import core.MediaRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by user on 8/7/2017.
 */
public class FileController {

    private static FileController INSTANCE;
    private Properties properties;

    private static String PROPERTY_FILE = "config.properties";
    private static String PROPERTY_FIELD = "playlist";
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

    public List<MediaRecord> getPlayListFromConfig() throws IOException {
        String path = this.properties.get(PROPERTY_FIELD).toString();
        List<String> lines = new ArrayList<>();
        if (!path.isEmpty()) {
            lines = Files.readAllLines(Paths.get(path));
        }

        List<MediaRecord> records = new ArrayList<>();
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                String[] split = line.split(";");
                MediaRecord m = new MediaRecord();
                m.setDisplayName(split[0]);
                m.setPath(split[0]);
                records.add(m);
            }
        }
        return records;
    }

    private void loadProperties() {
        InputStream inputStream = null;
        try {
            if (Paths.get(PROPERTY_FILE).toFile().exists()) {
                inputStream = new FileInputStream(new File(PROPERTY_FILE));
            } else {
                inputStream = FileController.this.getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE);

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
            saveProperties(PROPERTY_FIELD, null);
        }

    }

    public void saveProperties(String key, String value) {
        OutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(new File(PROPERTY_FILE));
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

    public void addNewMediaRecords(List<MediaRecord> newRecords) {
        this.mediaRecords = newRecords;
    }

    public List<MediaRecord> getMediaRecords() {
        return mediaRecords;
    }
}
