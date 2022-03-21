package ua.foxminded.dao;

import java.util.List;

import ua.foxminded.domain.models.Course;

public interface CourseDAO {
    public List<Course> findAllCourses();
    public List<Course> findCoursesByStudent(Integer id);
    public void addToCourse(Integer studentID);
    public void deleteFromCourse(Integer studentID);
}
