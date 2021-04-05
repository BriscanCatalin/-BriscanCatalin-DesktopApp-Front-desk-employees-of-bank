package repository.admin;

import model.Admin_User;
import model.validation.Notification;
import repository.AuthenticationException;

import java.util.List;

public interface AdminRepository {

    List<Admin_User> findAll();

    Notification<Admin_User> findByUsernameAndPassword(String username, String password) throws AuthenticationException;

    boolean save(Admin_User admin);

    void removeAll();

}
