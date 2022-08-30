package com.example.employee_app.web;

import com.example.employee_app.data.model.Department;
import com.example.employee_app.data.model.Employee;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("integration test all method in employee controller")
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllEmployees() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/employee/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

    }

    @Test
    void getEmployeeById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/employee/find/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("mary"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value("sedighi"));

    }

    @Test
    void addEmployee() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/employee/add")
                        .content(asJsonString(new Employee(4, "shokoufe", "sedighi", "87879",
                                "sss@gmail.com", 60000, Department.HUMAN_RESOURCE)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("New Employee created successfully"));

    }

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