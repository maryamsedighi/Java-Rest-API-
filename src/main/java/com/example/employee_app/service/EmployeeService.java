package com.example.employee_app.service;

import com.example.employee_app.data.model.Employee;
import com.example.employee_app.data.payloads.request.EmployeeRequest;
import com.example.employee_app.data.payloads.response.MessageResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EmployeeService {

    MessageResponse createEmployee(EmployeeRequest employeeRequest);

    MessageResponse updateEmployee(Integer employeeId, EmployeeRequest employeeRequest);

    MessageResponse deleteEmployee(Integer employeeId);

    Employee getASingleEmployee(Integer employeeId);

    List<Employee> getAllEmployee();
}