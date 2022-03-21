package ua.foxminded.dao;

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

import ua.foxminded.dao.exception.DAOException;

public class SQLExecuter {
    private final String MESSAGE_FILE_NOT_FOUND = " - file not found in directory src/main/resources.";

    public void runScript(String fileDirectory) throws DAOException {
        ScriptRunner sr = null;
        try {
            if (!Files.exists(Paths.get(fileDirectory))) {
                throw new FileNotFoundException(Paths.get(fileDirectory).getFileName() + MESSAGE_FILE_NOT_FOUND);
            } else if (Files.size(Paths.get(fileDirectory)) == 0) {
                throw new RuntimeException("File '" + fileDirectory + "' is empty.");// handle correctly
            } else {
                try (Connection connection = ConnectionManager.getInstance().getConnection();
                        Reader reader = new BufferedReader(new FileReader(fileDirectory))) {
                    sr = new ScriptRunner(connection);
                    sr.runScript(reader);
                } catch (SQLException sqlE) {
                    throw new DAOException("Failed to connect to the database while execute script.", sqlE);
                }
            }
        } catch (IOException exception) {
            throw new RuntimeException("IOException in ResourceReader class.", exception);
        }
    }
}
