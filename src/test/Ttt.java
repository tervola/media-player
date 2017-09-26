package test;

import java.io.File;
import java.io.IOException;

/**
 * Created by user on 7/10/2017.
 */
public class Ttt {
    public static void main(String[] args) throws IOException {
//        OutputStream outputStream = new FileOutputStream("mp.properties");
//
//        Properties properties = new Properties();
//        properties.setProperty("playlist","------");
//
//        properties.store(outputStream,null);
//        outputStream.close();
//
////        InputStream inputStream = new FileInputStream("mp.properties");
////        properties.load(inputStream);
////        System.out.println(properties.getProperty("playlist"));
//
//        InputStream inputStream = Ttt.class.getClassLoader().getResourceAsStream("config.properties");
//        properties.load(inputStream);
//        System.out.println(properties.getProperty("playlist"));


//        ArrayList arrayList = new ArrayList(10);
//        arrayList.ensureCapacity(20);

        File file = new File("c:\\tmp\\123.pl");
        String name = file.getName();
        String substring = name.substring(name.indexOf(".")+1);
        System.out.println(substring);


    }
}
