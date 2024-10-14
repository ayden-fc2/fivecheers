package com.fc.fcserver.service;

import java.sql.Date;
import java.util.List;

import com.fc.fcserver.entity.V2FocusSt;

public interface FocusService {

    List<V2FocusSt> getAllFocus(String userName, Date date);

    boolean updateFocus(V2FocusSt v2FocusSt);

    boolean addFocus(V2FocusSt v2FocusSt);

    boolean deleteFocus(int focusId);
    
}
