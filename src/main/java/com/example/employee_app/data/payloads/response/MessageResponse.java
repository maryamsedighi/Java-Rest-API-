package com.example.employee_app.data.payloads.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageResponse {
    private String message;
    private String date;

    public MessageResponse(String message){
        this.message = message;
        this.date=new Date().toString();
    }
}
