package ua.foxminded.app;

import java.util.EnumSet;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.dao.implementation.DAOLauncherImpl;

/**
 * 
 * @author Bogush Daria
 * @version 1.0
 *
 */
public class AppMenu {
    private static final Logger log = LoggerFactory.getLogger(AppMenu.class.getName());
    private static final Scanner input = new Scanner(System.in);

    /**
     * The method launch data to database and execute menu options by
     * MenuExecuter.class
     * 
     * @see MenuExecuter.class
     * @author Bogush Daria
     * @param args
     */
    public static void main(String[] args) {
        log.trace("Start program");
        log.info("Prepare database");
        DAOLauncherImpl.getInstance().prepareDB();
        int button = 0;
        log.info("Do menu");
        do {
            System.out.println("To display menu press 1");
            button = input.nextInt();
            log.debug("Input by scanner {}", button);
            if (button == 1) {
                log.trace("Display menu");
                displayMenu();
            }
            button = input.nextInt();
            log.debug("Input by scanner {}", button);
            try {
                if (button == 1) {
                    log.info("Execute option 1");
                    MenuExecutor.executeOption1(input);
                } else if (button == 2) {
                    log.info("Execute option 2");
                    MenuExecutor.executeOption2(input);
                } else if (button == 3) {
                    log.info("Execute option 3");
                    MenuExecutor.executeOption3(input);
                } else if (button == 4) {
                    log.info("Execute option 4");
                    MenuExecutor.executeOption4(input);
                } else if (button == 5) {
                    log.info("Execute option 5");
                    MenuExecutor.executeOption5(input);
                } else if (button == 6) {
                    log.info("Execute option 6");
                    MenuExecutor.executeOption6(input);
                }
            } catch (DAOException daoE) {
                log.error("Error occured while execute menu options", daoE);               
            }
        } while (button != 0);
        input.close();
        log.info("Scanner is closed");
        log.trace("End program");
    }

    private static void displayMenu() {
        log.trace("Start to display menu");
        for (MenuOptions option : EnumSet.allOf(MenuOptions.class)) {
            System.out.println(option.getDescription());
        }
        log.trace("End of menu display");
    }
}
