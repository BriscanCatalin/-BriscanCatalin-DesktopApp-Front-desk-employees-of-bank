package repository.security;

import model.Right;
import model.Role;
import model.Admin_User;
import model.Employee_User;

import java.util.List;
public interface RightsRolesRepository {

    void addRole(String role);

    void addRight(String right);

    Role findRoleByTitle(String role);

    Role findRoleById(Long roleId);

    Right findRightByTitle(String right);

    void addRolesToAdmin_User(Admin_User admin_user, List<Role> roles);

    void addRolesToEmployee_User(Employee_User employee_user, List<Role> roles);

    List<Role> findRolesForAdmin(Long adminId);

    List<Role> findRolesForEmployee(Long employeeId);

    void addRoleRight(Long roleId, Long rightId);
}
