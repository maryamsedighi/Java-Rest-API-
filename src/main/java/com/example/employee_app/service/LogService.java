package com.example.employee_app.service;

import com.example.employee_app.data.model.Log;
import org.springframework.stereotype.Component;

@Component
public interface LogService {
    void save(Log log);

}
