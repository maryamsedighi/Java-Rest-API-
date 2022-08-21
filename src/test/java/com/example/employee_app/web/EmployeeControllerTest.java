package com.example.employee_app.web;

import com.example.employee_app.data.model.Department;
import com.example.employee_app.data.model.Employee;
import com.example.employee_app.data.payloads.request.EmployeeRequest;
import com.example.employee_app.data.repository.EmployeeRepository;
import com.example.employee_app.service.EmployeeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("test all method in employee controller")
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    EmployeeRepository employeeRepository;

    //created some instances for testing purposes
    Employee employee1 = new Employee(1, "mary", "sedighi", "34253",
            "mary@gmail.com", 40000, Department.MARKETING);
    Employee employee2 = new Employee(2, "melina", "khodaie", "88798",
            "melina@gmail.com", 30000, Department.ENGINEERING);

    @DisplayName("test getAllEmployees method")
    @Test
    void getAllEmployees() throws Exception {

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        List<Employee> allEmployee = employeeServiceImpl.getAllEmployee();

        // Assert expected results
//        assertEquals(allEmployee.size(), 2);

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

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/employee/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

    }

    @DisplayName("test getASingleEmployee method")
    @Test
    void getEmployeeById() throws Exception {

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

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/employee/find/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("mary"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("sedighi"));

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
                .department(Department.MARKETING)
                .salary(50000)
                .build();

//         Assert expected results
        assertEquals(employeeServiceImpl.createEmployee(newEmployee).getMessage(),
                "New Employee created successfully");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/employee/add")
                        .content(asJsonString(new Employee(4, "shokoufe", "sedighi", "87879",
                                "sss@gmail.com", 60000, Department.HUMAN_RESOURCE)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("New Employee created successfully"));

    }

    @DisplayName("test updateEmployee method")
    @Test
    void updateEmployee() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/employee/update/{id}", 1)
                        .content(asJsonString(new Employee(1, "mary", "sedighi", "34253",
                                "mary@gmail.com", 50000, Department.HUMAN_RESOURCE)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The Employee updated successfully"));

    }

    @DisplayName("test deleteEmployee method")
    @Test
    void deleteEmployee() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/delete/{id}", 1))
                .andExpect(status().isAccepted());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}