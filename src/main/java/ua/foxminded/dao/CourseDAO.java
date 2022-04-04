package ua.foxminded.dao;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.domain.Course;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 *
 */
public interface CourseDAO {
    /**
     * The method returns list of all courses from the database
     * 
     * @author Bogush Daria
     * @return List<Course>
     * @see Course
     * @throws DAOException if a database access error occurs.
     */
    public Optional<List<Course>> findAllCourses() throws DAOException;

    /**
     * The method returns list of student's courses, selecting by studentID from the
     * database
     * 
     * @author Bogush Daria
     * @param studentID
     * @return List<Course>
     * @see Course
     * @throws DAOException if a database access error occurs.
     */
    public Optional<List<Course>> findCoursesByStudentID(int studentID) throws DAOException;

    /**
     * The method adds student to course by studentID and courseID to the database.
     * 
     * @author Bogush Daria
     * @param studentID
     * @param courseID
     * @return number of added rows
     * @throws DAOException if a database access error occurs.
     */
    public OptionalInt addStudentToCourse(int studentID, int courseID) throws DAOException;

    /**
     * The method remove student from his/her courses by studentID and courseID from
     * the database
     * 
     * @author Bogush Daria
     * @param studentID
     * @param courseID
     * @return number of deleted rows
     * @throws DAOException if a database access error occurs.
     */
    public OptionalInt deleteStudentFromCourse(int studentID, int courseID) throws DAOException;
}
