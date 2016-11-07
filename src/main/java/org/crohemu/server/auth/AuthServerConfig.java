package org.crohemu.server.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * The auth server's configuration.
 * Some values are read from property files, and others are hardcoded constants.
 * Stops the program if the values contained in the property files do not pass the sanity checks.
 */
public class AuthServerConfig {

    private static Logger logger = LoggerFactory.getLogger(AuthServerConfig.class);

    private static String ipAddress = null;
    private static Integer portNumber = null;
    private static ExecMode execMode = null;

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
        execMode = readExecMode();
    }

    private static String readIpAddress() {
        final String PROP_NAME = "ipAddress";

        String ipAddress = configProperties.getProperty(PROP_NAME);

        if (ipAddress == null || !Pattern.matches(IPV4_PATTERN, ipAddress)) {
            handleInvalidProperty(PROP_NAME);
        }

        return ipAddress;
    }

    private static Integer readPortNumber() {
        String PROP_NAME = "portNumber";
        int portNumber = Integer.parseInt(configProperties.getProperty(PROP_NAME));

        if (portNumber <= 1024 || portNumber >= 65536) {
            handleInvalidProperty(PROP_NAME);
        }

        return portNumber;
    }

    private static ExecMode readExecMode() {
        String PROP_NAME = "execMode";

        String execModeStr = configProperties.getProperty(PROP_NAME);
        switch (execModeStr) {
            case "debug0":
                return ExecMode.DEBUG0;
            case "debug1":
                return ExecMode.DEBUG1;
            case "release":
                return ExecMode.RELEASE;
            case "silent":
                return ExecMode.SILENT;
            default:
                handleInvalidProperty(PROP_NAME);
                return null;
        }
    }

    private static void handleInvalidProperty(String propName) {
        logger.error("invalid value for property {} in config file {}", propName, CONFIG_FILE_PATH);
    }

    public static String getIpAddress() {
        return ipAddress;
    }

    public static int getPortNumber() {
        return portNumber;
    }

    public enum ExecMode {
        DEBUG0, DEBUG1, RELEASE, SILENT
    }
}
