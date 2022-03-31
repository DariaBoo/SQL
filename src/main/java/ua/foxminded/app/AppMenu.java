package ua.foxminded.app;

import java.awt.Menu;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumSet;
import java.util.List;
import java.util.OptionalInt;
import java.util.Scanner;

import javax.activation.DataSource;

import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.foxminded.dao.dataSource.DataSourceDAO;
import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.dao.implementation.CourseDAOImpl;
import ua.foxminded.dao.implementation.DAOLauncherImpl;
import ua.foxminded.dao.implementation.GroupDAOImpl;
import ua.foxminded.dao.implementation.StudentDAOImpl;
import ua.foxminded.domain.Course;
import ua.foxminded.domain.Group;
import ua.foxminded.domain.Student;

public class AppMenu {
    private static final Logger log = LoggerFactory.getLogger(AppMenu.class.getName());
    private static Scanner input = new Scanner(System.in);

    private static final String createTablesScript = "src/main/resources/createTables.sql";
    private static final String fillTablesScript = "src/main/resources/fillTables.sql";

    
    public static void main(String[] args) throws SQLException {
        log.info("Start program");        
        log.info("Prepare database");
        DAOLauncherImpl.getInstance().prepareDB(createTablesScript, fillTablesScript);
        int button = 0;
        log.info("Do menu");
        do {
            System.out.println("To display menu press 1");
            log.debug("Input by scanner");
            button = input.nextInt();
            if (button == 1) {
                log.trace("Display menu");
                displayMenu();
            }
            log.debug("Input by scanner");
            button = input.nextInt();
            try {
                if (button == 1) {
                    log.debug("Execute option 1");
                    MenuExecutor.executeOption1(input);
                } else if (button == 2) {
                    log.debug("Execute option 2");
                    MenuExecutor.executeOption2(input);
                } else if (button == 3) {
                    log.debug("Execute option 3");
                    MenuExecutor.executeOption3(input);
                } else if (button == 4) {
                    log.debug("Execute option 4");
                    MenuExecutor.executeOption4(input);
                } else if (button == 5) {
                    log.debug("Execute option 5");
                    MenuExecutor.executeOption5(input);
                } else if (button == 6) {
                    log.debug("Execute option 6");
                    MenuExecutor.executeOption6(input);
                }
            } catch (DAOException daoE) {
                log.error("Error occured while execute menu options", daoE);
                System.err.println(daoE.getMessage());
                System.exit(0);
            }
        } while (button != 0);
        input.close();
        log.debug("Scanner is closed");
        log.info("End program");
    }

    private static void displayMenu() {
        log.info("Start to display menu");
        for (MenuOptions option : EnumSet.allOf(MenuOptions.class)) {
            System.out.println(option.getDescription());
        }
        log.info("End of menu display");
    }
}
