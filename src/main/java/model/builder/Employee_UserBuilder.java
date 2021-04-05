package model.builder;

import model.Employee_User;
import model.Role;

import java.util.List;

public class Employee_UserBuilder {

    private Employee_User employee_user;

    public Employee_UserBuilder(){
        employee_user = new Employee_User();
    }

    public Employee_UserBuilder setId(Long id){
        employee_user.setId(id);
        return this;
    }

    public Employee_UserBuilder setUsername(String username){
        employee_user.setUsername(username);
        return this;
    }

    public Employee_UserBuilder setPassword(String password){
        employee_user.setPassword(password);
        return this;
    }

    public  Employee_UserBuilder setRoles(List<Role> roles){
        employee_user.setRoles(roles);
        return this;
    }

    public  Employee_UserBuilder getRoles(){
        employee_user.getRoles();
        return this;
    }
    public  Employee_UserBuilder setReport(String report){
        employee_user.setReport(report);
        return this;
    }

    public Employee_User build(){
        return employee_user;
    }

}
