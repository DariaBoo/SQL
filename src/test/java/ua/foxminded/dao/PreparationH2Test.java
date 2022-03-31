package ua.foxminded.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.jdbc.ScriptRunner;

import ua.foxminded.dao.dataSource.DataSourceDAO;
import ua.foxminded.dao.exception.DAOException;

public class PreparationH2Test {
    private static String configFile = "h2config.properties";

    public void executeScript(Connection connection, String fileDirectory) {
        try (Reader reader = new BufferedReader(new FileReader(fileDirectory))) {
            ScriptRunner runner = new ScriptRunner(connection);
            runner.runScript(reader);
        } catch (IOException e) {
            throw new RuntimeException("File is not exist", e);
        }
    }
}
