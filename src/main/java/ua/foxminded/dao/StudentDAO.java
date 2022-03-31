package ua.foxminded.dao;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.domain.Student;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 *
 */
public interface StudentDAO {
    /**
     * The method adds new student to the database
     * @author Bogush Daria
     * @param student
     * @see Student
     * @return number of adding rows
     * @throws DAOException if a database access error occurs.
     */
    public OptionalInt addStudent(Student student) throws DAOException;
    
    /**
     * The method removes student by studentID from the database
     * @author Bogush Daria
     * @param studentID
     * @return number of removing rows
     * @throws DAOException if a database access error occurs.
     */
    public OptionalInt removeStudent(int studentID) throws DAOException;
    
    /**
     * The method select students by courseName from the database
     * @author Bogush Daria
     * @param courseName
     * @return Lise<Student>
     * @see Student
     * @throws DAOException if a database access error occurs.
     */
    public Optional<List<Student>> findStudentsByCourse(String courseName) throws DAOException; 
}
