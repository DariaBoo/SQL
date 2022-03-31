package ua.foxminded.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.foxminded.dao.GroupDAO;
import ua.foxminded.dao.dataSource.DataSourceDAO;
import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.domain.Group;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 *
 */
public class GroupDAOImpl implements GroupDAO {
    private static GroupDAOImpl instance;
    private DataSourceDAO dataSource;
    private static final Logger log = LoggerFactory.getLogger(GroupDAOImpl.class.getName());
    private static final String SQL_SELECTBYSIZE = "SELECT * FROM schoolmanager.groups WHERE count_of_students <= ? ORDER BY group_id ASC";

    private GroupDAOImpl() {

    }

    /**
     * Creates a GroupDAOImpl with ConnectionPool for tests
     * 
     * @author Bogush Daria
     * @param dataSource
     * @see DataSourceDAO
     */
    public GroupDAOImpl(DataSourceDAO dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Returns instance of the class
     * 
     * @author Bogush Daria
     */
    public static GroupDAOImpl getInstance() {
        log.trace("Get class instance");
        if (instance == null) {
            instance = new GroupDAOImpl();
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<Group>> selectBySize(int groupSize) throws DAOException {
        log.info("Select groups with less or equal groupSize {}", groupSize);
        ResultSet resultSet = null;
        List<Group> groups = new ArrayList<>();               
        log.debug("Get connection");
        try (Connection connection = DataSourceDAO.getConnection(); 
                PreparedStatement statement = connection.prepareStatement(SQL_SELECTBYSIZE)) {
            log.trace("Set groupSize {}", groupSize);
            statement.setInt(1, groupSize);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                log.debug("Take from resultSet");   
                groups.add(new Group(resultSet.getInt("group_id"), resultSet.getString("group_name"),
                        resultSet.getInt("count_of_students")));
            }
        } catch (SQLException sqlE) {
            log.error("Fail to connect to the database", sqlE);
            throw new DAOException("Fail to connect to the database while select groups by size.", sqlE);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    log.debug("ResultSet closed");
                }
            } catch (SQLException sqlE) {
                log.error("Fail to close the resultSet", sqlE);
                throw new DAOException("Fail to close the database while select groups by size.", sqlE);
            }
        }
        log.debug("Make list optional");
        return Optional.ofNullable(groups);
    }
}
