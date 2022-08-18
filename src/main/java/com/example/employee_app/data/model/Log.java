package com.example.employee_app.data.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer code;//1 -> error 2-> info
    private Date dataTime;
    private String comment;
    private String ip;
    private String userName;
    private String action;

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", code=" + code +
                ", dataTime=" + dataTime +
                ", comment='" + comment + '\'' +
                ", ip='" + ip + '\'' +
                ", userName='" + userName + '\'' +
                ", action='" + action + '\'' +
                '}';
    }

}
