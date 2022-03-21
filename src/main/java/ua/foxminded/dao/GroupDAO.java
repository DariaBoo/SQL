package ua.foxminded.dao;

import java.util.List;

import ua.foxminded.domain.models.Group;

public interface GroupDAO {

    public List<Group> findAllByCountOfStudent(int count);
    
}
