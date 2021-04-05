package Authenticate;

import database.DBConnectionFactory;

import model.Employee_User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import repository.employee.EmployeeRepository;
import repository.employee.EmployeeRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import service.employee.AuthenticationEmployeeService;
import service.employee.AuthenticationEmployeeServiceMySQL;

import java.sql.Connection;

public class AuthenticationEmployeeServiceTest {


    public static final String TEST_USERNAME = "tes23tt@userdafme.com";
    public static final String TEST_PASSWORD = "TestPaseqesword1@";
    private static AuthenticationEmployeeService authenticationEmployeeService;
    private static EmployeeRepository employeeRepository;

    @BeforeClass
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectinWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        employeeRepository = new EmployeeRepositoryMySQL(connection, rightsRolesRepository);

        authenticationEmployeeService = new AuthenticationEmployeeServiceMySQL(
                employeeRepository,
                rightsRolesRepository
        );
    }

    @Before
    public void cleanUp() {
        employeeRepository.removeAll();
    }


    @Test
    public void register() throws Exception {
        Assert.assertTrue(
                authenticationEmployeeService.register(TEST_USERNAME, TEST_PASSWORD).getResult()
        );
    }

    @Test
    public void login() throws Exception {
        authenticationEmployeeService.register(TEST_USERNAME, TEST_PASSWORD).getResult();
        Employee_User employee_user = authenticationEmployeeService.login(TEST_USERNAME, TEST_PASSWORD).getResult();
        Assert.assertNotNull(employee_user);
    }

    @Test
    public void logout() throws Exception {

    }


}
