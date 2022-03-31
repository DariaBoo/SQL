package ua.foxminded.app;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.dao.implementation.CourseDAOImpl;
import ua.foxminded.dao.implementation.GroupDAOImpl;
import ua.foxminded.dao.implementation.StudentDAOImpl;
import ua.foxminded.domain.Course;
import ua.foxminded.domain.Group;
import ua.foxminded.domain.Student;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 *
 */
public class MenuExecutor {
    private static final int MIN_GROUP_SIZE = 10;
    private static final int MAX_GROUP_SIZE = 30;
    private static final String GROUP_FORMAT = "%3s|%10s|%17s%n";
    private static final String STUDENT_FORMAT = "%3s|%-30s|%-30s%n";
    private static final String COURSE_FORMAT = "%3s|%-20s|%-100s|%n";
    private static final String LOG_SCANNER_INPUT = "Input by scanner {}";
    private static final String ENTER_ID_MESSAGE = "Please enter student id: ";
    private static final Logger log = LoggerFactory.getLogger(MenuExecutor.class.getName());

    /**
     * The method finds all groups with less or equals student count
     * 
     * @author Bogush Daria
     * @param input The scanner
     * @throws DAOException if a database access error occurs.
     */
    protected static final void executeOption1(Scanner input) throws DAOException {
        log.info("Start option 1");
        System.out.println("Please enter student count: ");
        int studentCount = input.nextInt();
        log.debug(LOG_SCANNER_INPUT, studentCount);
        log.info("Check if studentCount is beetwen %d and %d, studentCount %d", MIN_GROUP_SIZE, MAX_GROUP_SIZE,
                studentCount);
        if (studentCount >= MIN_GROUP_SIZE && studentCount <= MAX_GROUP_SIZE) {
            List<Group> list = GroupDAOImpl.getInstance().selectBySize(studentCount).get();
            log.debug("Took optional {} by method GroupDAOImpl.getInstance().selectBySize(studentCount)", list);
            log.info("Check if list is empty");
            if (!list.isEmpty()) {
                System.out.print(String.format(GROUP_FORMAT, "id", "group_name", "count_of_students"));
                list.stream().forEach(group -> System.out.printf(GROUP_FORMAT, group.getGroupID(), group.getGroupName(),
                        group.getStudentCount()));
                log.info("Output list");
            } else {
                System.out.println("Group less or equal " + studentCount + " doesn't exist");
                log.info("List is empty, output message");
            }
        } else {
            System.out.printf("Group size should be more than %d and less than %d%n", MIN_GROUP_SIZE, MAX_GROUP_SIZE);
            log.info("Student count is {}, output message", studentCount);
        }
        log.info("End option 1");
    }

    /**
     * The method finds all students related to course with given name
     * 
     * @author Bogush Daria
     * @param input The scanner
     * @throws DAOException if a database access error occurs.
     */
    protected static final void executeOption2(Scanner input) throws DAOException {
        log.info("Start option 2");
        System.out.println("Press 2 to display all courses, otherwise any button.");
        int button = input.nextInt();
        log.debug(LOG_SCANNER_INPUT, button);
        if (button == 2) {
            displayAllCourses();
            log.info("Executed the method displayAllCourses()");
        }
        System.out.println("Please enter course name: ");
        input.nextLine();
        String courseName = input.nextLine();
        log.debug(LOG_SCANNER_INPUT, courseName);
        log.warn("Check if courseName is not null");
        if (courseName != null) {
            List<Student> list = StudentDAOImpl.getInstance().findStudentsByCourse(courseName).get();
            log.debug("Took optional {} by method StudentDAOImpl.getInstance().findStudentsByCourse(courseName)", list);
            log.warn("Check if list is empty");
            if (!list.isEmpty()) {
                System.out.print(String.format(STUDENT_FORMAT, "id", "first_name", "last_name"));
                list.stream().forEach(student -> System.out.printf(STUDENT_FORMAT, student.getStudentID(),
                        student.getFirstName(), student.getLastName()));
                log.info("Output list");
            } else {
                System.out.println("The course with given name doesn't exist or name is incorectly");
                log.info("List is empty, output message");
            }
        }
        log.info("End option 2");
    }

    /**
     * The method adds new student
     * 
     * @author Bogush Daria
     * @param input The scanner
     * @throws DAOException if a database access error occurs.
     */
    protected static final void executeOption3(Scanner input) throws DAOException {
        log.info("Start option 3");
        System.out.println("Please enter first name: ");
        input.nextLine();
        String firstName = input.nextLine();
        log.debug(LOG_SCANNER_INPUT, firstName);
        System.out.println("Please enter last name: ");
        String lastName = input.nextLine();
        log.debug(LOG_SCANNER_INPUT, lastName);
        log.info("Check if firstName and lastName is not empty");
        if (!firstName.isEmpty() && !lastName.isEmpty() && firstName != null && lastName != null) {
            Student student = new Student.StudentBuidler().setFirstName(firstName).setLastName(lastName).build();
            log.debug("Create new Student with input firstName {} and lastName {}", firstName, lastName);
            int result = StudentDAOImpl.getInstance().addStudent(student).getAsInt();
            log.debug("Took result {} of adding the student", result);
            System.out.println(result != 0 ? "The student was added correctly \n"
                    : "Error occurs while adding a student. Please try again\n");
        } else {
            log.info("Empty firstName or lastName, output message");
            System.out.println("Can't add student to the database with empty first or last name");
        }
        log.info("End option 3");
    }

