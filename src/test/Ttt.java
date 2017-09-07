package test;

import java.io.*;
import java.util.Properties;

/**
 * Created by user on 7/10/2017.
 */
public class Ttt {
    public static void main(String[] args) throws IOException {
        OutputStream outputStream = new FileOutputStream("mp.properties");

        Properties properties = new Properties();
        properties.setProperty("playlist","------");

        properties.store(outputStream,null);
        outputStream.close();

//        InputStream inputStream = new FileInputStream("mp.properties");
//        properties.load(inputStream);
//        System.out.println(properties.getProperty("playlist"));

        InputStream inputStream = Ttt.class.getClassLoader().getResourceAsStream("config.properties");
        properties.load(inputStream);
        System.out.println(properties.getProperty("playlist"));




    }
}
