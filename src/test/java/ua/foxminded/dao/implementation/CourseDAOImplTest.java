package ua.foxminded.dao.implementation;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import ua.foxminded.dao.LaunchH2Test;
import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.domain.Course;

@TestInstance(Lifecycle.PER_CLASS)
class CourseDAOImplTest {
    private final String configFile = "h2config.properties";
    private final String SCRIPT = "src/test/resources/tablesTest.sql";
    private CourseDAOImpl courseDao;
    private LaunchH2Test test;

    @BeforeAll
    void initializeTestDatabase() throws DAOException, FileNotFoundException {
        courseDao = new CourseDAOImpl(configFile);
        test = new LaunchH2Test();
        test.executeScript(SCRIPT);
    }

    @Test
    void findAllCourses_shouldReturnAllCourses2() throws DAOException {
        List<Course> list = courseDao.findAllCourses().get();
        assertEquals(8, list.size());
    }

    @Test
    void findAllCourses_shouldReturnOptionalList() throws SQLException, DAOException {
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course(1, "Alchemy", "the study of transmutation of substances into other forms."));
        courses.add(new Course(2, "Herbology",
                "the study of magical plants and how to take care of, utilise and combat them."));
        courses.add(new Course(3, "History of Magic", "the study of magical history."));
        courses.add(new Course(4, "Potions", "the art of creating mixtures with magical effects."));
        courses.add(new Course(5, "Muggle Studies", "the study of the Muggle culture from a wizarding point of view."));
        courses.add(new Course(6, "Dark Arts", "the study of defensive techniques to defend against the Dark Arts."));
        courses.add(new Course(7, "Charms", "the study of spells concerned with giving an object new properties."));
        courses.add(new Course(8, "Flying", "the study of flying of broomsticks."));
        Optional<List<Course>> optional = Optional.of(courses);
        assertEquals(optional, courseDao.findAllCourses());
    }

    @Test
    void findCoursesByStudentID_shouldReturnStudentsCourses() throws DAOException {
        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course(1, "Alchemy", "the study of transmutation of substances into other forms."));
        courses.add(new Course(2, "Herbology",
                "the study of magical plants and how to take care of, utilise and combat them."));
        Optional<List<Course>> optional = Optional.of(courses);
        assertEquals(optional, courseDao.findCoursesByStudentID(1));
    }

    @DisplayName("Order 4 (Was used method's dublicate with SQL query for H2)")
    @Test
    void addStudentToCourse_shouldReturn1_whenAdd1Student() throws DAOException {
        int result = test.addStudentToCourseDuplicate(1, 3).getAsInt();
        courseDao.deleteStudentFromCourse(1, 3);
        assertEquals(1, result);
    }

    @DisplayName("Order 5 (Can't avoid duplicate key in H2, so throws DAOException instead of returning 0")
    @Test
    void addStudentToCourse_shouldThrowDAOException_whenAddStudentWhichAlreadyExist() throws DAOException {
        test.addStudentToCourseDuplicate(1, 3);
        assertThrows(DAOException.class, () -> test.addStudentToCourseDuplicate(1, 3).getAsInt());
        courseDao.deleteStudentFromCourse(1, 3);
    }

    @Test
    void deleteFromCourse_shouldReturn1_whenDeleteTheStudentFromTable() throws DAOException {
        test.addStudentToCourseDuplicate(1, 3);
        assertEquals(1, courseDao.deleteStudentFromCourse(1, 3).getAsInt());
    }

    @Test
    void deleteFromCourse_shouldReturn0_whenDeleteTheStudentWhichNotExist() throws DAOException {
        assertEquals(0, courseDao.deleteStudentFromCourse(0, 3).getAsInt());
    }
}
