package sample.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler {

    public static String read(String path)
    {

        //file reader to String
        try {
            File file = new File(path);


            String st = new String(Files.readAllBytes(
                    Paths.get(file.getAbsolutePath())));

            return st ;

        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }

    }


    public static void writeIntoFile(String content , String path){


        //write to file from string input

        try {
            Files.write(Paths.get(path), content.getBytes());
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
