package ua.foxminded.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.foxminded.dao.DataAssignerDAO;
import ua.foxminded.dao.dataSource.DataSourceDAO;
import ua.foxminded.dao.exception.DAOException;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 *
 */
public class DataAssignerDAOImpl implements DataAssignerDAO {
    private static DataAssignerDAOImpl instance;
    private DataSourceDAO dataSource;
    private static final Logger log = LoggerFactory.getLogger(DataAssignerDAOImpl.class.getName());

    private static final String SQL_SETCOURSESTOSTUDENT = "INSERT INTO schoolmanager.student_course (student_id,course_id) VALUES (?,?)";
    private static final String SQL_SETGROUPTOSTUDENT = "UPDATE schoolManager.students SET group_id = ? WHERE student_id = ?";
    private static final String SQL_SETCOUNTOFSTUDENTS = "UPDATE schoolManager.groups SET count_of_students = ? WHERE group_id = ?";
    private static final String SQL_COUNTOFSTUDENTS = "SELECT COUNT(*) FROM schoolManager.students";
    private static final String SQL_COUNTOFGROUPS = "SELECT COUNT(*) FROM schoolManager.groups";
    private static final String SQL_COUNTOFCOURSES = "SELECT COUNT(*) FROM schoolManager.courses";

    private int countOfStudents;
    private int groupSizeMin = 10;// the task's condition
    private int groupSizeMax = 30;// the task's condition
    private int courseCountMin = 1;// the task's condition
    private int courseCountMax = 3;// the task's condition
    private Random random = new Random();

    private DataAssignerDAOImpl() {

    }

    /**
     * Creates a DataAssignerDAOImpl with ConnectionPool for tests
     * 
     * @author Bogush Daria
     * @param connectionPool
     * @see DataSourceDAO
     */
    public DataAssignerDAOImpl(DataSourceDAO dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Returns instance of the class
     * 
     * @author Bogush Daria
     */
    public static DataAssignerDAOImpl getInstance() {
        log.debug("Create instance of DataAssignerDAOImpl");
        if (instance == null) {
            instance = new DataAssignerDAOImpl();
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignCoursesToStudents() throws DAOException {
        log.info("Assign courses to students");
        log.debug("Get connection");
        try (Connection connection = DataSourceDAO.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SETCOURSESTOSTUDENT)) {
            log.trace("Get count of students from the database");
            countOfStudents = countRows(connection, SQL_COUNTOFSTUDENTS);
            log.trace("Get count of courses from the database");
            int countOfCourses = countRows(connection, SQL_COUNTOFCOURSES);
            for (int i = 1; i <= countOfStudents; i++) {
                List<Integer> coursesID = shuffleID(countOfCourses);
                log.trace("Get random range of course beetwen 1 to 3");
                int coursesRange = randomizeSize(courseCountMin, courseCountMax);
                for (int j = 0; j < coursesRange; j++) {
                    log.trace("Set studentID {} to the student_course table", i);
                    statement.setInt(1, i);
                    log.trace("Set courseID {} to the student_course table", coursesID.get(j));
                    statement.setInt(2, coursesID.get(j));
                    statement.execute();
                }
            }
        } catch (SQLException sqlE) {
            log.error("Fail to connect to the database", sqlE);
            throw new DAOException("Fail to connect to the database while assign courses to students.", sqlE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void assignGroupsToStudents() throws DAOException {
        log.info("Assign groups to students");
        log.debug("Get connection");
        try (Connection connection = DataSourceDAO.getConnection();
                PreparedStatement statement1 = connection.prepareStatement(SQL_SETGROUPTOSTUDENT);
                PreparedStatement statement2 = connection.prepareStatement(SQL_SETCOUNTOFSTUDENTS)) {
            log.trace("Get count of students from the database");
            countOfStudents = countRows(connection, SQL_COUNTOFSTUDENTS);
            log.trace("Get count of students from the database");
            int countOfGroups = countRows(connection, SQL_COUNTOFGROUPS);
            List<Integer> studentsID = shuffleID(countOfStudents);
            List<Integer> groupsID = shuffleID(countOfGroups);
            for (int i = 0; i < countOfGroups; i++) {
                int groupID = groupsID.get(i);
                log.trace("Get random size of group");
                int groupSize = randomizeSize(groupSizeMin, groupSizeMax);// random number between 10 and 30
                if (studentsID.size() < groupSize) {
                    groupSize = studentsID.size();
                }
                for (int j = 0; j < groupSize; j++) {
                    if (studentsID.size() > groupSizeMin) {
                        log.trace("Set groupID to the student table for studentID");
                        statement1.setInt(1, groupID);
                        statement1.setInt(2, studentsID.get(j));
                        statement1.execute();

                        log.trace("Set group size to the groups table");
                        statement2.setInt(1, groupSize);
                        statement2.setInt(2, groupID);
                        statement2.execute();
                    } else {
                        break;
                    }
                }
                studentsID = studentsID.subList(groupSize, studentsID.size());
            }
        } catch (SQLException sqlE) {
            log.error("Fail to connect to the database", sqlE);
            throw new DAOException("Fail to connect to the database while assign groups to students.", sqlE);
        }
    }

    private List<Integer> shuffleID(int countOfTableRows) {
        log.trace("Shuffle list");
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < countOfTableRows; i++) {
            result.add(i + 1);
        }
        Collections.shuffle(result);
        return result;
    }

    private int countRows(Connection connection, String sql) throws DAOException {
        log.trace("Count rows of table");
        int result = 0;
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            resultSet.next();
            result = resultSet.getInt(1);
        } catch (SQLException sqlE) {
            throw new DAOException("Fail to connect to the database while take count of rows.", sqlE);
        }
        return result;
    }

    private int randomizeSize(int min, int max) {
        log.trace("Create random number beetwen {} and {}", min, max);
        return random.nextInt((max - min) + 1) + min;
    }
}
