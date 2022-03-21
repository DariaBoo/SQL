package ua.foxminded.dao;

import java.util.List;

import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.domain.models.Student;

public interface StudentDAO {
    public void addStudent(Student student) throws DAOException;
    public void removeStudent(int id) throws DAOException;
    public List<Student> findAllByCourse(int id) throws DAOException; 
}
