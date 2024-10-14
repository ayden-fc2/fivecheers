package com.fc.fcserver.entity;

import lombok.Data;

@Data
public class V2FocusSt {
    private int focusId;
    private String focusName;
    private String focusDetail;
    private java.sql.Timestamp focusDate;
    private String focusUser;
    private int focusLength;
}
