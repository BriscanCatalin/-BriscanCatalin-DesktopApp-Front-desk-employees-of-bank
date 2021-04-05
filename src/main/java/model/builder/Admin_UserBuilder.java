package model.builder;

import model.Admin_User;
import model.Role;

import java.util.List;

public class Admin_UserBuilder {

    private Admin_User admin_user;

    public Admin_UserBuilder(){
        admin_user = new Admin_User();
    }

    public Admin_UserBuilder setId(Long id){
        admin_user.setId(id);
        return this;
    }

    public Admin_UserBuilder setUsername(String username){
        admin_user.setUsername(username);
        return this;
    }

    public Admin_UserBuilder setPassword(String password){
        admin_user.setPassword(password);
        return this;
    }

    public Admin_UserBuilder setRoles(List<Role> roles) {
        admin_user.setRoles(roles);
        return this;
    }

    public  Admin_UserBuilder getRoles(){
        admin_user.getRoles();
        return  this;
    }

    public Admin_User build(){
        return admin_user;
    }

}
