package com.example.employee_app.service;

import com.example.employee_app.data.model.Employee;
import com.example.employee_app.data.model.Log;
import com.example.employee_app.data.payloads.request.EmployeeRequest;
import com.example.employee_app.data.payloads.response.MessageResponse;
import com.example.employee_app.data.repository.EmployeeRepository;
import com.example.employee_app.exception.ResourceNotFoundException;
import com.example.employee_app.web.EmployeeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    LogService logService;

    @Autowired
    private HttpServletRequest request;

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
        logger.info("**M**" + "the employee added successfully");
        return new MessageResponse("New Employee created successfully");

    }

    @Override
    public MessageResponse updateEmployee(Integer employeeId, EmployeeRequest employeeRequest)
            throws ResourceNotFoundException {

        Employee employee = employeeRepository.getById(employeeId);
        if (employee.toString().isEmpty()) {
            throw new ResourceNotFoundException(employeeId);
        } else {

            employee.setFirstName(employeeRequest.getFirstName());
            employee.setLastname(employeeRequest.getLastname());
            employee.setPhoneNumber(employeeRequest.getPhoneNumber());
            employee.setEmail(employeeRequest.getEmail());
            employee.setSalary(employeeRequest.getSalary());
            employee.setDepartment(employeeRequest.getDepartment());
            employeeRepository.save(employee);

            logService.save(Log.builder().code(1).action("Employee.update").comment("The Employee updated successfully").
                    dataTime(new Date()).ip(request.getRemoteAddr()).build());

            return new MessageResponse("The Employee updated successfully");
        }
    }

    @Override
    public MessageResponse deleteEmployee(Integer employeeId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.getById(employeeId);

        if (employee.toString().isEmpty()) {
            logService.save(Log.builder().code(1).action("Employee.delete").comment("employee not exist").
                    dataTime(new Date()).ip(request.getRemoteAddr()).build());
            throw new ResourceNotFoundException(employeeId);
        } else {
            employeeRepository.deleteById(employeeId);

            logService.save(Log.builder().code(1).action("Employee.delete").comment("The employee deleted successfully").
                    dataTime(new Date()).ip(request.getRemoteAddr()).build());

            return new MessageResponse("The employee deleted successfully");
        }
    }

    @Override
    public Employee getASingleEmployee(Integer employeeId) throws ResourceNotFoundException {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

}
