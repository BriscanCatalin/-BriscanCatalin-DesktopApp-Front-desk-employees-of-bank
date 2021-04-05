package repository.admin;

import model.Admin_User;
import model.builder.Admin_UserBuilder;
import model.validation.Notification;
import repository.AuthenticationException;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.List;

import static database.Constants.Tables.ADMIN_USER;

public class AdminRepositoryMySQL implements AdminRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public AdminRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<Admin_User> findAll() {
        return null;
    }

    @Override
    public Notification<Admin_User> findByUsernameAndPassword(String username, String password) throws AuthenticationException {
        Notification<Admin_User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchAdminSql = "Select * from `" + ADMIN_USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet adminResultSet = statement.executeQuery(fetchAdminSql);
            if (adminResultSet.next()) {
                Admin_User admin_user = new Admin_UserBuilder()
                        .setUsername(adminResultSet.getString("username"))
                        .setPassword(adminResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForAdmin(adminResultSet.getLong("id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(admin_user);
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
    public boolean save(Admin_User admin_user) {
        try {
            PreparedStatement insertAdminStatement = connection
                    .prepareStatement("INSERT INTO admin_user values (null, ?, ?)");
            insertAdminStatement.setString(1, admin_user.getUsername());
            insertAdminStatement.setString(2, admin_user.getPassword());
            insertAdminStatement.executeUpdate();

            ResultSet rs = insertAdminStatement.getGeneratedKeys();
            rs.next();
            long adminId = rs.getLong(1);
            admin_user.setId(adminId);

            rightsRolesRepository.addRolesToAdmin_User(admin_user, admin_user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from admin_user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}