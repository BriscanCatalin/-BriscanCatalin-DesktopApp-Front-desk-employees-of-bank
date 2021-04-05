package service.employee;

import model.Employee_User;
import model.Role;
import model.builder.Employee_UserBuilder;
import model.validation.Employee_UserValidator;
import model.validation.Notification;
import repository.AuthenticationException;
import repository.employee.EmployeeRepository;
import repository.security.RightsRolesRepository;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

import static database.Constants.Roles.EMPLOYEE;

public class AuthenticationEmployeeServiceMySQL implements  AuthenticationEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthenticationEmployeeServiceMySQL(EmployeeRepository employeeRepository, RightsRolesRepository rightsRolesRepository) {
        this.employeeRepository = employeeRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> register(String username, String password) {
        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        Employee_User employee_user = new Employee_UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(employeeRole))
                .setReport(new String())
                .build();

        Employee_UserValidator employee_userValidator = new Employee_UserValidator(employee_user);
        boolean employeeValid = employee_userValidator.validate();
        Notification<Boolean> employeeRegisterNotification = new Notification<>();

        if (!employeeValid) {
            employee_userValidator.getErrors().forEach(employeeRegisterNotification::addError);
            employeeRegisterNotification.setResult(Boolean.FALSE);
        } else {
            employee_user.setPassword(encodePassword(password));
            employeeRegisterNotification.setResult(employeeRepository.save(employee_user));
        }
        return employeeRegisterNotification;
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte [] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexStr = new StringBuilder();

            for (int i = 0 ;i < hash.length ;i++){
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexStr.append('0');
                hexStr.append(hex);
            }

            return hexStr.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Notification<Employee_User> login(String username, String password) throws AuthenticationException {
        return employeeRepository.findByUsernameAndPassword(username, encodePassword(password));
    }

    @Override
    public boolean logout(Employee_User employee_user) {
        return false;
    }
}
