package com.fc.fcserver.service.impl;

import com.fc.fcserver.dao.LogMapper;
import com.fc.fcserver.entity.V2LogSt;
import com.fc.fcserver.service.LogService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper lm;

    @Override
    public boolean postNewLog(String logOp, String logUUID, String logIpAddress) {
        return lm.insertNewPost(logOp, logUUID, logIpAddress);
    }

    @Override
    public List<V2LogSt> getLogsByPage(int page, int pageSize) {
        return lm.getLogsByPage(page, pageSize);
    }

    @Override
    public List<V2LogSt> getLogsByUUID(String logUUID) {
        return lm.getLogsByUUID(logUUID);
    }
}
