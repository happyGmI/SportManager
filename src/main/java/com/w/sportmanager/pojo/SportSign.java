package com.w.sportmanager.pojo;

import com.w.sportmanager.util.DateUtil;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class SportSign {

    /**
     * 打卡编号
     */
    private String signId;

    /**
     * 打卡学生学号
     */
    private String id;

    /**
     * 打卡日期
     */
    private String date;

    /**
     *
     */
    private String beginMachine;

    /**
     * 打卡开始时间
     */
    private Timestamp beginTime;

    /**
     *
     */
    private String finishMachine;

    /**
     *
     */
    private Timestamp finishTime;

    /**
     * 打卡状态
     * 未完成打卡 0， 已完成打卡 1
     */
    private Integer status;

    public SportSign() {
        Date date = new Date(System.currentTimeMillis());
        this.beginTime = new Timestamp(System.currentTimeMillis());
        this.date = DateUtil.selfDefineDateFormat(date);
        this.status = 0;
    }
}
