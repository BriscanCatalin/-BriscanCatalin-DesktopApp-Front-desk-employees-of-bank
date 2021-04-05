package model;

import java.util.List;

public class Employee_User {

    private Long id;
    private String username;
    private String password;
    private List<Role> roles;
    private String report;

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
