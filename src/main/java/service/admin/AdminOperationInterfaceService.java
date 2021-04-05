package service.admin;

import model.validation.Notification;
import view.AdminViewInterface;

public interface AdminOperationInterfaceService {

    Notification<Boolean> createEmployee(AdminViewInterface adminViewInterface);

    void retrieveEmployeeByName(String name,AdminViewInterface adminViewInterface);

    Notification<Boolean> updateEmployeeByName(String name, AdminViewInterface adminViewInterface);

    boolean deleteEmployee(String name, AdminViewInterface adminViewInterface);

    void generateReports(String name , AdminViewInterface adminViewInterface);


}
