package org.crohemu.server.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * The auth server's configuration.
 * Some values are read from property files, and others are hardcoded constants.
 * Stops the program if the values contained in the property files do not pass the sanity checks.
 */
public class AuthServerConfig {

    private static Logger logger = LoggerFactory.getLogger("Main");

    private static String ipAddress = null;
    private static Integer portNumber = null;

    private final static String IPV4_PATTERN = "^(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
            ".(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
            ".(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
            ".(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";


    public static final String CONFIG_FILE_PATH = "./config/authserver.properties";

    private static Properties configProperties;

    // Loads the config file then reads it
    static {
        try {
            configProperties = PropertiesLoaderUtils.loadProperties(new FileSystemResource(CONFIG_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        readProperties();
    }

    private static void readProperties() {

        ipAddress = readIpAddress();
        portNumber = readPortNumber();
    }

    private static String readIpAddress() {
        String ipAddress = configProperties.getProperty("ipAddress");

        if (ipAddress == null || !Pattern.matches(IPV4_PATTERN, ipAddress)) {
            logger.error("Invalid value for property ipAddress in config file {}", CONFIG_FILE_PATH);
        }

        return ipAddress;
    }

    private static Integer readPortNumber() {
        int portNumber = Integer.parseInt(configProperties.getProperty("portNumber"));

        if (portNumber <= 1024 || portNumber >= 65536) {
            logger.error("Invalid value for property portNumber in config file {}", CONFIG_FILE_PATH);
        }

        return portNumber;
    }

    public static String getIpAddress() {
        return ipAddress;
    }

    public static int getPortNumber() {
        return portNumber;
    }
}
