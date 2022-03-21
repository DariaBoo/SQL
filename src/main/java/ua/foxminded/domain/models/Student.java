package ua.foxminded.domain.models;

import java.util.List;

public class Student {
    private int studentID;
    private int groupID;
    private String firstName;
    private String lastName;
    private List<String> courses;
    
    public Student() {
       
    }
    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public int getStudentID() {
        return studentID;
    }
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }
    public int getGroupID() {
        return groupID;
    }
    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public List<String> getCourses() {
        return courses;
    }
    public void setCourses(List<String> courses) {
        this.courses = courses;
    }   
}
