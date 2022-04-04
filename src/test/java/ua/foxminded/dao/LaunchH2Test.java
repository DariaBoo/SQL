package ua.foxminded.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.OptionalInt;

import org.apache.ibatis.jdbc.ScriptRunner;

import ua.foxminded.dao.dataSource.DataSourceDAO;
import ua.foxminded.dao.dataSource.DataSourceDAOConfig;
import ua.foxminded.dao.exception.DAOException;

public class LaunchH2Test {
    private String configFile = "h2config.properties";
    private static final String SQL_ADDSTUDENTTOCOURSE = "INSERT INTO schoolmanager.student_course (student_id,course_id) VALUES (%d,%d)";

    public void executeScript(String fileDirectory) throws DAOException {
        DataSourceDAOConfig.setConfigFile(configFile);
        try (Connection connection = DataSourceDAO.getConnection();
                Reader reader = new BufferedReader(new FileReader(fileDirectory))) {
            ScriptRunner runner = new ScriptRunner(connection);
            runner.runScript(reader);
        } catch (IOException e) {
            throw new RuntimeException("File is not exist", e);
        } catch (SQLException sqlE) {
            throw new DAOException("Fail to connect to H2 database", sqlE);
        }
    }

    public OptionalInt addStudentToCourseDuplicate(int studentID, int courseID) throws DAOException {
        String sql = String.format(SQL_ADDSTUDENTTOCOURSE, studentID, courseID);
        try (Connection connection = DataSourceDAO.getConnection();
                Statement statement = connection.createStatement()) {
            OptionalInt result = OptionalInt.of(statement.executeUpdate(sql));
            return result;
        } catch (SQLException sqlE) {
            throw new DAOException("Fail to connect to the database while add student to course.", sqlE);
        }
    }
}
