package ua.foxminded.domain;

import ua.foxminded.domain.models.Student;

public interface StudentBuilder {
    public StudentBuilder setFirstName();
    public StudentBuilder setLastName();
    public StudentBuilder setGroupID();
    public StudentBuilder setCourse();
    public Student build();
}
