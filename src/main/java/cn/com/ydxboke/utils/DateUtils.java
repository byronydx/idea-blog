package cn.com.ydxboke.utils;

import org.apache.commons.lang.math.NumberUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

。lang.math.NumberUtils;
        ;

/**
 * 日期处理工具类
 * Created by hiyond on 2016/11/15.
 */
public class DateUtils {

    public enum DateFormatType {
        TYPE1("yyyy-MM-dd HH:mm:ss"),
        TYPE2("yyyy-MM-dd"),
        TYPE3("yyyyMMddHHmmss"),
        TYPE4("yyyyMMdd"),
        TYPE5("yyyy-MM"),
        TYPE6("yyyy-MM-dd HH:mm:ss SSS"),
        TYPE7("MM月 dd日 HH:mm");

        DateFormatType(String type) {
            this.type = type;
        }

        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    /**
     * 日期转为字符串格式
     *
     * @param date 日期
     * @param type 需要转换的类型
     * @return 返回日期的字符串
     * @throws Exception 异常抛出
     */
    public static String date2String(Date date, DateFormatType type) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type.getType());
            return simpleDateFormat.format(date);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 字符串转日期
     *
     * @param date 日期格式的字符串
     * @param type 日期类型
     * @return 返回转换后的日期
     * @throws Exception 抛出异常
     */
    public static Date string2Date(String date, DateFormatType type) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type.getType());
            return simpleDateFormat.parse(date);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 转换字符串的日期格式
     *
     * @param dateS   日期字符串
     * @param oldType 原来的日期格式
     * @param type    要转换的日期格式
     * @return 返回新格式的字符串
     * @throws Exception 抛出异常
     */
    public static String dateTransType(String dateS, String oldType, DateFormatType type) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oldType);
            Date date = simpleDateFormat.parse(dateS);
            return new SimpleDateFormat(type.getType()).format(date);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 只获得日期的年月日
     * 处理格式为yyyy-MM-dd的字符串
     *
     * @param date
     * @return
     */
    public static String dateSubStringGetYMD(String date) {
        try {
            date = date.substring(0, 10);
            return date;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 只获得日期的年月
     * 处理格式为yyyy-MM的字符串
     *
     * @param date
     * @return
     */
    public static String dateSubStringGetYM(String date) {
        try {
            date = date.substring(0, 7);
            return date;
        } catch (Exception e) {
        }
        return null;
    }

    //获取上个月份年月
    public static String getCurrentLastMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, -1);
        String monthNowStr = sdf.format(cale.getTime());
        return dateSubStringGetYM(monthNowStr);
    }

    //获取当前这个月份年月
    public static String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = Calendar.getInstance();
        String monthNowStr = sdf.format(cale.getTime());
        return dateSubStringGetYM(monthNowStr);
    }

    //获取下个月的几点几分几秒
    public static Date getNextMonthDay(int day, int hour, int minute, int second, int millisecond) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, millisecond);
        Date strDateTo = c.getTime();
        return strDateTo;
    }

    //获取上个月的月初、月末  传参数0 表示开始时间获取 |  其他表示结束时间获取
    public static String getCurrentStartAndEnd(int start) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String resStr = "";
        if (start == 0) {
            //获取当前月第一天
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, 0);
            c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            Date strDateTo = c.getTime();
            resStr = format.format(strDateTo);
        } else {
            //获取当前月最后一天
            Calendar ca = Calendar.getInstance();
            ca.add(Calendar.MONTH, 0);
            ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
            ca.set(Calendar.HOUR_OF_DAY, 23);
            ca.set(Calendar.MINUTE, 59);
            ca.set(Calendar.SECOND, 59);
            ca.set(Calendar.MILLISECOND, 59);
            Date strDateTo = ca.getTime();
            resStr = format.format(strDateTo);
        }

        return resStr;
    }
    /*public static void main(String[] args) throws Exception {
        System.out.println(DateFormatType.TYPE1.getType());
        System.out.println(Date2String(new Date(), DateFormatType.TYPE1));
        System.out.println(dateSubStringGetYMD("2016-11-1888"));
        System.out.println(String2Date(dateSubStringGetYMD("2016-11-1888"), DateUtils.DateFormatType.TYPE2));
    }*/

    public static Date getFormatNextMonthSomeDate(int day) {

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return new Date(calendar.getTimeInMillis());
    }

    public final static Date getString2Date(String strDate) {
        Date date = null;
        if (strDate != null && !"".equals(strDate)) {
            try {
                date = getDateFormat().parse(strDate);
            } catch (ParseException e) {
//                logger.error("getString2Date出错。", e);
            }
        }
        return date;
    }


    public static DateFormat getDateFormat() {
        return (DateFormat) threadLocal.get();
    }

    private static ThreadLocal threadLocal = new ThreadLocal() {
        @Override
        protected synchronized Object initialValue() {
            return new SimpleDateFormat(DATE_FORMAT);
        }
    };

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 方法功能描述：将传过来的date进行格式化成23点59分59秒，年月日不变，并返回
     * <p>
     * <b>参数说明</b>
     *
     * @param date 时间
     * @return <p>
     */
    public static Date formatTo24Hours(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        date = new Date(calendar.getTimeInMillis());
        return date;
    }

    /**
     * @param date
     * @describe 将传过来的date进行格式化成0点0分0秒，年月日不变，并返回
     */
    public static Date formatTo0Hours(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = new Date(calendar.getTimeInMillis());
        return date;
    }

    /**
     * 方法功能描述：将传过来的date进行格式化当月第一天的0点0分0秒0毫秒，年月不变，并返回
     * <p>
     * <b>参数说明</b>
     *
     * @param date date
     * @return <p>
     * @since 1.0
     */
    public static Date getFormatFirstDayDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = new Date(calendar.getTimeInMillis());
        return date;
    }

    /**
     * 方法功能描述：将传过来的date进行格式化当月最后一天的23点59分59秒，年月不变，并返回
     * <p>
     * <b>参数说明</b>
     *
     * @param date date
     * @return <p>
     * @since 1.0
     */
    public static Date getFormatLastDayDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int days = calendar.get(Calendar.DATE);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, days);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        date = new Date(calendar.getTimeInMillis());
        return date;
    }

    /**
     * 日期加上天数的时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date dateAddDay(Date date, int day) {
        return add(date, Calendar.DAY_OF_YEAR, day);
    }

    private static Date add(Date date, int type, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, value);
        return calendar.getTime();
    }

    /**
     * 计算两个时间之间相差的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static long diffDays(Date startDate, Date endDate) {
        long days = 0;
        long start = startDate.getTime();
        long end = endDate.getTime();
        // 一天的毫秒数1000 * 60 * 60 * 24=86400000
        days = (end - start) / 86400000;
        return days;
    }

    /**
     * 根据传入的日期,设定的小时，返回日期时间
     *
     * @param date
     * @param HOUR
     * @return
     */
    public static Date setDateHour(Date date, int HOUR) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.HOUR_OF_DAY, 2);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return now.getTime();
    }

    /**
     * 方法功能描述：将传过来的date进行格式化成00点00分00秒，年月日不变，并返回
     * <p>
     * <b>参数说明</b>
     *
     * @param date 时间
     * @return <p>
     */
    public static Date formatToStartHours(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        date = new Date(calendar.getTimeInMillis());
        return date;
    }


    /**
     * 上个月有多少天
     * @return
     */
    public static int getPreMonthDays(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    /**
     * 上个月的今天是第几天
     * @return
     */
    public static int getPreMonthWhatDay(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.get(Calendar.DAY_OF_MONTH);
        return c.get(Calendar.DAY_OF_MONTH);

    }

    /**
     * 上个月的今天
     * @return
     */
    public static Date getPreMonthDay() throws ParseException {
        Calendar c = Calendar.getInstance();

        c.add(Calendar.MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(c.getTime());
        return sdf.parse(s);
    }

    /**
     * 获取之前月份或之后月份的日期
     * 规则：
     * 如果参数{date}的月份总天数大于参数{amount}个月前（后）的总天数，返回值为null
     * 如果参数{date}的月份总天数与参数{amount}个月的前（后）总天数相等，返回参数{amount}个月前（后）与{date}天相同的日期
     * 如果参数{date}的月份总天数小于参数{amount}个月的前（后）总天数，返回参数{amount}个月前（后）与{date}天相同的日期
     *
     * @param date   日期
     * @param amount 之前或之后多少个月
     * @return
     */
    public static Date dateAddMonth(final Date date, final int amount) {
        if(amount == 0) {
            return date;
        }
        if (date == null) {
            throw new IllegalArgumentException("参数date不能为空!");
        }
        final Calendar addMonthCal = Calendar.getInstance();
        addMonthCal.setTime(date);
        addMonthCal.add(Calendar.MONTH, amount);
        Date resultDate = addMonthCal.getTime();
        int addMonthDay = addMonthCal.getActualMaximum(Calendar.DATE);

        final Calendar nowMonthCal = Calendar.getInstance();
        nowMonthCal.setTime(date);
        int nowMonthDay = nowMonthCal.getActualMaximum(Calendar.DATE);
        int nowDay = nowMonthCal.get(Calendar.DATE);

        int min = NumberUtils.min(addMonthDay, nowMonthDay);
        if (nowDay <= min || addMonthDay >= nowMonthDay) {
            return resultDate;
        }
        return null;
    }

    //获得上月最后一天的日期
    public static String getPreviousMonthEnd(){
        String str = "";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH,-1);//减一个月
        lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天
        str=sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 获取之前月份或之后月份的日期
     * 规则：
     * 如果参数{date}的月份总天数大于参数{amount}个月前（后）的总天数，返回值为{amount}个月的最后一天
     * 如果参数{date}的月份总天数与参数{amount}个月的前（后）总天数相等，返回参数{amount}个月前（后）与{date}天相同的日期
     * 如果参数{date}的月份总天数小于参数{amount}个月的前（后）总天数，返回参数{amount}个月前（后）与{date}天相同的日期
     *
     * @param date   日期
     * @param amount 之前或之后多少个月
     * @return
     */
    public static Date dateAddMonthTime(final Date date, final int amount) {
        if(amount == 0) {
            return date;
        }
        if (date == null) {
            throw new IllegalArgumentException("参数date不能为空!");
        }
        final Calendar addMonthCal = Calendar.getInstance();
        addMonthCal.setTime(date);
        addMonthCal.add(Calendar.MONTH, amount);
        return addMonthCal.getTime();
    }

    // 获取给定时间月份，cal.get(Calendar.MONTH)是从零开始。
    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * 获取{date}是几号
     * @param date 日期
     * @return {int}
     */
    public static int getDateDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 根据规则获取某一个时间点的年月或上一个月的年月
     * 如果是分割点{split}之前的，获取{nowDate}上个月的数据
     * 否则获取{nowDate}本月的数据
     * @param nowDate 时间点
     * @param split
     * @return {String}
     */
    public static String getYearMonthByDateAndSplit(Date nowDate, final int split) {
        int day = getDateDay(nowDate);
        Date date = day > split ? nowDate : dateAddMonthTime(nowDate, -1);
        return date2String(date, DateFormatType.TYPE5);
    }

    /**
     * 根据规则获取时间节点当月四
     * 如果是分割点{split}之前的，获取{nowDate}上个月的数据和本月的数据
     * 否则获取{nowData}本月的数据
     */
    public static String getYearMouth(Date nowDate){
        int split = 4;
        int day = getDateDay(nowDate);
        Date date = day > split ? nowDate : dateAddMonthTime(nowDate, -1);
        return date2String(date, DateFormatType.TYPE5);


    }

    /**
     * 得到给定时间的日
     *
     * @return
     */
    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    //获得上月第一天的日期
    public static String getPreviousMonthStart(){
        String str = "";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH,-1);//减一个月
        lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天
        str=sdf.format(lastDate.getTime());
        return str;
    }

    //拿到当月的第一天
    public static String getCurrentMonthStart() {
        String str = "";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天
        str=sdf.format(lastDate.getTime());
        return str;
    }

    //拿到当月的最后一天
    public static String getCurrentMonthEnd() {
        String str = "";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);//日期回滚一天，也就是本月最后一天
        str=sdf.format(lastDate.getTime());
        return str;
    }

    /**
     * 拿到当月的天数
     * @return
     */
    public static int getCurrentMonthDays() {
        Calendar c = Calendar.getInstance();
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 通过年月获取月份的天数
     * @param year 年份
     * @param month 月份
     * @return 月份中有多少天
     */
    public static int getDaysByYearMonth(int year, int month){
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int days = a.get(Calendar.DATE);

        return days;
    }

    /**
     * 字符串格式日期格式化
     *
     * @param date 日期格式的字符串
     * @param type 日期类型
     * @return 返回转换后的日期
     * @throws Exception 抛出异常
     */
    public static String StringDateFormat(String date, DateFormatType type) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(type.getType());
            return simpleDateFormat.format(simpleDateFormat.parse(date));
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 将重复代码排重
     * @param taskData worker中的taskData
     * @return list
     * @throws ParseException
     */
    /*public static List<Date> jsonToDate(String taskData) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DateFormatType.TYPE1.type);
        JSONObject jsonData = JSONObject.fromObject(taskData);
        String startTime = (String) jsonData.get("startTime");
        String endTime = (String) jsonData.get("endTime");
        List<Date> listDate = new ArrayList<Date>();
        Date startT = sdf.parse(startTime);
        Date endT = sdf.parse(endTime);
        listDate.add(startT);
        listDate.add(endT);
        return listDate;
    }*/

    /**
     * 按指定规则进行日期比较，只要格式化后的字符串相等则认为日期相等
     *
     * @param one
     * @param two
     * @param format 指定日期格式，
     * @return
     */
    public static boolean equals(Date one, Date two, String format) {
        if (one == null && two == null) {
            return true;
        }

        if (one != null && two != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

            if (simpleDateFormat.format(one).equals(simpleDateFormat.format(two))) {
                return true;
            }
        }

        return false;
    }

}
