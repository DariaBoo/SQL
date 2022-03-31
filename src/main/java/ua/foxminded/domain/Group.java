package ua.foxminded.domain;

import java.util.List;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 *
 */
public class Group {
    private int groupID;
    private String groupName;
    private List<String> students;
    private int studentCount;

    public Group(int groupID, String groupName, int studentCount) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.studentCount = studentCount;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + groupID;
        result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
        result = prime * result + studentCount;
        result = prime * result + ((students == null) ? 0 : students.hashCode());
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
        Group other = (Group) obj;
        if (groupID != other.groupID)
            return false;
        if (groupName == null) {
            if (other.groupName != null)
                return false;
        } else if (!groupName.equals(other.groupName))
            return false;
        if (studentCount != other.studentCount)
            return false;
        if (students == null) {
            if (other.students != null)
                return false;
        } else if (!students.equals(other.students))
            return false;
        return true;
    }
}
