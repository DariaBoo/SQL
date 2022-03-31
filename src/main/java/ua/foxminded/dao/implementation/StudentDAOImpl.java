package ua.foxminded.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.foxminded.dao.StudentDAO;
import ua.foxminded.dao.dataSource.DataSourceDAO;
import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.domain.Student;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 *
 */
public class StudentDAOImpl implements StudentDAO {
    private static StudentDAOImpl instance;
    private DataSourceDAO dataSource;
    private static final Logger log = LoggerFactory.getLogger(StudentDAOImpl.class.getName());

    private static final String SQL_ADDSTUDENT = "INSERT INTO schoolManager.students (first_name, last_name) values ('%s','%s')";
    private static final String SQL_REMOVESTUDENT = "DELETE FROM schoolManager.students WHERE student_id = %d";
    private static final String SQL_FINDSTUDENTSBYCOURSE = "select schoolmanager.students.student_id, schoolmanager.students.first_name, schoolmanager.students.last_name from schoolmanager.students\n"
            + "inner join schoolmanager.student_course on schoolmanager.student_course.student_id = schoolmanager.students.student_id\n"
            + "inner join schoolmanager.courses on schoolmanager.student_course.course_id = schoolmanager.courses.course_id\n"
            + "where LOWER(schoolmanager.courses.course_name) = ?";

    private StudentDAOImpl() {

    }

    /**
     * Creates a StudentDAOImpl with ConnectionPool for tests
     * 
     * @author Bogush Daria
     * @param dataSource
     * @see DataSourceDAO
     */
    public StudentDAOImpl(DataSourceDAO dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Returns instance of the class
     * 
     * @author Bogush Daria
     */
    public static StudentDAOImpl getInstance() {
        log.trace("Get class instance");
        if (instance == null) {
            instance = new StudentDAOImpl();
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OptionalInt addStudent(Student student) throws DAOException {
        log.info("Add new student to the table 'students' with name {} and surname {}", student.getFirstName(),
                student.getLastName());
        String sql = String.format(SQL_ADDSTUDENT, student.getFirstName(), student.getLastName());
        log.trace("Create sql query {}", sql);
        log.debug("Get connection");
        try (Connection connection = DataSourceDAO.getConnection();
                Statement statement = connection.createStatement()) {
            OptionalInt result = OptionalInt.of(statement.executeUpdate(sql));
            log.debug("ExecuteUpdate sql and get the result {}", result);
            return result;
        } catch (SQLException sqlE) {
            log.error("Fail to connect to the database", sqlE);
            throw new DAOException("Fail to connect to the database while add new student", sqlE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OptionalInt removeStudent(int studentID) throws DAOException {
        log.info("Remove student from the table 'students' by studentID", studentID);        
        String sql = String.format(SQL_REMOVESTUDENT, studentID);
        log.trace("Create sql query {}", sql);
        log.debug("Get connection");
        try (Connection connection = DataSourceDAO.getConnection();
                Statement statement = connection.createStatement()) {
            log.trace("Delete from the table 'students' by studentID {}", studentID);
            OptionalInt result = OptionalInt.of(statement.executeUpdate(sql));
            log.debug("ExecuteUpdate sql and get the result {}", result);
            return result;
        } catch (SQLException sqlE) {
            log.error("Fail to connect to the database", sqlE);
            throw new DAOException("", sqlE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<Student>> findStudentsByCourse(String courseName) throws DAOException {
        log.info("Find students by course name {}", courseName);
        ResultSet resultSet = null;
        List<Student> students = new ArrayList<>();
        log.debug("Get connection");
        try (Connection connection = DataSourceDAO.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_FINDSTUDENTSBYCOURSE)) {
            statement.setString(1, courseName.toLowerCase());
            resultSet = statement.executeQuery();
            log.debug("Take from resultSet");
            while (resultSet.next()) {
                students.add(new Student.StudentBuidler().setStudentID(resultSet.getInt("student_id"))
                        .setFirstName(resultSet.getString("first_name")).setLastName(resultSet.getString("last_name"))
                        .build());
            }
        } catch (SQLException sqlE) {
            log.error("Fail to connect to the database", sqlE);
            throw new DAOException("Fail to connect to the database while found students by course.", sqlE);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                    log.debug("ResultSet closed");
                }
            } catch (SQLException sqlE) {
                log.error("Fail to close the resultSet", sqlE);
                throw new DAOException("Fail to close the database while found students by course.", sqlE);
            }
        }
        return Optional.ofNullable(students);
    }
}
