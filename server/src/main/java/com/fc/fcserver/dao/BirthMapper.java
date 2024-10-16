package com.fc.fcserver.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.fc.fcserver.entity.V2BirthSt;

@Mapper
public interface BirthMapper {

    @Select("SELECT * FROM v2_birth_st")
    List<V2BirthSt> selectAll();

    @Insert ("INSERT INTO v2_birth_st (birthName, birthDate) VALUES (#{birthName}, #{birthDate})")
    boolean insert(V2BirthSt birth);

    @Delete("DELETE FROM v2_birth_st WHERE birthId = #{birthId}")
    boolean deleteByPrimaryKey(int birthId);

    
}
