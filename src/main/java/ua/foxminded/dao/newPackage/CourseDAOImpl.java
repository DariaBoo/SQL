package ua.foxminded.dao.newPackage;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.dao.CourseDAO;
import ua.foxminded.domain.models.Course;

public class CourseDAOImpl implements CourseDAO {
    private Connection connection;
    
    public CourseDAOImpl() {

    }

    public CourseDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Course> findAllCourses() {
        // TODO Auto-generated method stub
        return new ArrayList<Course>();
    }

    @Override
    public List<Course> findCoursesByStudent(Integer id) {
        // TODO Auto-generated method stub
        return new ArrayList<Course>();
    }

    @Override
    public void addToCourse(Integer studentID) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteFromCourse(Integer studentID) {
        // TODO Auto-generated method stub

    }

}
