package com.example.employee_app.service;

import com.example.employee_app.data.model.Employee;
import com.example.employee_app.data.payloads.request.EmployeeRequest;
import com.example.employee_app.data.payloads.response.MessageResponse;
import com.example.employee_app.data.repository.EmployeeRepository;
import com.example.employee_app.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public MessageResponse createEmployee(EmployeeRequest employeeRequest) {
        Employee newEmployee = new Employee();
        newEmployee.setFirstName(employeeRequest.getFirstName());
        newEmployee.setLastname(employeeRequest.getLastname());
        newEmployee.setPhoneNumber(employeeRequest.getPhoneNumber());
        newEmployee.setEmail(employeeRequest.getEmail());
        newEmployee.setSalary(employeeRequest.getSalary());
        newEmployee.setDepartment(employeeRequest.getDepartment());
        employeeRepository.save(newEmployee);
        return new MessageResponse("New Employee created successfully");

    }

    @Override
    public MessageResponse updateEmployee(Integer employeeId, EmployeeRequest employeeRequest) throws ResourceNotFoundException {
        Employee employee = employeeRepository.getById(employeeId);
        if (employee.toString().isEmpty()) {
            throw new ResourceNotFoundException(employee.getFirstName(), employee.getId().toString(), employeeId);
        } else
            employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastname(employeeRequest.getLastname());
        employee.setPhoneNumber(employeeRequest.getPhoneNumber());
        employee.setEmail(employeeRequest.getEmail());
        employee.setSalary(employeeRequest.getSalary());
        employee.setDepartment(employeeRequest.getDepartment());
        employeeRepository.save(employee);
        return new MessageResponse("The Employee updated successfully");
    }

    @Override
    public MessageResponse deleteEmployee(Integer employeeId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.getById(employeeId);
        if (employee.toString().isEmpty()) {
            throw new ResourceNotFoundException(employee.getFirstName(), employee.getId().toString(), employeeId);
        } else employeeRepository.deleteById(employeeId);
        return new MessageResponse("The employee deleted successfully");
    }

    @Override
    public Employee getASingleEmployee(Integer employeeId) throws ResourceNotFoundException {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee",
                employeeId.toString(), employeeId));
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

}