    /**
     * The method deletes student by STUDENT_ID
     * 
     * @author Bogush Daria
     * @param input The scanner
     * @throws DAOException if a database access error occurs.
     */
    protected static final void executeOption4(Scanner input) throws DAOException {
        log.info("Start option 4");
        System.out.println(ENTER_ID_MESSAGE);
        int studentID = input.nextInt();
        log.debug(LOG_SCANNER_INPUT, studentID);
        log.warn("Check if studentID is bigger than 0");
        if (studentID > 0) {
            int result = StudentDAOImpl.getInstance().removeStudent(studentID).getAsInt();
            log.debug("Took result {} of deleting the student", result);
            System.out.println(result != 0 ? "The student was deleted correctly\n"
                    : "Student with id " + studentID + " doesn't exist\n");
        }
        log.info("End option 4");
    }

    /**
     * The method adds a student to the course (from a list)
     * 
     * @author Bogush Daria
     * @param input The scanner
     * @throws DAOException if a database access error occurs.
     */
    protected static final void executeOption5(Scanner input) throws DAOException {
        log.info("Start option 5");
        System.out.println(ENTER_ID_MESSAGE);
        int studentID = input.nextInt();
        log.debug(LOG_SCANNER_INPUT, studentID);
        displayCoursesByStudentID(studentID);
        log.info("Executed the method displayCoursesByStudentID(studentID={})", studentID);
        System.out.println("Press 2 to display all courses, otherwise any button.");
        int button = input.nextInt();
        log.debug(LOG_SCANNER_INPUT, button);
        if (button == 2) {
            displayAllCourses();
            log.info("Call method displayAllCourses()");
        }
        System.out.println("Please enter course id: ");
        int courseID = input.nextInt();
        log.debug(LOG_SCANNER_INPUT, courseID);
        log.info("Check if studentId {} and courseID {] is bigger than 0", studentID, courseID);
        if (studentID > 0 && courseID > 0) {
            int result = CourseDAOImpl.getInstance().addStudentToCourse(studentID, courseID).getAsInt();
            log.debug("Took result {} of adding a student to the course", result);
            System.out.println(result);
            System.out.println(result != 0 ? "The student was added to the course\n" : "The course already added\n");
        }
        displayCoursesByStudentID(studentID);
        log.info("Executed the method displayCoursesByStudentID(studentID={})", studentID);
        log.info("End option 5");
    }

    /**
     * The method removes the student from one of his or her courses
     * 
     * @author Bogush Daria
     * @param input The scanner
     * @throws DAOException if a database access error occurs.
     */
    protected static final void executeOption6(Scanner input) throws DAOException {
        log.info("Start option 6");
        System.out.println(ENTER_ID_MESSAGE);
        int studentID = input.nextInt();
        log.debug(LOG_SCANNER_INPUT, studentID);
        displayCoursesByStudentID(studentID);
        log.info("Executed the method displayCoursesByStudentID(studentID={})", studentID);
        System.out.println("Please enter course id to delete: ");
        int courseID = input.nextInt();
        log.debug(LOG_SCANNER_INPUT, courseID);
        int result = 0;
        log.info("Check if studentId and courseId is bigger than 0");
        if (studentID > 0 && courseID > 0) {
            result = CourseDAOImpl.getInstance().deleteFromCourse(studentID, courseID).getAsInt();
            log.debug("Took result {} of deleting the student from the course", result);
        }
        if (result != 0) {
            log.info("The student was removed from the course");
            System.out.println("The student was removed from the course\n");
            log.info("Call the method displayCoursesByStudentID(studentID={})", studentID);
            displayCoursesByStudentID(studentID);
        } else {
            log.info("The student wasn't removed from the corse");
            System.out.println("Error occurs while removing the student from the course\n");
        }
        log.info("End option 6");
    }

    private static void displayAllCourses() throws DAOException {
        log.info("Start display all courses");
        Optional<List<Course>> optional = CourseDAOImpl.getInstance().findAllCourses();
        log.debug("Took optional by method CourseDAOImpl.getInstance().findAllCourses()");
        System.out.print(String.format(COURSE_FORMAT, "id", "name", "description"));
        optional.ifPresent(list -> list.stream().forEach(course -> System.out.printf(COURSE_FORMAT,
                course.getCourseID(), course.getCourseName(), course.getCourseDescription())));
        log.info("Output optional if present");
        log.info("End of courses displaying");
    }

    private static void displayCoursesByStudentID(int studentID) throws DAOException {
        log.info("Start display courses by student id {}", studentID);
        Optional<List<Course>> optional = CourseDAOImpl.getInstance().findCoursesByStudentID(studentID);
        log.debug("Took optional by method CourseDAOImpl.getInstance().findCoursesByStudent(studentID)");
        System.out.print(String.format(COURSE_FORMAT, "id", "name", "description"));
        optional.ifPresent(list -> list.stream().forEach(course -> System.out.printf(COURSE_FORMAT,
                course.getCourseID(), course.getCourseName(), course.getCourseDescription())));
        log.info("Output optional if present");
        log.info("End of courses displaying");
    }
}
