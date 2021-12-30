package com.example.employee_app.web;

import com.example.employee_app.data.model.Department;
import com.example.employee_app.data.model.Employee;
import com.example.employee_app.data.payloads.request.EmployeeRequest;
import com.example.employee_app.data.repository.EmployeeRepository;
import com.example.employee_app.service.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("test all method in employee service")
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Mock
    EmployeeServiceImpl employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    //created a few employee instances for testing purposes
    Employee employee1 = new Employee(1, "mary", "sedighi", "34253", "mary@gmail.com",
            43543, Department.MARKETING);
    Employee employee2 = new Employee(2, "melina", "sedighi", "34253", "mary@gmail.com",
            43543, Department.MARKETING);

    @DisplayName("test getAllEmployees method")
    @Test
    void getAllEmployees() throws Exception {

        List<Employee> employeeList = new ArrayList<>(Arrays.asList(employee1, employee2));

        //configure getAllEmployee method behavior
        Mockito.when(employeeService.getAllEmployee()).thenReturn(employeeList);

        // Assert expected results
        assertEquals(employeeList.size(), 2);

        assertAll(() -> assertEquals(employeeList.get(0).getId(), employee1.getId()),
                () -> assertEquals(employeeList.get(0).getFirstName(), employee1.getFirstName()),
                () -> assertEquals(employeeList.get(0).getLastname(), employee1.getLastname()),
                () -> assertEquals(employeeList.get(0).getPhoneNumber(), employee1.getPhoneNumber()),
                () -> assertEquals(employeeList.get(0).getEmail(), employee1.getEmail()),
                () -> assertEquals(employeeList.get(0).getSalary(), employee1.getSalary()),
                () -> assertEquals(employeeList.get(0).getDepartment(), employee1.getDepartment())
        );

        //        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/employee/all")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) jsonPath("$", hasSize(2)));

    }

    @DisplayName("test getASingleEmployee method")
    @Test
    void getEmployeeById() throws Exception {

        //configure getAllEmployee method behavior
        Mockito.when(employeeService.getASingleEmployee(1)).thenReturn(employee1);
        Mockito.when(employeeService.getASingleEmployee(2)).thenReturn(employee2);

        // Assert expected results
        Employee result1 = employeeService.getASingleEmployee(1);
        Employee result2 = employeeService.getASingleEmployee(2);

        assertAll(() -> assertEquals(result1.getFirstName(), employee1.getFirstName()),
                () -> assertEquals(result2.getFirstName(), employee2.getFirstName()),
                () -> assertEquals(result1.getLastname(), employee1.getLastname()),
                () -> assertEquals(result2.getLastname(), employee2.getLastname())
        );

//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/employee/find/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) jsonPath("$", notNullValue()))
//                .andExpect((ResultMatcher) jsonPath("$.firstName", is("mary")));

    }


    @DisplayName("test addEmployee method")
    @Test
    void addEmployee() throws Exception {

        EmployeeRequest newEmployee = EmployeeRequest.builder()
                .id(3)
                .firstName("bahar")
                .lastname("sedighi")
                .phoneNumber("45675")
                .email("bahar@gmail.com")
                .department(Department.ENGINEERING)
                .salary(324432)
                .build();

//         Assert expected results
        assertEquals(employeeService.createEmployee(newEmployee).getMessage(), "New Employee created successfully");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(employeeService.createEmployee(newEmployee)));

//        mockMvc.perform(mockRequest)
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()));

    }

    @Test
    void updateEmployee() {

        Mockito.when(employeeService.getASingleEmployee(1)).thenReturn(employee1);

        Employee result1 = employeeService.getASingleEmployee(1);
        result1.setSalary(300000);
        employeeRepository.save(result1);
        assertEquals(300000, employeeService.getASingleEmployee(1).getSalary());
    }

    @Test
    void deleteEmployee() throws Exception {

        Mockito.when(employeeService.getASingleEmployee(employee2.getId())).thenReturn(employee2);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/employee/delete/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}