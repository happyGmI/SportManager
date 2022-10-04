package com.w.sportmanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.w.sportmanager.map.StudentMapper;
import com.w.sportmanager.pojo.SportSignHistory;
import com.w.sportmanager.pojo.Student;
import com.w.sportmanager.service.SportSignService;
import com.w.sportmanager.service.StudentService;
import com.w.sportmanager.util.JSONResult;
import com.w.sportmanager.util.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class IndexController {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    StudentService studentService;
    @Autowired
    SportSignService sportSignService;

    @PostMapping("/api/tokens")
    public JSONResult<Map<String, Object>> logIn(@RequestBody JSONObject loginMessage) {
        Map<String, Object> result;
        try {
            result = studentService.loginService(loginMessage);
        } catch (Exception e) {
            return new JSONResult<>(201, e.getMessage());
        }
        return new JSONResult<>(200, "successfully", result);
    }

    @GetMapping("/api/users/{userId}")
    public JSONResult<Student> getUserInfo(@PathVariable("userId") String id) {
        List<Student> students = studentMapper.getStudentsById(id);
        if (students.isEmpty()) {
            students = studentMapper.getStudentsById(UserContextHolder.getCurrentUserId());
        }
        if (students.isEmpty()) {
            return new JSONResult<>(401, "用户不存在，确认用户是否已经注销");
        }
        return new JSONResult<>(200, "successfully", students.get(0));
    }

    @RequestMapping("/upload")
    public JSONResult<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) {

        String id = UserContextHolder.getCurrentUserId();
        if (file.isEmpty()) {
            return new JSONResult<>(201, "upload fail");
        }
        System.out.println("get");
        String fileName = id + "_" + System.currentTimeMillis() + ".jpg";
        String filePath = "D:\\tmp-photo\\" + fileName;
        File dest = new File(filePath);
        try {
            file.transferTo(dest);
            log.info("上传成功");
            HashMap<String, Object> result = new HashMap<>();
            result.put("url", "http://localhost:8090/dataResourceImages/" + fileName);
            return new JSONResult<>(200, "upload successfully", result);
        } catch (IOException e) {
            log.error(e.toString(), e);
            return new JSONResult<>(201, "upload fail");
        }
    }

    @PostMapping("/api/face/checkIn/{id}")
    public JSONResult<JSONObject> faceCheckIn(@PathVariable("id") String id) {
        String[] idAndTime = id.split("_");
        id = idAndTime[0];
        JSONObject result;
        try {
            result = sportSignService.checkInSportSign(id);
        } catch (Exception e) {
            return new JSONResult<>(201, e.getMessage());
        }
        return new JSONResult<>(200, "check in successfully", result);
    }

    @GetMapping("/api/sportsign/history")
    public JSONResult<SportSignHistory> sportSignHistory() {
        SportSignHistory history = new SportSignHistory();
        try {
            history = sportSignService.checkSportSignHistory(UserContextHolder.getCurrentUserId());
        } catch (Exception e) {
            return new JSONResult<>(201, e.getMessage());
        }
        return new JSONResult<>(200, "successfully", history);
    }

}
