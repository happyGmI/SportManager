package com.w.sportmanager.map;

import com.w.sportmanager.pojo.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Insert("Insert Into `student` Values (#{id}, #{email}, #{password}, #{name}, #{avatar}, #{nickName}, #{birthdate}, #{gender}, #{level})")
    Boolean insertStudent(Student student);

    @Select("Select * From `student` Where id = #{id}")
    List<Student> getStudentsById(String id);

    @Select("Select * From `student` Where email = #{email}")
    List<Student> getStudentsByEmail(String email);
}
