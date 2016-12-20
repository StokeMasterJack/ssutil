package com.smartsoft.ssutil;

import com.google.common.io.Closeables;

import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.IOException;

public class Close {

    public static void closeQuietly(@Nullable Closeable closeable) {
        try {
            Closeables.close(closeable, true);
        } catch (IOException e) {
            System.out.println("IOException should not have been thrown.");
            e.printStackTrace();
        }
    }

}
