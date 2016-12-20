package com.smartsoft.ssutil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ResourceLoaderSafe {

    /**
     *
     * example:
     *
     *      /myProject/src/a/b/c.xml
     *
     *      InputStream is = ResourceLoaderSafe.loadResource("a/b/c.xml",this.getClass());
     *
     * @param fullResourceName full path relative to classpath root (usually src)
     * @param contextClasses classes to use a reference class loader
     * @return
     * @throws IllegalStateException  if it fails to load the resource
     */
    public static InputStream loadResource(String fullResourceName, Class... contextClasses) throws IllegalStateException {

        InputStream is;

        ArrayList<Class> contextsToTry = new ArrayList<Class>();
        if (contextClasses != null && contextClasses.length != 0) {
            contextsToTry.addAll(Arrays.asList(contextClasses));
        }
        contextsToTry.add(ResourceLoaderSafe.class);

        for (Class cls : contextsToTry) {
            is = cls.getClassLoader().getResourceAsStream(fullResourceName);
            if (is != null) return is;
        }

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader != null) {
            is = loader.getResourceAsStream(fullResourceName);
            if (is != null) {
                return is;
            }
        }

        is = ClassLoader.getSystemResourceAsStream(fullResourceName);
        if (is != null) {
            return is;
        }

        throw new IllegalStateException("Failed to load resource[" + fullResourceName + "]");
    }


}
