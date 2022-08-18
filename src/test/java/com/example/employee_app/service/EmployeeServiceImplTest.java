package com.example.employee_app.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeServiceImplTest {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createEmployee() {
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void deleteEmployee() {
    }

    @Test
    void getASingleEmployee() throws Exception {

//        mockMvc.perform(get("/find/1")).andDo(print()).andExpect(status().isOk());
        assertNotNull(employeeService.getASingleEmployee(1));
        assertNull(employeeService.getASingleEmployee(2));
    }

    @Test
    void getAllEmployee() {
    }
}