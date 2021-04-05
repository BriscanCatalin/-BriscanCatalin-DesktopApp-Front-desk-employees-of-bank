package service.admin;

import model.Admin_User;
import model.validation.Notification;
import repository.AuthenticationException;

public interface AuthenticationAdminService {

    Notification<Boolean> register(String username, String password);

    Notification<Admin_User> login(String username, String password) throws AuthenticationException;

    boolean logout(Admin_User user);

}
