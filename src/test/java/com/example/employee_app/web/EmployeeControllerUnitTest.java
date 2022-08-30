package com.example.employee_app.web;

import com.example.employee_app.data.model.Department;
import com.example.employee_app.data.model.Employee;
import com.example.employee_app.data.payloads.request.EmployeeRequest;
import com.example.employee_app.data.repository.EmployeeRepository;
import com.example.employee_app.service.EmployeeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("unit test all methods in employee controller")
@SpringBootTest
class EmployeeControllerUnitTest {

    @Autowired
    EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    EmployeeRepository employeeRepository;

    //created some instances for testing purposes
    Employee employee1 = new Employee(1, "mary", "sedighi", "34253",
            "mary@gmail.com", 40000, Department.MARKETING);
    Employee employee2 = new Employee(2, "melina", "khodaie", "88798",
            "melina@gmail.com", 30000, Department.ENGINEERING);

    @DisplayName("this test should check the size of employee list which is 2 and also check the values of each employee")
    @Test
    void getAllEmployees() {

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        List<Employee> allEmployee = employeeServiceImpl.getAllEmployee();

//        Assert expected results
        assertEquals(allEmployee.size(), 2);

        assertAll(() -> assertEquals(allEmployee.get(0).getId(), employee1.getId()),
                () -> assertEquals(allEmployee.get(0).getFirstName(), employee1.getFirstName()),
                () -> assertEquals(allEmployee.get(0).getLastname(), employee1.getLastname()),
                () -> assertEquals(allEmployee.get(0).getPhoneNumber(), employee1.getPhoneNumber()),
                () -> assertEquals(allEmployee.get(0).getEmail(), employee1.getEmail()),
                () -> assertEquals(allEmployee.get(0).getSalary(), employee1.getSalary()),
                () -> assertEquals(allEmployee.get(0).getDepartment(), employee1.getDepartment()));


        assertAll(() -> assertEquals(allEmployee.get(1).getId(), employee2.getId()),
                () -> assertEquals(allEmployee.get(1).getFirstName(), employee2.getFirstName()),
                () -> assertEquals(allEmployee.get(1).getLastname(), employee2.getLastname()),
                () -> assertEquals(allEmployee.get(1).getPhoneNumber(), employee2.getPhoneNumber()),
                () -> assertEquals(allEmployee.get(1).getEmail(), employee2.getEmail()),
                () -> assertEquals(allEmployee.get(1).getSalary(), employee2.getSalary()),
                () -> assertEquals(allEmployee.get(1).getDepartment(), employee2.getDepartment()));

    }

    @DisplayName("this test should check the value of the first employee")
    @Test
    void getEmployeeById() {

        Employee aSingleEmployee = employeeServiceImpl.getASingleEmployee(1);

        // Assert expected results
        assertAll(() -> assertEquals(1, aSingleEmployee.getId()),
                () -> assertEquals("mary", aSingleEmployee.getFirstName()),
                () -> assertEquals("sedighi", aSingleEmployee.getLastname()),
                () -> assertEquals("34253", aSingleEmployee.getPhoneNumber()),
                () -> assertEquals("mary@gmail.com", aSingleEmployee.getEmail()),
                () -> assertEquals(40000, aSingleEmployee.getSalary()),
                () -> assertEquals(Department.MARKETING, aSingleEmployee.getDepartment())
        );

    }

    @DisplayName("This test should check the values of new employee which have been added")
    @Test
    void addEmployee() {

        EmployeeRequest newEmployee = EmployeeRequest.builder()
                .id(3)
                .firstName("bahar")
                .lastname("sedighi")
                .phoneNumber("45675")
                .email("bahar@gmail.com")
                .department(Department.MARKETING)
                .salary(50000)
                .build();

//         Assert expected results
        assertEquals(employeeServiceImpl.createEmployee(newEmployee).getMessage(),
                "New Employee created successfully");

    }

    @DisplayName("This test should check the values of the first employee which have been updated")
    @Test
    void updateEmployee() {

        Employee existedEmployee = new Employee(1, "mary", "sedighi", "12345",
                "mary@gmail.com", 50000, Department.HUMAN_RESOURCE);

        employeeRepository.save(existedEmployee);

        Employee aSingleEmployee = employeeServiceImpl.getASingleEmployee(1);

        // Assert expected results
        assertAll(() -> assertEquals(1, aSingleEmployee.getId()),
                () -> assertEquals("12345", aSingleEmployee.getPhoneNumber()),
                () -> assertEquals(50000, aSingleEmployee.getSalary()),
                () -> assertEquals(Department.HUMAN_RESOURCE, aSingleEmployee.getDepartment())
        );

    }

    @DisplayName("This test should check the employee list size after deleting an employee")
    @Test
    void deleteEmployee() {

        employeeRepository.deleteById(3);
        List<Employee> allEmployee = employeeServiceImpl.getAllEmployee();

        // Assert expected results
        assertEquals(allEmployee.size(), 2);
    }

}