package org.crohemu.server.auth;

import org.apache.commons.lang3.StringUtils;
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
    private static Integer protocolVersion = null;
    private static String dofusPublicKey = null;

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
        protocolVersion = readProtocolVersion();
        dofusPublicKey = readDofusPublicKey();
    }

    private static String readDofusPublicKey() {
        final String PROP_NAME = "dofusPublicKey";

        String dofusPublicKey = configProperties.getProperty("dofusPublicKey");

        if (StringUtils.isBlank(dofusPublicKey)) {
            logger.warn("{} is empty. Is this right ? ({})", PROP_NAME, CONFIG_FILE_PATH);
        } return dofusPublicKey;
    }

    private static Integer readProtocolVersion() {
        final String PROP_NAME = "protocolVersion";

        int protocolVersion = Integer.valueOf(configProperties.getProperty("protocolVersion"));

        if (protocolVersion < 0) {
            handleInvalidProperty(PROP_NAME);
        }

        return protocolVersion;
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

    public static boolean execModeLessThan(ExecMode mode) {
        return execMode.id() <= mode.id();
    }

    public static String getIpAddress() {
        return ipAddress;
    }

    public static int getPortNumber() {
        return portNumber;
    }

    public static ExecMode getExecMode() {
        return execMode;
    }

    public static Integer getProtocolVersion() {
        return protocolVersion;
    }

    public static String getDofusPublicKey() {
        return dofusPublicKey;
    }

    public enum ExecMode {
        DEBUG0(0), DEBUG1(10), RELEASE(20), SILENT(30);

        private final int id;

        ExecMode(int id) {
            this.id = id;
        }

        public int id() {
            return this.id;
        }
    }
}
