package ua.foxminded.dao;

import java.util.List;
import java.util.Optional;

import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.domain.Group;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 *
 */
public interface GroupDAO {
    /**
     * The method selects groups from the database that are less or equal inputing
     * group size
     * 
     * @param groupSize
     * @return List<Group>
     * @see Group
     * @throws DAOException
     */
    public Optional<List<Group>> selectBySize(int groupSize) throws DAOException;
}
