package com.fc.fcserver.service;

import java.util.List;

import com.fc.fcserver.entity.V2LogSt;

public interface LogService  {
    boolean postNewLog(String logOp, String logUUID, String logIpAddress);

    List<V2LogSt> getLogsByPage(int page, int pageSize);

    List<V2LogSt> getLogsByUUID(String logUUID);
}
