package ua.foxminded.app;

import java.time.chrono.MinguoChronology;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.internal.ExactComparisonCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.dao.implementation.CourseDAOImpl;
import ua.foxminded.dao.implementation.GroupDAOImpl;
import ua.foxminded.dao.implementation.StudentDAOImpl;
import ua.foxminded.domain.Course;
import ua.foxminded.domain.Group;
import ua.foxminded.domain.Student;

public class MenuExecutor {
    //private static Scanner input = new Scanner(System.in);
    private static final int minGroupSize = 10;
    private static final int maxGroupSize = 30;
    private static final String groupFormat = "%3s|%10s|%17s%n";
    private static final String studentFormat = "%3s|%-30s|%-30s%n";
    private static final String courseFormat = "%3s|%-20s|%-100s|%n";
    private static final String logScannerInput = "Input by scanner";
    private static final String enterIdMessage = "Please enter student id: ";

    private static final Logger log = LoggerFactory.getLogger(MenuExecutor.class.getName());

    protected static final void executeOption1(Scanner input) throws DAOException {
        log.info("Start option 1");
        System.out.println("Please enter student count: ");
        log.debug(logScannerInput);
        int studentCount = input.nextInt();
        log.info("Check if studentCount is beetwen %d and %d, studentCount %d", minGroupSize, maxGroupSize,
                studentCount);
        if (studentCount >= minGroupSize && studentCount <= maxGroupSize) {
            log.debug("Take optional by method GroupDAOImpl.getInstance().selectBySize(studentCount)");
            List<Group> list = GroupDAOImpl.getInstance().selectBySize(studentCount).get();
            log.info("Check if list is empty");
            if (!list.isEmpty()) {
                System.out.print(String.format(groupFormat, "id", "group_name", "count_of_students"));
                log.info("Output list");
                list.stream().forEach(group -> System.out.printf(groupFormat, group.getGroupID(), group.getGroupName(),
                        group.getStudentCount()));
            } else {
                log.info("List is empty, output message");
                System.out.println("Group less or equal " + studentCount + " doesn't exist");
            }
        } else {
            log.info("Student count is {}, output message", studentCount);
            System.out.printf("Group size should be more than %d and less than %d%n", minGroupSize, maxGroupSize);
        }
        log.info("End option 1");
    }

    protected static final void executeOption2(Scanner input) throws DAOException {
        log.info("Start option 2");
        System.out.println("To display all courses press 2");
        log.debug(logScannerInput);
        int button = input.nextInt();
        if (button == 2) {
            log.info("Call method displayAllCourses()");
            displayAllCourses();
        }
        System.out.println("Please enter course name: ");
        log.debug(logScannerInput);
        input.nextLine();
        String courseName = input.nextLine();
        log.warn("Check if courseName is not null");
        if (courseName != null) {
            log.debug("Take optional by method StudentDAOImpl.getInstance().findStudentsByCourse(courseName)");
            List<Student> list = StudentDAOImpl.getInstance().findStudentsByCourse(courseName).get();
            log.info("Check if list is empty");
            if (!list.isEmpty()) {
                System.out.print(String.format(studentFormat, "id", "first_name", "last_name"));
                log.info("Output list");
                list.stream().forEach(student -> System.out.printf(studentFormat, student.getStudentID(),
                        student.getFirstName(), student.getLastName()));
            } else {
                log.info("List is empty, output message");
                System.out.println("The course with given name doesn't exist or name is incorectly");
            }
        }
        log.info("End option 2");
    }

