package ua.foxminded.dao;

import ua.foxminded.dao.exception.DAOException;
import ua.foxminded.dao.newPackage.DAODataAssignerImpl;

public class DAOFacade {
    private final String createTablesScript = "src/main/resources/createTables.sql";
    private final String fillTablesScript = "src/main/resources/fillTables.sql";
    
    private SQLExecuter executer = new SQLExecuter();
    
    private DAODataAssigner daoAssigner= new DAODataAssignerImpl();
    
    public void prepareDB() {
        try {
        executer.runScript(createTablesScript);
        executer.runScript(fillTablesScript);
        daoAssigner.assignStudentsToGroups();
        daoAssigner.assignCoursesToStudents();
        } catch (DAOException daoE) {
            System.err.println("Exception Message : " + daoE.getMessage());
            System.exit(0);
        }        
    }
}
