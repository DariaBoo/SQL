package ua.foxminded.dao.newPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ua.foxminded.dao.DAODataAssigner;
import ua.foxminded.dao.exception.DAOException;

public class DAODataAssignerImpl implements DAODataAssigner {
    private final String SQL_ADDGROUP = "UPDATE schoolManager.students SET group_id = ? WHERE student_id = ?";
    private final String SQL_ADDCOUNTOFSTUDENTS = "UPDATE schoolManager.groups SET count_of_students = ? WHERE group_id = ?";
    
    private Connection connection;

    public DAODataAssignerImpl() {

    }

    public DAODataAssignerImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void assignCoursesToStudents() {

    }

    @Override
    public void assignStudentsToGroups() throws DAOException {

    }

    private int generateGroupSize(Connection connection, int group_id) throws SQLException {

        return 0;
    }

}