    protected static final void executeOption3(Scanner input) throws DAOException {
        log.info("Start option 3");
        System.out.println("Please enter first name: ");
        input.nextLine();
        log.debug(logScannerInput);
        String firstName = input.nextLine();
        System.out.println("Please enter last name: ");
        log.debug(logScannerInput);
        String lastName = input.nextLine();
        log.info("Check if firstName and lastName is not empty");
        if (!firstName.isEmpty() && !lastName.isEmpty() && firstName != null && lastName != null) {
            log.trace("Create new Student with input firstName {} and lastName {}", firstName, lastName);
            Student student = new Student.StudentBuidler().setFirstName(firstName).setLastName(lastName).build();
            int result = StudentDAOImpl.getInstance().addStudent(student).getAsInt();
            System.out.println(result);
            log.debug("Took result {} of adding the student", result);
            System.out.println(result != 0 ? "The student was added correctly \n"
                    : "Error occurs while adding a student. Please try again\n");
        } else {
            log.info("Empty firstName or lastName, output message");
            System.out.println("Can't add student to the database with empty first or last name");
        }
        log.info("End option 3");
    }

    protected static final void executeOption4(Scanner input) throws DAOException {
        log.info("Start option 4");
        System.out.println(enterIdMessage);
        log.debug(logScannerInput);
        int studentID = input.nextInt();
        log.info("Check if studentID is bigger than 0");
        if (studentID > 0) {            
            int result = StudentDAOImpl.getInstance().removeStudent(studentID).getAsInt();
            log.debug("Took result {} of deleting the student", result);
            System.out.println(result != 0 ? "The student was deleted correctly\n"
                    : "Student with id " + studentID + " doesn't exist\n");
        }
        log.info("End option 4");
    }

    protected static final void executeOption5(Scanner input) throws DAOException {
        log.info("Start option 5");
        System.out.println(enterIdMessage);
        log.debug(logScannerInput);
        int studentID = input.nextInt();
        log.info("Call the method displayCoursesByStudentID(studentID={})", studentID);
        displayCoursesByStudentID(studentID);
        System.out.println("To display all courses press 2");
        log.debug(logScannerInput);
        int button = input.nextInt();
        if (button == 2) {
            log.info("Call method displayAllCourses()");
            displayAllCourses();
        }
        System.out.println("Please enter course id: ");
        log.debug(logScannerInput);
        int courseID = input.nextInt();
        if (studentID > 0 && courseID > 0) {
            int result = CourseDAOImpl.getInstance().addStudentToCourse(studentID, courseID).getAsInt();
            log.debug("Took result {} of adding a student to the course", result);
            System.out.println(result);
            System.out.println(result != 0 ? "The student was added to the course\n"
                    : "The course already added\n");
        }
        log.info("Call the method displayCoursesByStudentID(studentID={})", studentID);
        displayCoursesByStudentID(studentID);
        log.info("End option 5");
    }

    protected static final void executeOption6(Scanner input) throws DAOException {
        log.info("Start option 6");
        System.out.println(enterIdMessage);
        log.debug(logScannerInput);
        int studentID = input.nextInt();
        log.info("Call method displayCoursesByStudentID(studentID={})", studentID);
        displayCoursesByStudentID(studentID);
        System.out.println("Please enter course id: ");
        log.debug(logScannerInput);
        int courseID = input.nextInt();
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
        log.debug("Take optional by method CourseDAOImpl.getInstance().findAllCourses()");
        Optional<List<Course>> optional = CourseDAOImpl.getInstance().findAllCourses();
        log.info("Output optional if present");
        System.out.print(String.format(courseFormat, "id", "name", "description"));
        optional.ifPresent(list -> list.stream().forEach(course -> System.out.printf(courseFormat, course.getCourseID(),
                course.getCourseName(), course.getCourseDescription())));
        log.info("End of courses displaying");
    }

    private static void displayCoursesByStudentID(int studentID) throws DAOException {
        log.info("Start display courses by student id {}", studentID);
        log.debug("Take optional by method CourseDAOImpl.getInstance().findCoursesByStudent(studentID)");
        Optional<List<Course>> optional = CourseDAOImpl.getInstance().findCoursesByStudentID(studentID);
        System.out.print(String.format(courseFormat, "id", "name", "description"));
        log.info("Output optional if present");
        optional.ifPresent(list -> list.stream().forEach(course -> System.out.printf(courseFormat, course.getCourseID(),
                course.getCourseName(), course.getCourseDescription())));
        log.info("End of courses displaying");
    }
}
