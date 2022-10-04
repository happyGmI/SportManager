package com.w.sportmanager.service;

import com.alibaba.fastjson.JSONObject;
import com.w.sportmanager.map.SportSignMapper;
import com.w.sportmanager.map.StudentMapper;
import com.w.sportmanager.pojo.SportSign;
import com.w.sportmanager.pojo.SportSignHistory;
import com.w.sportmanager.pojo.Student;
import com.w.sportmanager.repository.SportHistorySignRepository;
import com.w.sportmanager.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SportSignService {

    @Autowired
    SportSignMapper sportSignMapper;

    @Autowired
    SportHistorySignRepository sportHistorySignRepository;

    @Autowired
    StudentMapper studentMapper;

    public JSONObject checkInSportSign(String id) throws Exception {
        Date date = new Date(System.currentTimeMillis());
        List<SportSign> sportSigns = sportSignMapper.getSportSignByIdAndDate(id, DateUtil.selfDefineDateFormat(date));
        System.out.println(sportSigns);
        /**
         * 查询到今日已经有打卡记录存在
         */
        if (!sportSigns.isEmpty()) {
            SportSign sign = sportSigns.get(0);
            if (sign.getStatus() == 1)
                throw new Exception("今日已经完成打卡任务(1/1)，请勿重复打卡！");
            if (sign.getStatus() == 0) {
                Timestamp beginTime = sign.getBeginTime();
                Integer check = DateUtil.checkSportSignValidity(beginTime);
                if (check == -1) {
                    String beginTimeFormat = beginTime.toString().substring(0, 19);
                    long mintues = (System.currentTimeMillis() - beginTime.getTime()) / 1000 / 60;
                    String errorMsg = "打卡时间太短当前锻炼时长为：" + mintues + "/60 - 120(minutes)";
                    throw new Exception(errorMsg);
                } else if (check == 1) {
                    String beginTimeFormat = beginTime.toString().substring(0, 19);
                    long mintues = (System.currentTimeMillis() - beginTime.getTime()) / 1000 / 60;
                    String errorMsg = "打卡时间太长，已经为您重新计时，当前锻炼时长为：" + mintues + "/60 - 120(minutes)，当前锻炼开始时间为" + beginTimeFormat;
                    sign.setBeginTime(new Timestamp(System.currentTimeMillis()));
                    sign.setFinishTime(null);
                    try {
                        sportSignMapper.updateSportSignByIdAndDate(sign);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        throw new Exception("重新计时失败，请重试");
                    }
                    throw new Exception(errorMsg);
                } else if (check == 0) {
                    /**
                     * 打卡成功更新状态
                     */
                    long mintues = (System.currentTimeMillis() - beginTime.getTime()) / 1000 / 60;
                    sign.setFinishTime(new Timestamp(System.currentTimeMillis()));
                    sign.setFinishMachine("0");
                    sign.setStatus(1);
                    try {
                        sportSignMapper.updateSportSignByIdAndDate(sign);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        throw new Exception("打卡失败，请重试");
                    }
                    /**
                     * 打卡成功，返回学生信息以供核对
                     * 有这条记录就一定有这个学生，数据库的外键约束
                     */
                    Student student = studentMapper.getStudentsById(id).get(0);
                    JSONObject result = new JSONObject();
                    result.put("id", id);
                    result.put("name", student.getName());
                    result.put("sport_time", mintues);
                    /**
                     * 更新mongodb某个同学的签到打卡历史记录
                     * 考虑到一致性还有不能影响打卡提醒效果
                     */
                    try {
                        SportSignHistory sportSignHistory = sportHistorySignRepository.getSportSignHistoryById(id);
                        if (sportSignHistory == null) {
                            sportSignHistory = new SportSignHistory();
                            sportSignHistory.setId(id);
                            sportSignHistory.setSignTimes(1);
                            List<String> sportDate = new ArrayList<>();
                            sportDate.add(DateUtil.selfDefineDateFormat(date));
                            sportSignHistory.setSportDate(sportDate);
                            List<Integer> sportTime = new ArrayList<>();
                            sportTime.add((int) mintues);
                            sportSignHistory.setSportTime(sportTime);
                            sportHistorySignRepository.save(sportSignHistory);
                        } else {
                            sportSignHistory.setSignTimes(sportSignHistory.getSignTimes() + 1);
                            sportSignHistory.getSportDate().add(DateUtil.selfDefineDateFormat(date));
                            sportSignHistory.getSportTime().add((int) mintues);
                            sportHistorySignRepository.save(sportSignHistory);
                        }
                        /**
                         * 保证两个数据库一致性，如果写mongodb出现问题打卡记录有可能会丢失，为了协调数据库上的压力，每次打卡结束检查一次两个库之间的打卡次数是否一致，不一致则更新！
                         */
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return result;
                }
            }
        } else {
            /**
             * 检查到底是今天没打卡还是学生根本就不存在于数据库之中
             */
            List<Student> students = studentMapper.getStudentsById(id);
            if (students.isEmpty())
                throw new Exception("您的学号信息不存在，请联系管理员！或者使用学生邮箱进行注册！");
            SportSign sportSign = new SportSign();
            sportSign.setId(id);
            sportSign.setBeginMachine("0");
            try {
                sportSignMapper.insertSportSign(sportSign);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new Exception("打卡失败，请联系管理员！");
            }
            Student student = studentMapper.getStudentsById(id).get(0);
            JSONObject result = new JSONObject();
            result.put("id", id);
            result.put("name", student.getName());
            result.put("sport_time", 0);
            return result;
        }
        return new JSONObject();
    }

    public SportSignHistory checkSportSignHistory(String id) throws Exception {
        SportSignHistory history = sportHistorySignRepository.getSportSignHistoryById(id);
        if (history == null) {
            throw new Exception("当前用户未查询到打卡信息");
        }
        return history;
    }

}
