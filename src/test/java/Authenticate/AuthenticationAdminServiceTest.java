package Authenticate;

import database.DBConnectionFactory;
import model.Admin_User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.admin.AdminRepository;
import repository.admin.AdminRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import service.admin.AuthenticationAdminService;
import service.admin.AuthenticationAdminServiceMySQL;


import java.sql.Connection;

public class AuthenticationAdminServiceTest {


    public static final String TEST_USERNAME = "test@username.com";
    public static final String TEST_PASSWORD = "TestPassword1@";
    private static AuthenticationAdminService authenticationAdminService;
    private static AdminRepository adminRepository;

    @BeforeClass
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectinWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        adminRepository = new AdminRepositoryMySQL(connection, rightsRolesRepository);

        authenticationAdminService = new AuthenticationAdminServiceMySQL(
                adminRepository,
                rightsRolesRepository
        );
    }

    @Before
    public void cleanUp() {
        adminRepository.removeAll();
    }


    @Test
    public void register() throws Exception {
        Assert.assertTrue(
                authenticationAdminService.register(TEST_USERNAME, TEST_PASSWORD).getResult()
        );
    }

    @Test
    public void login() throws Exception {
        authenticationAdminService.register(TEST_USERNAME, TEST_PASSWORD).getResult();
        Admin_User admin_user = authenticationAdminService.login(TEST_USERNAME, TEST_PASSWORD).getResult();
        Assert.assertNotNull(admin_user);
    }

    @Test
    public void logout() throws Exception {

    }

}
