package ua.foxminded.dao;

import ua.foxminded.dao.exception.DAOException;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 *
 */
public interface DataAssignerDAO {
    /**
     * The method randomly assigns 1-3 courses to student in the database
     * @author Bogush Daria
     * @throws DAOException if a database access error occurs.
     */
    public void assignCoursesToStudents() throws DAOException;
    
    /**
     * The method randomly assigns students to groups in the database. Group capacity 10-30 students
     * @author Bogush Daria
     * @throws DAOException if a database access error occurs.
     */
    public void assignGroupsToStudents() throws DAOException;

}
