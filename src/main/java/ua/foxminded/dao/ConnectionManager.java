package ua.foxminded.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.view.AppMenu;

/**
 * 
 * @author Bogush Daria
 *
 */
public class ConnectionManager {
    private static ConnectionManager instance;
    private Connection connection;
    private static String DB_DRIVER;
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;
    
    private static String configFile = "dbconfig.properties";

    private static final Logger log = LoggerFactory.getLogger(ConnectionManager.class);

    static {
        Properties properties = new Properties();
        try {
            log.info("");
            InputStream inputStream = ConnectionManager.class.getClassLoader()
                    .getResourceAsStream(configFile);
            // new FileInputStream("src/main/resources/dbconfig.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("");
            e.printStackTrace();
        }
        DB_DRIVER = properties.getProperty("DB_DRIVER");
        DB_URL = properties.getProperty("DB_URL");
        DB_USER = properties.getProperty("DB_USER");
        DB_PASSWORD = properties.getProperty("DB_PASSWORD");
    }

    private ConnectionManager() {
        try {
            Class.forName(DB_DRIVER);// ?????
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("", e);
        }
    }

    /**
     * The method returns a connection to the database.
     * 
     * @author Bogush Daria
     * @return Connection
     */

    public Connection getConnection() {
        return connection;
    }

    /**
     * The method returns an instance of the class.
     * 
     * @author Bogush Daria
     * @return ConnectionManager
     * @throws DAOException if a database access error occurs
     */

    public static ConnectionManager getInstance() throws DAOException {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new ConnectionManager();
            }
        } catch (SQLException sqlE) {
            throw new DAOException("Failed to connect to database.", sqlE);
        }
        return instance;
    }
}
