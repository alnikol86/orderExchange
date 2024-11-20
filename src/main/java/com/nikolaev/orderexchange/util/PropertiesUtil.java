package com.nikolaev.orderexchange.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (
                InputStream inputStream = PropertiesUtil.class
                        .getClassLoader()
                        .getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Property file 'application.properties' not found in the classpath");
            }
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Something is wrong with loading the properties file.", e);
        }
    }

    private PropertiesUtil() {
    }
}
