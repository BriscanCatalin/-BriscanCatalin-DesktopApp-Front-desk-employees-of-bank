package database;

import java.util.*;


import static database.Constants.Rights.*;
import static database.Constants.Roles.*;

public class Constants {

    public static class Schemas {
        public static final String TEST = "test_bank";
        public static final String PRODUCTION = "bank";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String ACCOUNT = "client_account";
        public static final String CLIENT_INFO = "client";
        public static final String ADMIN_USER = "admin_user";
        public static final String EMPLOYEE_USER = "employee_user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String BILL = "bill";
        public static final String ROLE_RIGHT = "role_right";
        public static final String ADMIN_ROLE = "admin_role";
        public static final String EMPLOYEE_ROLE = "employee_role";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{ADMIN_USER, EMPLOYEE_USER, ROLE, RIGHT,BILL, ROLE_RIGHT, ADMIN_ROLE, EMPLOYEE_ROLE, ACCOUNT, CLIENT_INFO};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class Rights {
        public static final String CREATE_CLIENT = "create_client";
        public static final String DELETE_CLIENT = "delete_client";
        public static final String UPDATE_CLIENT = "update_client";
        public static final String VIEW_CLIENT = "view_client";

        public static final String CREATE_ACCOUNT = "create_account";
        public static final String DELETE_ACCOUNT = "delete_account";
        public static final String UPDATE_ACCOUNT = "update_account";
        public static final String VIEW_ACCOUNT = "view_account";

        public static final String TRANSFER_MONEY = "transfer_money";
        public static final String PROCESS_BILLS = "process_bills";

        public static final String[] RIGHTS_EMPLOYEE = new String[]{CREATE_CLIENT, DELETE_CLIENT, UPDATE_CLIENT, VIEW_CLIENT, CREATE_ACCOUNT, DELETE_ACCOUNT, UPDATE_ACCOUNT, VIEW_ACCOUNT, TRANSFER_MONEY, PROCESS_BILLS };

        public static final String CREATE_EMPLOYEE = "create_employee";
        public static final String DELETE_EMPLOYEE = "delete_employee";
        public static final String UPDATE_EMPLOYEE = "update_employee";
        public static final String VIEW_EMPLOYEE = "view_employee";

        public static final String GENERATE_REPORT = "generate_report";

        public static final String[] RIGHTS_ADMIN = new String[]{CREATE_EMPLOYEE, DELETE_EMPLOYEE, UPDATE_EMPLOYEE, VIEW_EMPLOYEE, GENERATE_REPORT};

    }

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> rolesRights = new HashMap<>();
        for (String role : ROLES) {
            rolesRights.put(role, new ArrayList<>());
        }
        rolesRights.get(ADMINISTRATOR).addAll(Arrays.asList(RIGHTS_ADMIN));

        rolesRights.get(EMPLOYEE).addAll(Arrays.asList(RIGHTS_EMPLOYEE));

        return rolesRights;
    }


}
