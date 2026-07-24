package com.epam.training.student_arkadii_ilinov.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new IllegalStateException(CONFIG_FILE + " not found on the classpath");
            }
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load " + CONFIG_FILE, e);
        }
    }

    private ConfigReader() {
    }

    public static String getBaseUrl() {
        return PROPERTIES.getProperty("base.url");
    }

    public static String getUsername() {
        return PROPERTIES.getProperty("username");
    }

    public static String getPassword() {
        return PROPERTIES.getProperty("password");
    }

    public static String getWindowWidth() {
        return PROPERTIES.getProperty("window.width", "1920");
    }
    public static String getWindowHeight() {
        return PROPERTIES.getProperty("window.height", "1080");
    }
}
