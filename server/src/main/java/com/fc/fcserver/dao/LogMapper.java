package com.fc.fcserver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.fc.fcserver.entity.V2LogSt;

@Mapper
public interface LogMapper {

    @Insert("INSERT INTO v2_log_st (log_op, log_uuid, log_ip_address) VALUES (#{logOp}, #{logUUID}, #{logIpAddress})")
    boolean insertNewPost(String logOp, String logUUID, String logIpAddress);

    @Select("SELECT * FROM v2_log_st ORDER BY log_time DESC LIMIT #{page}, #{pageSize}")
    List<V2LogSt> getLogsByPage(int page, int pageSize);

    @Select("SELECT * FROM v2_log_st WHERE log_uuid = #{log_uuid} ORDER BY log_time DESC")
    List<V2LogSt> getLogsByUUID(String logUUID);
}
