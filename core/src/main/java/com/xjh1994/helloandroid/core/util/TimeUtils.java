package com.xjh1994.helloandroid.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 *
 * @author lenovos20-2
 *         不支持同时加减年、月、日
 *         某些不支持格式进行转换 ps：yyyy-MM-dd HH:mm:ss
 */
public class TimeUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取两个日期之间的日期
     * @param start 开始日期
     * @param end 结束日期
     * @return 日期集合
     */
    public static List<String> getBetweenDates(Date start, Date end) {
        List<String> result = new ArrayList<String>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(sdf.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }

    public final static int YEAR = 1;
    public final static int MONTH = 2;
    public final static int DATE = 3;

    /**
     * 将日期以指定形式输出
     *
     * @param timeFormat yyyy-MM-dd
     * @param calendar
     * @return
     */
    public static String getYMD(Calendar calendar, String timeFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(timeFormat);
        String data = sf.format(calendar.getTime());
        return data;
    }

    /**
     * 时间转为calender 比如2015-10-12以 yyyy-MM-dd输出calender对象，有了calender对象我们可以做很多事情
     *
     * @param time       2015-10-10
     * @param timeFormat yyyy-MM-dd
     * @return
     */
    public static Calendar getCalendarByString(String time, String timeFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate(time, timeFormat));
        return calendar;
    }


    public static String getCusTime() {
        return getYMD(Calendar.getInstance(), "yyyy-MM-dd");
    }

    public static Date getDate(String time, String timeFormat) {
        Date date = null;
        try {
            DateFormat df = new SimpleDateFormat(timeFormat);
            date = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取时间-加减
     *
     * @param calendar
     * @param type     TimeUtils.YEAR
     * @param values   5 or -5
     * @return
     */
    public static String getTime(Calendar calendar, int type, int values) {
        if (type == YEAR) {
            calendar.add(Calendar.YEAR, values);
        } else if (type == MONTH) {
            calendar.add(Calendar.MONTH, values);
        } else if (type == DATE) {
            calendar.add(Calendar.DATE, values);
        }
        return getYMD(calendar, "yyyy-MM-dd");
    }

    /**
     * 获取时间-加减
     *
     * @param type
     * @param values 1 or -1
     * @return
     */
    public static String getTime(int type, int values) {
        Calendar calendar = Calendar.getInstance();
        return getTime(calendar, type, values);
    }

    public static String getTimeDay(Calendar calendar, int values) {
        calendar.add(Calendar.DATE, values);
        return getYMD(calendar, "yyyy-MM-dd");
    }

    /**
     * 获取当前时间-加减
     *
     * @param values 1 or -1
     * @return
     */
    public static String getTimeDay(int values) {
        Calendar calendar = Calendar.getInstance();
        return getTimeDay(calendar, values);
    }

    /**
     * time与timeto比较true time>timeto
     *
     * @param time
     * @param timeto
     * @param isReturnTrueEqual 相等的时候是否返回true
     * @return
     */
    public static boolean compareTime(String time, String timeto, boolean isReturnTrueEqual) {

        int result = getCalendarByString(time, "yyyy-MM-dd").compareTo(getCalendarByString(timeto, "yyyy-MM-dd"));
        if (isReturnTrueEqual) {
            return result >= 0 ? true : false;
        } else {
            return result > 0 ? true : false;
        }
    }

    /**
     * 获取星期
     *
     * @param calendar
     * @return
     */
    public static String getDayOfWeek(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String week = "";
        switch (dayOfWeek) {
            case 1:
                week = "周日";
                break;
            case 2:
                week = "周一";
                break;
            case 3:
                week = "周二";
                break;
            case 4:
                week = "周三";
                break;
            case 5:
                week = "周四";
                break;
            case 6:
                week = "周五";
                break;
            case 7:
                week = "周六";
                break;
        }
        return week;
    }

    /**
     * 获取某年某日星期几
     *
     * @param time 2015-12-01
     * @return 周六
     */
    public static String getDayOfWeek(String time) {
        return getDayOfWeek(getCalendarByString(time, "yyyy-MM-dd"));
    }

    public static String getPrefix(String datetime) {
        return getPrefix(datetime, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getPrefix(String datetime, String timeFormat) {
        long currentSeconds = System.currentTimeMillis();
        long timeGap = currentSeconds - getDate(datetime, timeFormat).getTime();// 与现在时间差
        String timeStr;
        if (timeGap > 24 * 3 * 60 * 60 * 1000) {
            timeStr = getYMD(getCalendarByString(datetime, timeFormat), "yy-MM-dd");
        } else if (timeGap > 24 * 2 * 60 * 60 * 1000) {
            timeStr = "前天 " + getYMD(getCalendarByString(datetime, timeFormat), "HH:mm");
        } else if (timeGap > 24 * 60 * 60 * 1000) {
            timeStr = "昨天 " + getYMD(getCalendarByString(datetime, timeFormat), "HH:mm");
        } else if (timeGap > 60 * 60 * 1000) {
            timeStr = timeGap / 3600 / 1000 + "小时前";
        } else if (timeGap > 60 * 1000) {
            timeStr = timeGap / 60 / 1000 + "分钟前";
        } else {
            timeStr = "刚刚";
        }
        return timeStr;
    }

}
