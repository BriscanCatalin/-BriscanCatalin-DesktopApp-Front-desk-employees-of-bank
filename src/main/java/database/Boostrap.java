package database;

import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.RIGHTS_ADMIN;
import static database.Constants.Rights.RIGHTS_EMPLOYEE;
import static database.Constants.Roles.ROLES;
import static database.Constants.Schemas.SCHEMAS;
import static database.Constants.getRolesRights;

public class Boostrap {

    private static RightsRolesRepository rightsRolesRepository;

    public static void main(String[] args) throws SQLException {
        //dropAll();

        bootstrapTables();

        bootstrapData();
    }

    private static void dropAll() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Dropping all tables in schema: " + schema);

            Connection connection = new JDBConnectionWrapper(schema).getConnection();
            Statement statement = connection.createStatement();

            String[] dropStatements = {
                    "TRUNCATE `role_right`;",
                    "DROP TABLE `role_right`;",
                    "TRUNCATE `right`;",
                    "DROP TABLE `right`;",
                    "TRUNCATE `employee_role`;",
                    "DROP TABLE `employee_role`;",
                    "TRUNCATE `admin_role`;",
                    "DROP TABLE `admin_role`;",
                    "TRUNCATE `role`;",
                    "DROP TABLE `role`;",
                    "TRUNCATE `bill`;",
                    "DROP TABLE `bill`;",
                    "TRUNCATE `client`;",
                    "DROP TABLE `client`;",
                    "DROP TABLE  `client_account`, `employee_user`, `admin_user`;"
            };

            Arrays.stream(dropStatements).forEach(dropStatement -> {
                try {
                    statement.execute(dropStatement);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapTables() throws SQLException {
        SQLTableCreationFactory sqlTableCreationFactory = new SQLTableCreationFactory();

        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping " + schema + " schema");


            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            Connection connection = connectionWrapper.getConnection();

            Statement statement = connection.createStatement();

            for (String table : Constants.Tables.ORDERED_TABLES_FOR_CREATION) {
                String createTableSQL = sqlTableCreationFactory.getCreateSQLForTable(table);
                System.out.println(table);
                statement.execute(createTableSQL);
            }
        }

        System.out.println("Done table bootstrap");
    }

    private static void bootstrapData() throws SQLException {
        for (String schema : SCHEMAS) {
            System.out.println("Bootstrapping admin and employee data data for " + schema);

            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
            rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());

            bootstrapRoles();
            bootstrapRightsAdmin();
            bootstrapRightsEmployee();
            bootstrapRoleRight();
        }
    }

    private static void bootstrapRoles() throws SQLException {
        for (String role : ROLES) {
            rightsRolesRepository.addRole(role);
        }
    }

    private static void bootstrapRightsAdmin() throws SQLException {
        for (String right : RIGHTS_ADMIN) {
            rightsRolesRepository.addRight(right);
        }
    }

    private static void bootstrapRightsEmployee() throws SQLException {
        for (String right : RIGHTS_EMPLOYEE) {
            rightsRolesRepository.addRight(right);
        }
    }

    private static void bootstrapRoleRight() throws SQLException {
        Map<String, List<String>> rolesRights = getRolesRights();

        for (String role : rolesRights.keySet()) {
            Long roleId = rightsRolesRepository.findRoleByTitle(role).getId();

            for (String right : rolesRights.get(role)) {
                Long rightId = rightsRolesRepository.findRightByTitle(right).getId();

                rightsRolesRepository.addRoleRight(roleId, rightId);
            }
        }
    }
}
