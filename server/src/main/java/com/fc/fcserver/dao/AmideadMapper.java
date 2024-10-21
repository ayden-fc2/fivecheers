package com.fc.fcserver.dao;

import com.fc.fcserver.entity.V2AmideadSt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AmideadMapper {

    @Select("SELECT dead_id AS deadId, am_i_dead AS amIDead, time AS time, last_word AS lastWord, map_url AS mapUrl, gift_still AS giftStill From v2_amidead_st")
    V2AmideadSt getAmidead();

    @Update("UPDATE v2_amidead_st SET time = CURRENT_TIMESTAMP, gift_still = 1")
    boolean updateTime();

    @Update("UPDATE v2_amidead_st SET gift_still = 0")
    boolean updateGiftStill();

    @Select("SELECT am_i_dead FROM v2_amidead_st")
    int getRightSecret();

    @Select("SELECT gift_url FROM v2_amidead_st")
    String getGiftUrl();

    @Select("SELECT am_i_dead AS amIDead, last_word AS lastWord, map_url AS mapUrl, gift_Url AS giftUrl From v2_amidead_st")
    V2AmideadSt getAmideadAll();

    @Update("UPDATE v2_amidead_st SET am_i_dead = #{amIDead}, last_word = #{lastWord}, map_url = #{mapUrl}, gift_url = #{giftUrl}")
    boolean updateAmideadAll(V2AmideadSt amideadSt);

}
