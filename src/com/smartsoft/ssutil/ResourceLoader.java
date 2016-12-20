package com.smartsoft.ssutil;


import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;


public class ResourceLoader {


    /**
     * @param cls
     * @param localName
     * @return returns the inputStream for a file in the same dir as cls
     */
    public static InputStream getLocalResource(Class cls, String localName) {
        String fullResourceName = getFullResourceName(cls, localName);
        return cls.getClassLoader().getResourceAsStream(fullResourceName);
    }


    public static InputStream getLocalResource(Object o, String localName) {
        String resourceName = getFullResourceName(o.getClass(), localName);
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
    }

    public static String getString(String resourceName) throws ResourceLoaderException {
        return getResourceAsString(resourceName);
    }


    public static String getString(Class contextClass, String localName) throws ResourceLoaderException {
        return getLocalResourceAsString(contextClass, localName);
    }

    public static String getString(Object contextObject, String localName) throws ResourceLoaderException {
        return getLocalResourceAsString(contextObject.getClass(), localName);
    }

    public static String getLocalResourceAsString(Class cls, String localName) throws ResourceLoaderException {
        URL u = Resources.getResource(cls, localName);
        try {
            return Resources.toString(u, Charsets.UTF_8);
        } catch (IOException e) {
            throw new ResourceLoaderException(u.toString(), e);
        }
    }

    /**
     * Untested
     */
    public static byte[] getLocalResourceAsBytes(Class cls, String localName) throws ResourceLoaderException {
        String resourceName = ResourceLoader.getFullResourceName(cls, localName);
        return ResourceLoader.getResourceAsBytes(resourceName);
    }

    private static byte[] getResourceAsBytes(String resourceName) {
        InputStream is = loadResource(resourceName);
        byte[] b = null;
        try {
            int i = is.read(b);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        return b;
    }

    public static String getFullResourceName(Class cls, String localResourceName) {
        String packageName = getPackageName(cls);
        String resourceName = packageName + "/" + localResourceName;
        return resourceName;
    }

    public static String getPackageName(Class cls) {
        String className = cls.getName();
        String packageName = className.substring(0, className.lastIndexOf("."));
        packageName = packageName.replace('.', '/');
        return packageName;
    }

    public static InputStream loadResource(String resourceName) {
        InputStream is;
        Class<ResourceLoader> cls = ResourceLoader.class;
        ClassLoader loader = cls.getClassLoader();
        if (loader == null) {
            is = null;
        } else {
            is = loader.getResourceAsStream(resourceName);
        }
        if (is == null) {
            loader = Thread.currentThread().getContextClassLoader();
            is = loader.getResourceAsStream(resourceName);
            if (is == null) {
                ClassLoader parent = loader.getParent();
                is = parent.getResourceAsStream(resourceName);
                if (is == null) {
                    return ClassLoader.getSystemResourceAsStream(resourceName);
                } else {
                    return is;
                }
            } else {
                return is;
            }
        } else {
            return is;
        }
    }

    public static String getResourceAsString(String resourceName) {
        InputStream is = null;
        String s;
        try {
            is = loadResource(resourceName);
            if (is == null) throw new IllegalArgumentException("No such resource: " + resourceName);
            s = StringUtil.readAll(is);

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (java.io.IOException e) {
                    System.err.println(e);
                }
            }
        }
        return s;
    }

    public static Class loadClass(String name) throws ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        while (true) {
            try {
                if (classLoader == null) break;
                return classLoader.loadClass(name);
            } catch (ClassNotFoundException e) {
                classLoader = classLoader.getParent();
            }
        }
        classLoader = ResourceLoader.class.getClassLoader();
        return classLoader.loadClass(name);
    }

    public static void processStream(String resourceName, ResourceProcessorStream processor) throws Exception {
        InputStream is = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            is = loadResource(resourceName);
            bufferedInputStream = new BufferedInputStream(is);
            processor.process(bufferedInputStream);
        } finally {
            if (is != null) is.close();
            if (bufferedInputStream != null) bufferedInputStream.close();
        }
    }

    public static void processReader(String resourceName, ResourceProcessorReader processor) {
        InputStream is = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;
        try {
            is = loadResource(resourceName);
            if (is == null) {
                throw new RuntimeException("No such resource: [" + resourceName + "]");
            }
            reader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(reader);
            processor.process(bufferedReader);
        } finally {
            try {
                if (is != null) is.close();
                if (reader != null) reader.close();
                if (bufferedReader != null) bufferedReader.close();
            } catch (java.io.IOException e) {
                throw new RuntimeException("Problem closing streams", e);
            }
        }
    }

}
