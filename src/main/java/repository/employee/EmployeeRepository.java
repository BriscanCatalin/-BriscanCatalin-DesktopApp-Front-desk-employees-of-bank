package repository.employee;

import model.Employee_User;
import model.validation.Notification;
import repository.AuthenticationException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface EmployeeRepository {

    List<Employee_User> findAll();
    Notification<Employee_User> findByUsernameAndPassword(String username, String password) throws AuthenticationException, AuthenticationException;
    boolean save(Employee_User employee_user);
    boolean update(Employee_User employee_user, Long id);
    public Employee_User getEmployeeFromResultSet(ResultSet resultSet) throws SQLException ;
    void removeAll();
}
