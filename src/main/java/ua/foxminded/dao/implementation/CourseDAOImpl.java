package ua.foxminded.dao.implementation;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.foxminded.dao.CourseDAO;
import ua.foxminded.dao.dataSource.DataSourceDAO;
import ua.foxminded.dao.dataSource.DataSourceDAOConfig;
import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.domain.Course;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 *
 */
public class CourseDAOImpl implements CourseDAO {
    private static CourseDAOImpl instance;
    private static final Logger log = LoggerFactory.getLogger(CourseDAOImpl.class.getName());
    private static final String SQL_FINDALLCOURSES = "SELECT * FROM schoolmanager.courses ORDER BY course_id";
    private static final String SQL_FINDCOURSESBYSTUDENT = "SELECT schoolmanager.courses.course_id, schoolmanager.courses.course_name, schoolmanager.courses.description FROM schoolmanager.courses\n"
            + "            INNER JOIN schoolmanager.student_course ON schoolmanager.student_course.course_id = schoolmanager.courses.course_id\n"
            + "            WHERE schoolmanager.student_course.student_id = %d\n" + "ORDER BY course_id";
    private static final String SQL_ADDSTUDENTTOCOURSE = "INSERT INTO schoolmanager.student_course (student_id,course_id) VALUES (%d,%d) ON CONFLICT DO NOTHING";
    private static final String SQL_DELETESTUDENTFROMCOURSE = "DELETE FROM schoolmanager.student_course WHERE schoolmanager.student_course.student_id = %d AND schoolmanager.student_course.course_id = %d";

    private CourseDAOImpl() {

    }

    /**
     * Returns instance of the class
     * 
     * @author Bogush Daria
     */
    public static CourseDAOImpl getInstance() {
        if (instance == null) {
            instance = new CourseDAOImpl();
        }
        log.info("Got the class instance");
        return instance;
    }

    /**
     * Creates a CourseDAOImpl with ConnectionPool for tests
     * 
     * @author Bogush Daria
     * @param connectionPool
     * @see DataSourceDAO
     */
    public CourseDAOImpl(String configFile) {
        DataSourceDAOConfig.setConfigFile(configFile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<Course>> findAllCourses() throws DAOException {
        log.trace("Find all courses from the database");
        List<Course> courses = new ArrayList<>();
        log.info("Get connection");
        try (Connection connection = DataSourceDAO.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_FINDALLCOURSES)) {
            log.info("Executed sql query {}", SQL_FINDALLCOURSES);
            while (resultSet.next()) {
                courses.add(new Course(resultSet.getInt("course_id"), resultSet.getString("course_name"),
                        resultSet.getString("description")));
            }
            log.debug("Took from the resultSet {}", courses);
            return Optional.ofNullable(courses);
        } catch (SQLException sqlE) {
            log.error("Fail to connect to the database", sqlE);
            throw new DAOException("Fail to connect to the database while found all courses.", sqlE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<Course>> findCoursesByStudentID(int studentID) throws DAOException {
        log.trace("Find courses by studentID {}", studentID);
        List<Course> courses = new ArrayList<>();
        String sql = String.format(SQL_FINDCOURSESBYSTUDENT, studentID);
        log.trace("Create sql query {}", sql);
        log.info("Get connection");
        try (Connection connection = DataSourceDAO.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            log.info("Executed sql query {} with studentID {}", SQL_FINDCOURSESBYSTUDENT, studentID);
            while (resultSet.next()) {
                courses.add(new Course(resultSet.getInt("course_id"), resultSet.getString("course_name"),
                        resultSet.getString("description")));
            }
            log.debug("Took from the resultSet {}", courses);
            return Optional.ofNullable(courses);
        } catch (SQLException sqlE) {
            log.error("Fail to connect to the database", sqlE);
            throw new DAOException("Fail to connect to the database while found all courses by student id.", sqlE);
        } 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OptionalInt addStudentToCourse(int studentID, int courseID) throws DAOException {
        log.trace("Add student to course by studentID {} and courseID {}", studentID, courseID);
        String sql = String.format(SQL_ADDSTUDENTTOCOURSE, studentID, courseID);
        log.trace("Create sql query {} ", sql);
        log.info("Get connection");
        try (Connection connection = DataSourceDAO.getConnection();
                Statement statement = connection.createStatement()) {
            OptionalInt result = OptionalInt.of(statement.executeUpdate(sql));
            log.info("Executed sql query {} ", sql);
            log.debug("ExecuteUpdate sql and get the result {}", result);
            return result;
        } catch (SQLException sqlE) {
            log.error("Fail to connect to the database", sqlE);
            throw new DAOException("Fail to connect to the database while add student to course.", sqlE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OptionalInt deleteStudentFromCourse(int studentID, int courseID) throws DAOException {
        log.trace("Delete student from his/her course by studentID {} and courseID {}", studentID, courseID);        
        String sql = String.format(SQL_DELETESTUDENTFROMCOURSE, studentID, courseID);
        log.trace("Create sql query {} with studentId {} and courseID {}", SQL_DELETESTUDENTFROMCOURSE, studentID,
                courseID);
        log.info("Get connection");
        try (Connection connection = DataSourceDAO.getConnection();
                Statement statement = connection.createStatement()) {
            OptionalInt result = OptionalInt.of(statement.executeUpdate(sql));
            log.info("Executed sql query {}", sql);
            log.debug("Got the result {} of deleting the student from the course", result);
            return result;
        } catch (SQLException sqlE) {
            log.error("Fail to connect to the database", sqlE);
            throw new DAOException("Fail to connect to the database while add student to course.", sqlE);
        }
    }
}
