package com.w.sportmanager;

import com.w.sportmanager.map.SportSignMapper;
import com.w.sportmanager.map.StudentMapper;
import com.w.sportmanager.pojo.SportSign;
import com.w.sportmanager.pojo.Student;
import com.w.sportmanager.service.SportSignService;
import com.w.sportmanager.util.TokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest
class SportManagerApplicationTests {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    SportSignMapper sportSignMapper;

    @Autowired
    SportSignService sportSignService;
    @Test
    void contextLoads() {

        // test student
        Student student = new Student("19030500308", "193@qq.com", "123456", "吴越");
//        studentMapper.insertStudent(student);
        System.out.println(studentMapper.getStudentsById("19030500103"));
        System.out.println(TokenUtil.sign(student));

    }

    @Test
    void testSportSignMapper() {
//        SportSign sportSign = new SportSign();
//        sportSign.setId("19030500103");
//        sportSign.setBeginMachine("0");
//        sportSignMapper.insertSportSign(sportSign);
        List<SportSign> sportSignByIdAndDate = sportSignMapper.getSportSignByIdAndDate("19030500103", "2022-05-25");
        System.out.println(sportSignByIdAndDate);
        sportSignByIdAndDate.get(0).setFinishMachine("0");
        sportSignByIdAndDate.get(0).setFinishTime(new Timestamp(System.currentTimeMillis()));

        sportSignMapper.updateSportSignByIdAndDate(sportSignByIdAndDate.get(0));
    }

    @Test
    void testSportSignService() {
        try {
            System.out.println(sportSignService.checkInSportSign("19030500103"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
