package repository.employee;


import model.Employee_User;
import model.builder.Employee_UserBuilder;
import model.validation.Notification;
import repository.AuthenticationException;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.List;

import static database.Constants.Tables.EMPLOYEE_USER;

public class EmployeeRepositoryMySQL implements EmployeeRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;
    private Employee_User employee_user;

    public EmployeeRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<Employee_User> findAll() {
        return null;
    }

    @Override
    public Notification<Employee_User> findByUsernameAndPassword(String username, String password) throws AuthenticationException {
        Notification<Employee_User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchEmployeeSql = "Select * from `" + EMPLOYEE_USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet employeeResultSet = statement.executeQuery(fetchEmployeeSql);
            if (employeeResultSet.next()) {
                Employee_User employee_user = new Employee_UserBuilder()
                        .setUsername(employeeResultSet.getString("username"))
                        .setPassword(employeeResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForEmployee(employeeResultSet.getLong("id")))
                        .setReport(employeeResultSet.getString("report"))
                        .build();
                findByUsernameAndPasswordNotification.setResult(employee_user);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AuthenticationException();
        }
    }

    @Override
    public boolean save(Employee_User employee_user) {
        this.employee_user = employee_user;
        try {
            PreparedStatement insertEmployeeStatement = connection
                    .prepareStatement("INSERT INTO employee_user values (null, ?, ?, ?)");
            insertEmployeeStatement.setString(1, employee_user.getUsername());
            insertEmployeeStatement.setString(2, employee_user.getPassword());
            insertEmployeeStatement.setString(3, employee_user.getReport());
            insertEmployeeStatement.executeUpdate();

            ResultSet rs = insertEmployeeStatement.getGeneratedKeys();
            rs.next();
            long adminId = rs.getLong(1);
            employee_user.setId(adminId);

            rightsRolesRepository.addRolesToEmployee_User(employee_user, employee_user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Employee_User employee_user, Long id) {
        this.employee_user = employee_user;
        try {
            PreparedStatement updateEmployeeStatement = connection
                    .prepareStatement("Update employee_user set username = ?, password = ?, report = ? where id = " + id + ";" );
            updateEmployeeStatement.setString(1, employee_user.getUsername());
            updateEmployeeStatement.setString(2, employee_user.getPassword());
            updateEmployeeStatement.setString(3, employee_user.getReport());
            updateEmployeeStatement.executeUpdate();


            employee_user.setId(id);

            rightsRolesRepository.addRolesToEmployee_User(employee_user, employee_user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }    }

    public Employee_User getEmployeeFromResultSet(ResultSet resultSet) throws SQLException {
        return new Employee_UserBuilder()
                .setId(resultSet.getLong("id"))
                .setUsername(resultSet.getString("username"))
                .setPassword(resultSet.getString("password"))
                .setReport(resultSet.getString("report"))
                 .build();
    }

    @Override
    public void removeAll() {

    }

}
