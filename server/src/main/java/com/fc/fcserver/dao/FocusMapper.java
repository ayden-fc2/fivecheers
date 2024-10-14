package com.fc.fcserver.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fc.fcserver.entity.V2FocusSt;

@Mapper
public interface FocusMapper {

    @Select("SELECT * FROM v2_focus_st WHERE focusUser = #{userName} AND DATE(focusDate) = DATE(#{date})")
    List<V2FocusSt> getAllFocus(String userName, Date date);

    @Update("UPDATE v2_focus_st SET focusName = #{focusName}, focusDetail = #{focusDetail}, focusLength = #{focusLength} WHERE focusId = #{focusId}")
    boolean updateFocus(V2FocusSt v2FocusSt);

    @Insert("INSERT INTO v2_focus_st(focusName, focusDetail, focusDate, focusUser, focusLength) VALUES(#{focusName}, #{focusDetail}, #{focusDate}, #{focusUser}, #{focusLength})")
    boolean addFocus(V2FocusSt v2FocusSt);

    @Delete("DELETE FROM v2_focus_st WHERE focusId = #{focusId}")
    boolean deleteFocus(int focusId);
    
}
