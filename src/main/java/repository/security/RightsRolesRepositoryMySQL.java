package repository.security;

import model.Admin_User;
import model.Employee_User;
import model.Right;
import model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.*;

public class RightsRolesRepositoryMySQL implements RightsRolesRepository{

    private final Connection connection;

    public RightsRolesRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public void addRole(String role) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO " + ROLE + " values (null, ?)");
            insertStatement.setString(1, role);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addRight(String right) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO `" + RIGHT + "` values (null, ?)");
            insertStatement.setString(1, right);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Role findRoleByTitle(String role) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ROLE + " where `role`=\'" + role + "\'";
            ResultSet roleResultSet = statement.executeQuery(fetchRoleSql);
            roleResultSet.next();
            Long roleId = roleResultSet.getLong("id");
            String roleTitle = roleResultSet.getString("role");
            return new Role(roleId, roleTitle, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Role findRoleById(Long roleId) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ROLE + " where `id`=\'" + roleId + "\'";
            ResultSet roleResultSet = statement.executeQuery(fetchRoleSql);
            roleResultSet.next();
            String roleTitle = roleResultSet.getString("role");
            return new Role(roleId, roleTitle, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Right findRightByTitle(String right) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from `" + RIGHT + "` where `right`=\'" + right + "\'";
            ResultSet rightResultSet = statement.executeQuery(fetchRoleSql);
            rightResultSet.next();
            Long rightId = rightResultSet.getLong("id");
            String rightTitle = rightResultSet.getString("right");
            return new Right(rightId, rightTitle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;    }

    @Override
    public void addRolesToAdmin_User(Admin_User admin_user, List<Role> roles) {
        try {
            for (Role role : roles){
                PreparedStatement insertAdminRoleStatement = connection
                        .prepareStatement("INSERT INTO  `admin_role` VALUES (null , ?, ? )");
                insertAdminRoleStatement.setLong(1, admin_user.getId());
                insertAdminRoleStatement.setLong(2, role.getId());
                insertAdminRoleStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addRolesToEmployee_User(Employee_User employee_user, List<Role> roles) {
        try {
            for (Role role : roles){
                PreparedStatement insertEmployeeRoleStatement = connection
                        .prepareStatement("INSERT INTO `employee_role` VALUES (null , ? ,?)");
                insertEmployeeRoleStatement.setLong(1,employee_user.getId());
                insertEmployeeRoleStatement.setLong(2,role.getId());
                insertEmployeeRoleStatement.executeUpdate();
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Role> findRolesForAdmin(Long adminId) {
        try {
            List<Role> roles = new ArrayList<>();
            Statement statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ADMIN_ROLE + " where `admin_id`=\'" + adminId + "\'";
            ResultSet adminRoleResultSet = statement.executeQuery(fetchRoleSql);
            while (adminRoleResultSet.next()) {
                long roleId = adminRoleResultSet.getLong("role_id");
                roles.add(findRoleById(roleId));
            }
            return roles;
        } catch (SQLException e) {}

        return null;//trebuie sa separ in doua any - employee , admin => 2 metode
                    //in loc de USER_ROLE - > ADMIN_ROLE, EMPLOYEE_ROLE
    }

    @Override
    public List<Role> findRolesForEmployee(Long employeeId) {
        try {
            List<Role> roles = new ArrayList<>();
            Statement statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + EMPLOYEE_ROLE + " where `employee_id`=\'" + employeeId + "\'";
            ResultSet employeeRoleResultSet = statement.executeQuery(fetchRoleSql);
            while (employeeRoleResultSet.next()) {
                long roleId = employeeRoleResultSet.getLong("role_id");
                roles.add(findRoleById(roleId));
            }
            return roles;
        } catch (SQLException e) {}

        return null;
    }

    @Override
    public void addRoleRight(Long roleId, Long rightId) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO " + ROLE_RIGHT + " values (null, ?, ?)");
            insertStatement.setLong(1, roleId);
            insertStatement.setLong(2, rightId);
            insertStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }
}
