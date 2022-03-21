package ua.foxminded.domain.models;

import java.util.List;


public class Group {
    private Integer group_id;
    private String group_name;
    private List<String> students;
    private int studentCount;
    
    public Integer getGroup_id() {
        return group_id;
    }
    public String getGroup_name() {
        return group_name;
    }
    
    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }
    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
    
    public int getStudentCount() {
        return studentCount;
    }
    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }
    public List<String> getStudents() {
        return students;
    }
    public void setStudents(List<String> students) {
        this.students = students;
    }
}
