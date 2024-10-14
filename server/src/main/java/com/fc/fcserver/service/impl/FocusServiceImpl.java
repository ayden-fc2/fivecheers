package com.fc.fcserver.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fc.fcserver.dao.FocusMapper;
import com.fc.fcserver.entity.V2FocusSt;
import com.fc.fcserver.service.FocusService;

@Service
public class FocusServiceImpl implements FocusService {

    @Autowired FocusMapper fm;

    @Override
    public List<V2FocusSt> getAllFocus(String userName, Date date) {
        return fm.getAllFocus(userName, date);
    }

    @Override
    public boolean updateFocus(V2FocusSt v2FocusSt) {
        return fm.updateFocus(v2FocusSt);
    }

    @Override
    public boolean addFocus(V2FocusSt v2FocusSt) {
        return fm.addFocus(v2FocusSt);
    }

    @Override
    public boolean deleteFocus(int focusId) {
        return fm.deleteFocus(focusId);
    }
    
}
