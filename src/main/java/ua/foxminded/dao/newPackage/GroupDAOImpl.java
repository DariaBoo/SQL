package ua.foxminded.dao.newPackage;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.dao.GroupDAO;
import ua.foxminded.domain.models.Group;

public class GroupDAOImpl implements GroupDAO{
    private Connection connection;
    
    public GroupDAOImpl() {

    }

    public GroupDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Group> findAllByCountOfStudent(int count) {
        // TODO Auto-generated method stub
        return new ArrayList<>();
    }
}
