package com.w.sportmanager.map;

import com.w.sportmanager.pojo.SportSign;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SportSignMapper {

    @Insert("Insert Into `sport_sign` " +
            "Values(" +
            "#{signId}," +
            "#{id}," +
            "#{date}," +
            "#{beginMachine}," +
            "#{beginTime}," +
            "#{finishMachine}," +
            "#{finishTime}," +
            "#{status} )")
    void insertSportSign(SportSign sportSign);

    @Select("Select * " +
            "From `sport_sign` " +
            "Where id = #{id} and date = #{date}")
    List<SportSign> getSportSignByIdAndDate(String id, String date);

    @Update("Update `sport_sign` " +
            "SET " +
            "beginMachine = #{beginMachine}, " +
            "beginTime = #{beginTime}, " +
            "finishMachine = #{finishMachine}, " +
            "finishTime = #{finishTime}, " +
            "status = #{status} " +
            "Where id = #{id} and date = #{date}")
    void updateSportSignByIdAndDate(SportSign sportSign);
}
