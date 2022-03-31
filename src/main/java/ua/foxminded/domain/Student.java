package ua.foxminded.domain;

import java.util.List;
import java.util.Objects;

public class Student {
    private int studentID;
    private int groupID;
    private String firstName;
    private String lastName;
    private List<String> courses;
    
    
    private Student(int studentID, String firstName, String lastName, int groupID, List<String> courses) {    
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupID = groupID;
        this.courses = courses;
    }
    
    public static StudentBuidler builder() {
        return new StudentBuidler();
    }
   
    public static class StudentBuidler{
        private int studentID;
        private int groupID;
        private String firstName;
        private String lastName;
        private List<String> courses;
        
        public StudentBuidler setStudentID(int studentID) {
            if(studentID>0) {
            this.studentID = studentID;
            }
            return this;
        }
        public StudentBuidler setFirstName(String firstName) {
            this.firstName = Objects.requireNonNull(firstName,
                    "Student's name must not be null. NullPointerException inside Student constructor.");
            return this;
        }
        public StudentBuidler setLastName(String lastName) {
            this.lastName = Objects.requireNonNull(lastName,
                    "Student's name must not be null. NullPointerException inside Student constructor.");
            return this;
        }
        public StudentBuidler setGroupID(int groupID) {
            if(groupID>0) {
            this.groupID = groupID;
            }
            return this;
        }       
        public StudentBuidler setCourses(List<String> courses) {
            this.courses = courses;
            return this;
        }
        public Student buildWith(Object object) {
            return construct(object).build();
        }
        private StudentBuidler construct(Object object) {
            return this;
        }
        public Student build() {
            return new Student(studentID, firstName, lastName, groupID, courses);
        }
    }

    public int getStudentID() {
        return studentID;
    }

    public int getGroupID() {
        return groupID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getCourses() {
        return courses;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((courses == null) ? 0 : courses.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + groupID;
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + studentID;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (courses == null) {
            if (other.courses != null)
                return false;
        } else if (!courses.equals(other.courses))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (groupID != other.groupID)
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (studentID != other.studentID)
            return false;
        return true;
    }    
}
