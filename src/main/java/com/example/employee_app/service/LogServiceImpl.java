package com.example.employee_app.service;

import com.example.employee_app.data.model.Log;
import com.example.employee_app.data.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogRepository logRepository;

    @Override
    public void save(Log log) {
        logRepository.save(log);
    }

}
