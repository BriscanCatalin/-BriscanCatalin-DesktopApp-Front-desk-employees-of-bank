import controller.LoginControllerAdmin;
import controller.LoginControllerEmployee;
import view.LoginViewAdmin;
import view.LoginViewEmployee;

public class Luncher {

    public static void main(String[] args) {

        ComponentFactory componentFactory = ComponentFactory.instance(false);
        new LoginControllerAdmin(new LoginViewAdmin(),
                componentFactory.getAuthenticationAdminService(),
                componentFactory.getAdminOperationInterfaceService());
        new LoginControllerEmployee(new LoginViewEmployee(),
                componentFactory.getAuthenticationEmployeeService(),
                componentFactory.getEmployeeOperationInterfaceService());

    }

}
