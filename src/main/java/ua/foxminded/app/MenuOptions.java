package ua.foxminded.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 *
 */
public enum MenuOptions {
    OPTION1("To find all groups with less or equals student count - press 1"),
    OPTION2("To find all students related to course with given name - press 2"),
    OPTION3("To add new student - press 3"), 
    OPTION4("To delete student by STUDENT_ID - press 4"),
    OPTION5("To add a student to the course (from a list) - press 5"),
    OPTION6("To remove the student from one of his or her courses - press 6"), 
    OPTION7("Exit - press 0");

    private static final Logger log = LoggerFactory.getLogger(AppMenu.class.getName());

    private String description;

    private MenuOptions(String description) {
        this.description = description;
    }

    /**
     * The method returns option's description
     * 
     * @author Bogush Daria
     * @return String option's description
     */
    public String getDescription() {
        log.info("Get option's description");
        return description;
    }
}
