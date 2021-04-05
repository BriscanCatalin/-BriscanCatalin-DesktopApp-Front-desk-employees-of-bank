package service.employee;

import model.Account;
import view.EmployeeViewInterface;

public interface EmployeeOperationInterfaceService {

    void addClientInformation(EmployeeViewInterface employeeViewInterface);

    void updateClientInformationByName(String name, EmployeeViewInterface employeeViewInterface);

    void viewClientInformationByName(String name, EmployeeViewInterface employeeViewInterface);

    void createAccount(EmployeeViewInterface employeeViewInterface);

    Account updateAccount(Long id,  EmployeeViewInterface employeeViewInterface);

    boolean deleteAccount(Long id, EmployeeViewInterface employeeViewInterface);

    void viewAccount(Long id,  EmployeeViewInterface employeeViewInterface);

    boolean transferMoney(Integer id1, Integer id2, int money, EmployeeViewInterface employeeViewInterface);

    void processBills(String name, EmployeeViewInterface employeeViewInterface);

}
