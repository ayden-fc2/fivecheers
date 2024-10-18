package com.fc.fcserver.entity;

import lombok.Data;

@Data
public class V2LogSt {
    private int log_id;
    private String log_op;
    private String log_uuid ;
    private String log_ip_address;
    private java.sql.Timestamp log_time;
}
