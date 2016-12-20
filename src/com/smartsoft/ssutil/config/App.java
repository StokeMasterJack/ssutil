package com.smartsoft.ssutil.config;

import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

public class App {

    private final static String CONFIG_DIR_KEY = "configDir";
    private final static String DEFAULT_CONFIG_DIR_1 = "/temp/tmsConfig";
    private final static String DEFAULT_CONFIG_DIR_2 = getUserHome() + "/temp/tmsConfig";

    private final String appName;

    private final ReloadingProperties reloadingProperties;
    protected final Logger log;

    public App(final String appName) {
        this.appName = appName;
        File configFile = getConfigFile();
        System.err.println("configFile[" + configFile + "]");
        if (configFile.exists()) {
            System.err.println("configFile[" + configFile + "] DOES exist");
        } else {
            System.err.println("configFile[" + configFile + "] does NOT exist");
        }
        reloadingProperties = new ReloadingProperties(configFile);
        log = Logger.getLogger(getClass().getName());
        log.info("configFile = " + configFile);
    }


    public String getProperty(String propertyName) {
        return reloadingProperties.getProperty(propertyName);
    }

    public Map<String, String> getProperties(String keySuffix) {
        return reloadingProperties.getProperties(keySuffix);
    }

    public File getConfigFile() {
        File configDir = getConfigDir();
        File configFile = new File(configDir, appName + ".properties");
        if (!configFile.exists()) throw new IllegalStateException("ConfigFile[" + configFile + "] does not exist");
        return configFile;
    }

    private File getConfigDir() {
        File configDir;

        configDir = configDirExists(System.getProperty(CONFIG_DIR_KEY));
        if (configDir != null) {
            return configDir;
        }

        configDir = configDirExists(DEFAULT_CONFIG_DIR_1);
        if (configDir != null) {
            return configDir;
        }

        configDir = configDirExists(DEFAULT_CONFIG_DIR_2);
        if (configDir != null) {
            return configDir;
        }


        throw new IllegalStateException("Could locate configDir. Tried: [System.getProperty(" + CONFIG_DIR_KEY + ")=" + System.getProperty(CONFIG_DIR_KEY) + "], " + DEFAULT_CONFIG_DIR_1 + ", " + DEFAULT_CONFIG_DIR_2);

    }

    private File configDirExists(String configDir) {
        if (configDir == null) {
            return null;
        }
        File f = new File(configDir);
        if (f.exists()) {
            return f;
        } else {
            return null;
        }
    }

    public static File getUserHome() {
        return new File(System.getProperty(USER_HOME_KEY));
    }

    private static final String USER_HOME_KEY = "user.home";


}
