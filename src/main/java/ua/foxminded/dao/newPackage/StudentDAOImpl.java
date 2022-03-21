package ua.foxminded.dao.newPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.dao.ConnectionManager;
import ua.foxminded.dao.StudentDAO;
import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.domain.models.Student;

public class StudentDAOImpl implements StudentDAO {
    private final String SQL_ADDSTUDENT = "INSERT INTO schoolManager.students (first_name, last_name) values (?,?)";
    private final String SQL_REMOVESTUDENT = "DELETE FROM schoolManager.students WHERE student_id = ?";
    
    private Connection connection;
    public StudentDAOImpl() {

    }

    public StudentDAOImpl(Connection connection) {
        this.connection = connection;
    }
     
    @Override
    public void addStudent(Student student) throws DAOException {
        try (Connection connection = ConnectionManager.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_ADDSTUDENT)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.execute();
        } catch (SQLException sqlE) {
            throw new DAOException("", sqlE);
        }
    }

    @Override
    public void removeStudent(int id) throws DAOException {
        try (Connection connection = ConnectionManager.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_REMOVESTUDENT)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException sqlE) {
            throw new DAOException("", sqlE);
        }
    }

    @Override
    public List<Student> findAllByCourse(int id) {

        return new ArrayList<Student>();
    }

}
