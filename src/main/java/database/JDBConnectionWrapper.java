package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBConnectionWrapper {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/";

    private static final String USER = "root";
    private static final String PASS = "0754651665Cc";
    private static final int TIMEOUT = 5;

    private Connection connection;

    public JDBConnectionWrapper(String schema){

        try{
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL + schema, USER , PASS);
            createTables();
        }catch (ClassNotFoundException  | SQLException e){
            e.printStackTrace();
        }
    }

    private void  createTables() throws  SQLException {

        Statement statement = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS client (" +
                "  id int(11) NOT NULL AUTO_INCREMENT," +
                "  name varchar(500) NOT NULL," +
                "  identity_card_number varchar(500) NOT NULL," +
                "  personal_numerical_code varchar(500) NOT NULL," +
                "  address varchar(500) NOT NULL," +
                "  bills varchar(500) NOT NULL," +
                "  PRIMARY KEY (id)," +
                "  UNIQUE KEY id_UNIQUE (id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
        statement.execute(sql);

        String sql1 =  "CREATE TABLE IF NOT EXISTS client_account (" +
                "  id int(11) NOT NULL AUTO_INCREMENT," +
                "  identification_number int(11) NOT NULL," +
                "  type varchar(500) NOT NULL," +
                "  amount_of_money int(11)," +
                "  date_of_creation datetime DEFAULT NULL," +
                "  PRIMARY KEY (id)," +
                "  UNIQUE KEY id_UNIQUE (id)" +
                ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
        statement.execute(sql1);


    }

    public boolean testConnection() throws SQLException{
        return connection.isValid(TIMEOUT);
    }
    public Connection getConnection(){
        return connection;
    }




}
