package database;

public class DBConnectionFactory {

    public JDBConnectionWrapper getConnectinWrapper(boolean test) {
        if (test) {
            return new JDBConnectionWrapper(Constants.Schemas.TEST);
        }
        return new JDBConnectionWrapper(Constants.Schemas.PRODUCTION);
    }
}
