package service.employee;

import model.Client_Info;
import model.Employee_User;
import model.validation.Notification;
import repository.AuthenticationException;

public interface AuthenticationEmployeeService {

    Notification<Boolean> register(String username, String password);

    Notification<Employee_User> login(String username, String password) throws AuthenticationException;

    boolean logout(Employee_User employee_user);

}
