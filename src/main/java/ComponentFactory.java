import database.DBConnectionFactory;
import database.JDBConnectionWrapper;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.admin.AdminRepository;
import repository.admin.AdminRepositoryMySQL;
import repository.client_info.Client_InfoRepository;
import repository.client_info.Client_InfoRepositoryMySQL;
import repository.employee.EmployeeRepository;
import repository.employee.EmployeeRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import service.admin.AdminOperationInterfaceService;
import service.admin.AdminOperationInterfaceServiceMySQL;
import service.admin.AuthenticationAdminService;
import service.admin.AuthenticationAdminServiceMySQL;
import service.employee.AuthenticationEmployeeService;
import service.employee.AuthenticationEmployeeServiceMySQL;
import service.employee.EmployeeOperationInterfaceService;
import service.employee.EmployeeOperationInterfaceServiceMySQL;

import java.sql.Connection;

public class ComponentFactory {

    private final AuthenticationAdminService authenticationAdminService;
    private final AuthenticationEmployeeService authenticationEmployeeService;

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final Client_InfoRepository client_infoRepository;
    private final AccountRepository accountRepository;

    private final RightsRolesRepository rightsRolesRepositoryAdmin;
    private final RightsRolesRepository rightsRolesRepositoryEmployee;

    private final EmployeeOperationInterfaceService employeeOperationInterfaceService;
    private final AdminOperationInterfaceService adminOperationInterfaceService;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectinWrapper(componentsForTests).getConnection();
        this.rightsRolesRepositoryAdmin = new RightsRolesRepositoryMySQL(connection);
        this.rightsRolesRepositoryEmployee = new RightsRolesRepositoryMySQL(connection);
        this.adminRepository = new AdminRepositoryMySQL(connection, rightsRolesRepositoryAdmin);
        this.employeeRepository = new EmployeeRepositoryMySQL(connection, rightsRolesRepositoryEmployee);
        this.client_infoRepository = new Client_InfoRepositoryMySQL( connection);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.authenticationAdminService = new AuthenticationAdminServiceMySQL(this.adminRepository, this.rightsRolesRepositoryAdmin);
        this.authenticationEmployeeService = new AuthenticationEmployeeServiceMySQL(this.employeeRepository, this.rightsRolesRepositoryEmployee);
        this.employeeOperationInterfaceService = new EmployeeOperationInterfaceServiceMySQL(this.employeeRepository, this.rightsRolesRepositoryEmployee, client_infoRepository,accountRepository,connection);
        this.adminOperationInterfaceService = new AdminOperationInterfaceServiceMySQL(this.employeeRepository, this.rightsRolesRepositoryAdmin , connection);
    }

    public AuthenticationAdminService getAuthenticationAdminService() {
        return authenticationAdminService;
    }

    public AuthenticationEmployeeService getAuthenticationEmployeeService() {
        return authenticationEmployeeService;
    }

    public EmployeeOperationInterfaceService getEmployeeOperationInterfaceService () {
        return employeeOperationInterfaceService;
    }

    public AdminOperationInterfaceService getAdminOperationInterfaceService() {
        return adminOperationInterfaceService;
    }

    public AdminRepository getAdminRepository() {
        return adminRepository;
    }

    public EmployeeRepository getEmployeeRepository() {
        return employeeRepository;
    }

    public RightsRolesRepository getRightsRolesRepositoryAdmin() {
        return rightsRolesRepositoryAdmin;
    }

    public RightsRolesRepository getRightsRolesRepositoryEmployee() {
        return rightsRolesRepositoryEmployee;
    }


}
