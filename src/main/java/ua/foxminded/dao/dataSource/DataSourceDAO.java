package ua.foxminded.dao.dataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 */
public class DataSourceDAO extends DataSourceDAOConfig {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSourse;
    private static String configFile = null;
    private static final Logger log = LoggerFactory.getLogger(DataSourceDAO.class.getName());

    static {
        log.info("Create HikariDataSource with dbconfig.properties");
        configFile = DataSourceDAOConfig.getConfigFile();
        if (configFile == null) {
            configFile = "dbconfig.properties";
        }
        log.debug("Set configFile {} ", configFile);
        Properties properties = new Properties();
        try {
            log.debug("Get configFile by Properties");
            InputStream inputStream = DataSourceDAO.class.getClassLoader().getResourceAsStream(configFile);
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("Fail to load configFile", e);
            throw new RuntimeException("IOException in DataSource class", e);
        }
        log.debug("Set database config");
        config.setJdbcUrl(properties.getProperty("DB_URL"));
        config.setUsername(properties.getProperty("DB_USER"));
        config.setPassword(properties.getProperty("DB_PASSWORD"));
        log.debug("Create new HikariDataSource with config");
        dataSourse = new HikariDataSource(config);
        log.trace("Set validation timeout as 1 min");
        dataSourse.setValidationTimeout(60000L);
    }

    private DataSourceDAO() {

    }

    /**
     * The method returns a connection to the database.
     * 
     * @author Bogush Daria
     * @return Connection
     */
    public static Connection getConnection() throws SQLException {
        log.info("Get connection from DataSource");
        return dataSourse.getConnection();
    }
}
