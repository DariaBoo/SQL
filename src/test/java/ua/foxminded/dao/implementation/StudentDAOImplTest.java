package ua.foxminded.dao.implementation;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import ua.foxminded.dao.PreparationH2Test;
import ua.foxminded.dao.dataSource.DataSourceDAO;
import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.domain.Student;

@TestInstance(Lifecycle.PER_CLASS)
class StudentDAOImplTest {
    private final String configFile = "h2config.properties";
    private final String SCRIPT = "src/test/resources/tablesTest.sql";
    private StudentDAOImpl studentDao;

    @BeforeAll
    void initializeTestDatabase() throws DAOException, FileNotFoundException {
        studentDao = new StudentDAOImpl(configFile);
        PreparationH2Test test = new PreparationH2Test();
        test.executeScript(SCRIPT);
    }

    @Test
    void addStudent_shouldReturn1_whenInputFirstAndLastNames() throws DAOException {
        Student student = new Student.StudentBuidler().setFirstName("Harry").setLastName("Potter").build();
        assertEquals(1, studentDao.addStudent(student).getAsInt());
    }

    @Test
    void addStudent_shouldReturn1_whenInputEmptyFirstAndLastNames() throws DAOException {
        Student student = new Student.StudentBuidler().setFirstName("").setLastName("").build();
        assertEquals(1, studentDao.addStudent(student).getAsInt());
    }

    @Test
    void removeStudent_shouldReturn1_whenInputStudentID() throws DAOException {
        assertEquals(0, studentDao.removeStudent(10).getAsInt());
    }

    @Test
    void removeStudent_shouldReturn0_whenInputStudentIDWhichNotExist() throws DAOException {
        assertEquals(1, studentDao.removeStudent(10).getAsInt());
    }

    @Test
    void findStudentsByCourse() throws DAOException {
        List<Student> list = new ArrayList<Student>();
        list.add(new Student.StudentBuidler().setStudentID(2).setFirstName("Seamus").setLastName("Longbottom").build());
        list.add(new Student.StudentBuidler().setStudentID(3).setFirstName("Lisa").setLastName("Macmillan").build());
        list.add(new Student.StudentBuidler().setStudentID(4).setFirstName("Hannah").setLastName("Thomas").build());

        assertEquals(list, studentDao.findStudentsByCourse("potions").get());
    }
}
