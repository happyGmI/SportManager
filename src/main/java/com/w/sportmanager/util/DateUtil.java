package com.w.sportmanager.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateUtil {

    private static Long ONE_HOUR_TIME = 3600000L;
    private static Long TWO_HOUR_TIME = 7200000L;

    public static Integer checkSportSignValidity(Timestamp beginTime) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (now.getTime() - beginTime.getTime() < ONE_HOUR_TIME)
            return -1;
        if (now.getTime() - beginTime.getTime() > TWO_HOUR_TIME)
            return 1;
        return 0;
    }

    public static String selfDefineDateFormat(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static void main(String[] args) {
        System.out.println(checkSportSignValidity(new Timestamp(System.currentTimeMillis() - ONE_HOUR_TIME - TWO_HOUR_TIME)));
//        System.out.println(selfDefineDateFormat(new Date(System.currentTimeMillis())));
    }
}
