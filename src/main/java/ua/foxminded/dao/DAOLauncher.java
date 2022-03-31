package ua.foxminded.dao;

public interface DAOLauncher {
    public void prepareDB(String createTablesScript, String fillTablesScript);
}
