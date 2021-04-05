package service.admin;

import model.Admin_User;
import model.Role;
import model.builder.Admin_UserBuilder;
import model.validation.Admin_UserValidator;
import model.validation.Notification;
import repository.AuthenticationException;
import repository.admin.AdminRepository;
import repository.security.RightsRolesRepository;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

import static database.Constants.Roles.ADMINISTRATOR;

public class AuthenticationAdminServiceMySQL implements AuthenticationAdminService {

    private final AdminRepository adminRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthenticationAdminServiceMySQL(AdminRepository adminRepository, RightsRolesRepository rightsRolesRepository) {
        this.adminRepository = adminRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> register(String username, String password) {
        Role adminRole = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR);
        Admin_User admin_user = new Admin_UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(adminRole))
                .build();

        Admin_UserValidator admin_userValidator = new Admin_UserValidator(admin_user);
        boolean adminValid = admin_userValidator.validate();
        Notification<Boolean> adminRegisterNotification = new Notification<>();

        if (!adminValid) {
            admin_userValidator.getErrors().forEach(adminRegisterNotification::addError);
            adminRegisterNotification.setResult(Boolean.FALSE);
        } else {
            admin_user.setPassword(encodePassword(password));
            adminRegisterNotification.setResult(adminRepository.save(admin_user));
        }
        return adminRegisterNotification;
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
    public Notification<Admin_User> login(String username, String password) throws AuthenticationException {
        return adminRepository.findByUsernameAndPassword(username, encodePassword(password));
    }

    @Override
    public boolean logout(Admin_User user) {
        return false;
    }
}
