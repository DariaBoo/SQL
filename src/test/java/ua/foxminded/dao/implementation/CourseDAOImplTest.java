package ua.foxminded.dao.implementation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.h2.tools.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import ua.foxminded.dao.PreparationH2Test;
import ua.foxminded.dao.dataSource.DataSourceDAO;
import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.dao.implementation.CourseDAOImpl;
import ua.foxminded.dao.implementation.DAOLauncherImpl;
import ua.foxminded.dao.implementation.DataAssignerDAOImpl;
import ua.foxminded.domain.Course;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class CourseDAOImplTest {
    private String configFile = "h2config.properties";
    private final String SCRIPT = "src/test/resources/tablesTest.sql";
    private final String SQL_SELECT1 = "SELECT course_name FROM schoolmanager.courses WHERE course_id = 1";
   
    private DataSourceDAO source;
    private Connection connection;
    private ResultSet resultSet;
    private Statement statement;
    private CourseDAOImpl courseDao;

    @BeforeAll
    void initializeTestDatabase() throws DAOException, SQLException, FileNotFoundException, IOException {
        //Server server = Server.createTcpServer().start();
        source = new DataSourceDAO(configFile);
        PreparationH2Test test = new PreparationH2Test();
        connection = source.getConnection();
        test.executeScript(connection, SCRIPT);
        courseDao = new CourseDAOImpl(source);
        statement = connection.createStatement();          
    }
    @AfterAll
    void closeTestDatabase() throws SQLException {
        resultSet.close();
        statement.close();
        connection.close();
    }   

    @Test 
    @Order (1)
    void findAllCourses_shouldReturnAllCourses2() throws DAOException{
        List<Course> list = courseDao.findAllCourses().get();
        assertEquals(8, list.size());
    }   
    @Test 
    @Order (2)
    void findAllCourses_shouldReturnOptionalList() throws SQLException, DAOException{
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course(1,"Alchemy", "the study of transmutation of substances into other forms."));
        courses.add(new Course(2,"Herbology","the study of magical plants and how to take care of, utilise and combat them."));
        courses.add(new Course(3,"History of Magic", "the study of magical history."));
        courses.add(new Course(4,"Potions", "the art of creating mixtures with magical effects."));
        courses.add(new Course(5,"Muggle Studies", "the study of the Muggle culture from a wizarding point of view."));
        courses.add(new Course(6,"Dark Arts", "the study of defensive techniques to defend against the Dark Arts."));
        courses.add(new Course(7,"Charms", "the study of spells concerned with giving an object new properties."));
        courses.add(new Course(8,"Flying", "the study of flying of broomsticks."));
        Optional<List<Course>> optional = Optional.of(courses);
        assertEquals(optional, courseDao.findAllCourses());
    } 
    @Test
    @Order (3)
    void findAllCourses_shouldReturnTheCourseFromTable() throws SQLException, DAOException {        
        resultSet = statement.executeQuery(SQL_SELECT1);
        resultSet.next();
        assertEquals("Alchemy", resultSet.getString("course_name"));
    }   
    @Test
    @Order (4)
    void findCoursesByStudentID_shouldReturnStudentsCourses() throws DAOException {
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course(1,"Alchemy", "the study of transmutation of substances into other forms."));
        courses.add(new Course(2,"Herbology","the study of magical plants and how to take care of, utilise and combat them."));
        Optional<List<Course>> optional = Optional.of(courses);
        assertEquals(optional, courseDao.findCoursesByStudentID(1));
    }
    @Test
    @Order (5)
    void addStudentToCourse_shouldReturn1_whenAdd1Student() throws DAOException {
        assertEquals(1, courseDao.addStudentToCourse(1, 3).getAsInt());
    }
    @Test
    @Order (6)
    void addStudentToCourse_shouldReturn0_whenAddStudentWhichAlreadyExist() throws DAOException {
        assertEquals(0, courseDao.addStudentToCourse(1, 3).getAsInt());
    }
    @Test
    @Order (7)
    void deleteFromCourse_shouldReturn1_whenDeleteTheStudentFromTable() throws DAOException {
        assertEquals(1, courseDao.deleteFromCourse(1, 3).getAsInt());
    }
    @Test
    @Order (8)
    void deleteFromCourse_shouldReturn0_whenDeleteTheStudentWhichNotExist() throws DAOException {        
        assertEquals(0, courseDao.deleteFromCourse(1, 3).getAsInt());
    }
}
