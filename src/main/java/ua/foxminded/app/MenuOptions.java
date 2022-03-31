package ua.foxminded.app;

public enum MenuOptions {
    OPTION1("To find all groups with less or equals student count - press 1"),
    OPTION2("To find all students related to course with given name - press 2"),
    OPTION3("To add new student - press 3"),
    OPTION4("To delete student by STUDENT_ID - press 4"),
    OPTION5("To add a student to the course (from a list) - press 5"),
    OPTION6("To remove the student from one of his or her courses - press 6"),
    OPTION7("Exit - press 0");
    
    private String description;
    
    private MenuOptions(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
