package ua.foxminded.dao.dataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.foxminded.dao.implementation.StudentDAOImpl;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 */
public class DataSourceDAOConfig {
    private static String configFile;
    private static final Logger log = LoggerFactory.getLogger(DataSourceDAOConfig.class.getName());

    public static void setConfigFile(String configFileName) {
        log.info("Set configFile {}", configFileName);
        configFile = configFileName;
    }

    public static String getConfigFile() {
        return configFile;
    }
}
