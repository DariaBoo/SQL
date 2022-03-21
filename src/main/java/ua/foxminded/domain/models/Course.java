package ua.foxminded.domain.models;

import java.util.List;

public class Course {
    private String course_id;
    private String course_name;
    private String course_description;
    private List<Integer> students;
    public String getCourse_id() {
        return course_id;
    }
    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }
    public String getCourse_name() {
        return course_name;
    }
    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
    public String getCourse_description() {
        return course_description;
    }
    public void setCourse_description(String course_description) {
        this.course_description = course_description;
    }
    public List<Integer> getStudents() {
        return students;
    }
    public void setStudents(List<Integer> students) {
        this.students = students;
    }

}
