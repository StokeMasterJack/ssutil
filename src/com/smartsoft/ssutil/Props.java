package com.smartsoft.ssutil;

import com.google.common.collect.Maps;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import static com.smartsoft.ssutil.Close.closeQuietly;

public class Props {

    public static Map<String, String> readFromFile(File file) {
        Properties p = new Properties();

        FileReader r = null;
        try {
            r = new FileReader(file);
            p.load(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            closeQuietly(r);
        }

        return Maps.fromProperties(p);


    }

}
