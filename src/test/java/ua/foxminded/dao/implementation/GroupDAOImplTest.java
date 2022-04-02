package ua.foxminded.dao.implementation;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.h2.tools.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import ua.foxminded.dao.LaunchH2Test;
import ua.foxminded.dao.dataSource.DataSourceDAO;
import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.domain.Group;

@TestInstance(Lifecycle.PER_CLASS)
class GroupDAOImplTest {
    private final String configFile = "h2config.properties";
    private final String SCRIPT = "src/test/resources/tablesTest.sql";
    private GroupDAOImpl groupDao;
    private List<Group> list;

    @BeforeAll
    void initializeTestDatabase() throws DAOException, FileNotFoundException {
        groupDao = new GroupDAOImpl(configFile);
        LaunchH2Test test = new LaunchH2Test();
        test.executeScript(SCRIPT);
    }

    @Test
    void selectBySize_shouldReturnCountOfGroups() throws DAOException {
        list = groupDao.selectBySize(25).get();
        assertEquals(6, list.size());
    }

    @Test
    void selectBySize_shouldReturn0_whenInputMinOfGroupCount() throws DAOException {
        list = groupDao.selectBySize(9).get();
        assertEquals(0, list.size());
    }

    @Test
    void selectBySize_shouldReturnAllGroups_whenInputMaxOfGroupCount() throws DAOException {
        list = groupDao.selectBySize(30).get();
        assertEquals(8, list.size());
    }

    @Test
    void selectBySize_shouldReturn0_whenInputNegativeNumber() throws DAOException {
        list = groupDao.selectBySize(-1).get();
        assertEquals(0, list.size());
    }
}
