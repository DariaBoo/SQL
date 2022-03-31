package ua.foxminded.dao.dataSource;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 */
public class DataSourceDAOConfig {
    private static String configFile;

    public static void setConfigFile(String configFileName) {
        configFile = configFileName;
    }

    public static String getConfigFile() {
        return configFile;
    }
}
