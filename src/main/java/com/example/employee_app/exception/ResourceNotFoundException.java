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

    public ResourceNotFoundException(Integer employeeId) {
        super("The employee with Id: " + employeeId + " does not exist.");
        this.employeeId = employeeId;
    }
}
