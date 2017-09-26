package core.controls;

import core.MediaRecord;

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
    private static String PROPERTY_FIELD_PLAYLIST_LOCAL = "playlistlocal";
    private static String PROPERTY_FIELD_PLAYLIST_ONLINE = "playlistonline";
    private static String PROPERTY_FIELD_CACHED_LOCAL_FILE = "cache_local";
    private static String PROPERTY_FIELD_CACHED_ONLINE_FILE = "cache_online";
    private static String PROPERTY_FIELD_CACHED_FILE_VALUE = "pl.tmp";
    private List<MediaRecord> mediaRecords = new ArrayList<>();
    private List<MediaRecord> onlineMediaRecords = new ArrayList<>();
    private int currentSelectedLocalRecord = 0;
    private int currentSelectedOnlineRecord = 0;

    private FileController() {
        System.out.println("Created!");
        this.properties = new Properties();
        loadProperties();
    }

    public static FileController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FileController();
        }
        return INSTANCE;
    }

    public List<MediaRecord> getPlayListFromSavedPL(String filePath) {
        return readFile(filePath);
    }

    private List<MediaRecord> readFile(String path) {
        List<String> lines = new ArrayList<>();
        List<MediaRecord> records = new ArrayList<>();

        File file = new File(path);
        if (file.exists()) {
            try {
                lines = Files.readAllLines(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    String[] split = line.split(";");
                    MediaRecord m = new MediaRecord();
                    m.setDisplayName(split[0]);
                    final Boolean isOnline = Boolean.valueOf(split[2]);
                    m.setOnline(isOnline);
                    if (isOnline) {
                        m.setUri(split[1]);
                    } else {
                        m.setPath(split[1]);
                    }

                    records.add(m);
                }
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
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

    public void setOnlineMediaRecords(List<MediaRecord> onlineMediaRecords) {
        this.onlineMediaRecords = onlineMediaRecords;
    }

    public List<MediaRecord> getMediaRecords() {
        return this.mediaRecords;
    }

    public List<MediaRecord> getCachedPlayList(boolean isOnline) {
        String tmp;
        if (isOnline) {
            tmp = this.properties.get(PROPERTY_FIELD_CACHED_ONLINE_FILE).toString();
        } else {
            tmp = this.properties.get(PROPERTY_FIELD_CACHED_LOCAL_FILE).toString();
        }
        return readFile(tmp);
    }

    private void createCacheFile(final String tmp, final boolean isOnline) {
        try {
            Files.deleteIfExists(Paths.get(tmp));
            File cachedPlayListFile = new File(tmp);
            final String content = getContentToSave(isOnline);
            Files.write(cachedPlayListFile.toPath(), content.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.out.println("error during write to chached PL");
        }
    }

    public String getContentToSave(final boolean isOnline) {
        StringBuilder stringBuilder = new StringBuilder();

        if (isOnline) {
            for (MediaRecord mediaRecord : getOnlineMediaRecord()) {
                stringBuilder.append(mediaRecord.getDisplayName());
                stringBuilder.append(";");
                stringBuilder.append(mediaRecord.getUri());
                stringBuilder.append(";");
                stringBuilder.append(mediaRecord.isOnline());
                stringBuilder.append("\r\n");
            }
        } else {
            for (MediaRecord mediaRecord : getMediaRecords()) {
                stringBuilder.append(mediaRecord.getDisplayName());
                stringBuilder.append(";");
                stringBuilder.append(mediaRecord.getPath());
                stringBuilder.append(";");
                stringBuilder.append(mediaRecord.isOnline());
                stringBuilder.append("\r\n");
            }
        }
        return stringBuilder.toString();
    }

    public void addOnlineMediaRecord(MediaRecord m) {
        this.onlineMediaRecords.add(m);
    }

    public List<MediaRecord> getOnlineMediaRecord() {
        return this.onlineMediaRecords;
    }

    public void addMediaRecord(MediaRecord r) {
        this.mediaRecords.add(r);
    }

    public void setCachedPlayList(boolean isOnline) {
        final String tmp;
        if (isOnline) {
            tmp = this.properties.get(PROPERTY_FIELD_CACHED_ONLINE_FILE).toString();
        } else {
            tmp = this.properties.get(PROPERTY_FIELD_CACHED_LOCAL_FILE).toString();
        }
        createCacheFile(tmp, isOnline);
    }

    public void setCurrentSelectedLocalRecord(int index) {
        this.currentSelectedLocalRecord = index;
    }

    public int getCurrentSelectedLocalRecordIndex() {
        return this.currentSelectedLocalRecord;
    }

    public void setCurrentSelectedOnlineRecord(int index) {
        this.currentSelectedOnlineRecord = index;
    }

    public int getCurrentSelectedOnlineRecordIndex() {
        return this.currentSelectedOnlineRecord;
    }
}
