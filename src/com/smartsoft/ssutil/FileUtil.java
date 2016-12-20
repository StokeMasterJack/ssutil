package com.smartsoft.ssutil;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileUtil {

    public static File createDirNotExists(File dirName) {
        if (!dirName.exists()) {
            try {
                Files.createParentDirs(dirName);
                dirName.mkdir();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return dirName;
    }

    public static String fileToString(File file)  {
        try {
            return Files.toString(file, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void stringToFile(File file, String s) {
        try {
            Files.write(s, file, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createFile(File f) {
        try {
            Files.touch(f);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
