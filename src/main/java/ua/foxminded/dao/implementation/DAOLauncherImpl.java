package ua.foxminded.dao.implementation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.foxminded.dao.DAOLauncher;
import ua.foxminded.dao.dataSource.DataSourceDAO;
import ua.foxminded.dao.exception.DAOException;

import com.google.common.annotations.VisibleForTesting;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 *
 */
public class DAOLauncherImpl implements DAOLauncher{
    private static DAOLauncherImpl instance;
    private DataSourceDAO dataSource;
    
    private final String MESSAGE_FILE_NOT_FOUND = " - file not found in directory src/main/resources.";
    private static final Logger log = LoggerFactory.getLogger(DAOLauncherImpl.class.getName());

    
    private DAOLauncherImpl() {
        
    }
    /**
     * Creates a CourseDAOImpl with ConnectionPool for tests
     * @author Bogush Daria
     * @param connectionPool
     * @see DataSourceDAO
     */
    public DAOLauncherImpl(DataSourceDAO dataSource) {
        this.dataSource = dataSource;        
    }
    /**
     * Returns instance of the class
     * @author Bogush Daria
     */
    public static DAOLauncherImpl getInstance() {
        log.trace("Get class instance");
        if(instance == null) {
            instance = new DAOLauncherImpl();
        }
        return instance;
    }
    /**
     * The method prepares the database by executing scripts and assigning groups and
     * courses to students
     * 
     * @author Bogush Daria
     */
    @Override
    public final void prepareDB(String createTablesScript, String fillTablesScript) {
        log.info("Start method prepareDB");
        try {
            log.info("Execute script {}", createTablesScript);
            executeScript(createTablesScript);
            log.info("Execute script {}",fillTablesScript);
            executeScript(fillTablesScript); 
            log.info("Assign groups to students by method {}", "assignGroupsToStudents()");
            DataAssignerDAOImpl.getInstance().assignGroupsToStudents();
            log.info("Assign courses to students by method {}", "assignCoursesToStudents()");
            DataAssignerDAOImpl.getInstance().assignCoursesToStudents();
        } catch (DAOException daoE) {
            log.error("Fail to connect to the database", daoE);
            System.err.println("Exception Message : " + daoE.getMessage());
            System.exit(0);
        }
        log.info("End of database preparation");
    }
    
    @VisibleForTesting
    private void executeScript(String fileDirectory) throws DAOException {
        log.info("Start execute script {}", fileDirectory);
        try {
            if (!Files.exists(Paths.get(fileDirectory))) {
                log.error("File {} doesn't exist", Paths.get(fileDirectory).getFileName());
                throw new FileNotFoundException(Paths.get(fileDirectory).getFileName() + MESSAGE_FILE_NOT_FOUND);
            } else if (Files.size(Paths.get(fileDirectory)) == 0) {
                log.error("File {} is empty", Paths.get(fileDirectory).getFileName());
                throw new DAOException("File '" + fileDirectory + "' is empty."); 
            } else {
                log.debug("Get connection");
                try (Connection connection = DataSourceDAO.getConnection();     
                        Reader reader = new BufferedReader(new FileReader(fileDirectory))) {
                    ScriptRunner runner = new ScriptRunner(connection);
                    log.debug("Read file {}", fileDirectory);
                    runner.runScript(reader);
                } catch (SQLException sqlE) {
                    log.error("Connection fail", sqlE);
                    throw new DAOException("Failed to connect to the database while execute script.", sqlE);
                }
            }
        } catch (IOException exception) {
            log.error("Can't find the file " + fileDirectory + " in the DAOLauncher class.", exception);
            throw new DAOException("Can't find the file " + fileDirectory + " in the DAOLauncher class.", exception); 
        }
        log.info("End of script executing");
    }
}
