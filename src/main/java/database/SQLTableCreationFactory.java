package database;


import static database.Constants.Tables.*;

public class SQLTableCreationFactory {

    public String getCreateSQLForTable(String table) {
        switch (table) {
            case CLIENT_INFO:
                return "CREATE TABLE IF NOT EXISTS client (" +
                        "  id int(11) NOT NULL AUTO_INCREMENT," +
                        "  name varchar(500) NOT NULL," +
                        "  identity_card_number varchar(500) NOT NULL," +
                        "  personal_numerical_code varchar(500) NOT NULL," +
                        "  address varchar(500) NOT NULL," +
                        "  bills varchar(500) NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE KEY id_UNIQUE (id)" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
            case ACCOUNT:
                return "CREATE TABLE IF NOT EXISTS client_account (" +
                        "  id int(11) NOT NULL AUTO_INCREMENT," +
                        "  identification_number int(11) NOT NULL," +
                        "  type varchar(500) NOT NULL," +
                        "  amount_of_money int(11)," +
                        "  date_of_creation datetime DEFAULT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE KEY id_UNIQUE (id)" +
                        ") ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;";
            case ADMIN_USER:
                return "CREATE TABLE IF NOT EXISTS admin_user (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  username VARCHAR(200) NOT NULL," +
                        "  password VARCHAR(64) NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  UNIQUE INDEX username_UNIQUE (username ASC));";
            case EMPLOYEE_USER:
                return "CREATE TABLE IF NOT EXISTS employee_user (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  username VARCHAR(200) NOT NULL," +
                        "  password VARCHAR(64) NOT NULL," +
                        "  report varchar(30000) ," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  UNIQUE INDEX username_UNIQUE (username ASC));";
            case ROLE:
                return "  CREATE TABLE IF NOT EXISTS role (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  role VARCHAR(100) NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  UNIQUE INDEX role_UNIQUE (role ASC));";

            case RIGHT:
                return "  CREATE TABLE IF NOT EXISTS `right` (" +
                        "  `id` INT NOT NULL AUTO_INCREMENT," +
                        "  `right` VARCHAR(100) NOT NULL," +
                        "  PRIMARY KEY (`id`)," +
                        "  UNIQUE INDEX `id_UNIQUE` (`id` ASC)," +
                        "  UNIQUE INDEX `right_UNIQUE` (`right` ASC));";
            case BILL:
                return "  CREATE TABLE IF NOT EXISTS `bill` (" +
                        "  `id` INT NOT NULL AUTO_INCREMENT," +
                        "  `name` VARCHAR(100) NOT NULL," +
                        "  `cost` int not null," +
                        "  PRIMARY KEY (`id`)," +
                        "  UNIQUE INDEX `id_UNIQUE` (`id` ASC));";

            case ROLE_RIGHT:
                return "  CREATE TABLE IF NOT EXISTS role_right (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  role_id INT NOT NULL," +
                        "  right_id INT NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  INDEX role_id_idx (role_id ASC)," +
                        "  INDEX right_id_idx (right_id ASC)," +
                        "  CONSTRAINT role_id" +
                        "    FOREIGN KEY (role_id)" +
                        "    REFERENCES role (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE," +
                        "  CONSTRAINT right_id" +
                        "    FOREIGN KEY (right_id)" +
                        "    REFERENCES `right` (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";

            case EMPLOYEE_ROLE:
                return "\tCREATE TABLE IF NOT EXISTS employee_role (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  employee_id INT NOT NULL," +
                        "  role_id INT NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  INDEX employee_id_idx (employee_id ASC)," +
                        "  INDEX role_id_idx (role_id ASC));" ;

            case ADMIN_ROLE:
                return "\tCREATE TABLE IF NOT EXISTS admin_role (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  admin_id INT NOT NULL," +
                        "  role_id INT NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  INDEX admin_id_idx (admin_id ASC)," +
                        "  INDEX role_id_idx (role_id ASC));";

            default:
                return "";

        }
    }
}
