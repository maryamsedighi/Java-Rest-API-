package com.example.employee_app.exception;

public class ResourceNotFoundException extends RuntimeException {
    private String name;
    private Integer employeeId;

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceNotFoundException(String name, String Id, Integer employeeId) {
        super("the employee" + name + "with employeeId " + employeeId + "does not exist.");
        this.name = name;
        this.employeeId = employeeId;
    }
}
