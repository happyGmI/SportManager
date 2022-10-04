package com.w.sportmanager.service;

import com.alibaba.fastjson.JSONObject;
import com.w.sportmanager.map.StudentMapper;
import com.w.sportmanager.pojo.Student;
import com.w.sportmanager.util.JSONResult;
import com.w.sportmanager.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    @Autowired
    StudentMapper studentMapper;

    public Map<String, Object> loginService(JSONObject loginMessage) throws Exception{
        /**
         * 从请求的body中读取id email password
         */
        String id = loginMessage.getString("id");
        String email = loginMessage.getString("email");
        String password = loginMessage.getString("password");

        List<Student> students = new ArrayList<>();
        /**
         * 检查参数是否丢失
         */
        if (id == null && email == null)
            throw new Exception("Account is empty!");
        if (password == null)
            throw new Exception("Password is empty!");
        /**
         * 获得students
         */
        if (id != null) {
            students = studentMapper.getStudentsById(id);
        } else {
            students = studentMapper.getStudentsByEmail(email);
        }
        /**
         * 检查账号是否存在
         */
        if (students.isEmpty())
            throw new Exception("Account is not exits, please register first!");
        /**
         * 检查密码是否正确
         */
        if (!students.get(0).getPassword().equals(password)) {
            System.out.println(students.get(0).getPassword().equals(password));
            throw new Exception("Password error!");
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", TokenUtil.sign(students.get(0)));
        resultMap.put("id", students.get(0).getId());

        return resultMap;
    }
}
