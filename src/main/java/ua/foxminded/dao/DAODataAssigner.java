package ua.foxminded.dao;

import ua.foxminded.dao.exception.DAOException;

public interface DAODataAssigner {
    void assignCoursesToStudents();
    void assignStudentsToGroups() throws DAOException;

}
