package com.fc.fcserver.controller;

import com.fc.fcserver.service.LogService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.fc.fcserver.entity.V2LogSt;

@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    LogService ls;

    @PostMapping("/postNewLog")
    public boolean postNewLog(@Param("logOp") String logOp,@Param("logUUID") String logUUID, @Param("logIpAddress") String logIpAddress){
        try{
            return ls.postNewLog(logOp,logUUID,logIpAddress);
        }catch (Exception e){
            return false;
        }
    }

    @GetMapping("/getLogsByPage")
    public List<V2LogSt> getLogsByPage(@Param("page") int page, @Param("pageSize") int pageSize){
        return ls.getLogsByPage(page,pageSize);
    }

    @GetMapping("/getLogsByUUID")
    public List<V2LogSt> getLogsByUUID(@Param("logUUID") String logUUID){
        return ls.getLogsByUUID(logUUID);
    }

}
