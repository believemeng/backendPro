package com.dspread.august.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    //加减多少天
    public static Date  addDateNum(Date  date,Integer  num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, num);
        Date d = calendar.getTime();
        return d;
    }

    //加减多少月
    public static Date  addMonthNum(Date  date,Integer  num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, num);
        Date d = calendar.getTime();
        return d;
    }

    //字符串转日期格式
    public  static Date stringToDate(String time){
        return stringToDate(time,"yyyy-MM-dd HH:mm:ss");
    }

    //字符串转日期格式
    public  static Date stringToDate(String time,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(time);
            return date;
        }catch (ParseException ex){
            return  null;
        }
    }
    //日期转字符串yyyy-MM-dd HH:mm:ss格式
    public  static String dateToString(Date date){
        return dateToString(date,"yyyy-MM-dd HH:mm:ss");
    }

    //日期转字符串yyyy-MM-dd HH:mm:ss格式
    public  static String dateToString(Date date,String format){
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        String newTime  =  sdf.format(date);
        return  newTime;
    }

    //日期转字符串yyyy-MM-dd HH:mm:ss格式
    public  static String dateToStringI(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String newTime  =  sdf.format(date);
        return  newTime;
    }

    //日期转字符串yyyyMMddHHmmss格式
    public  static String dateToStringIn(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("MM'月'dd'日'HH'时'");
        String newTime  =  sdf.format(date);
        return  newTime;
    }

    //日期转字符串yyyy-MM-dd格式
    public  static Date getDate(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String newTime  =  sdf.format(date);
        try {
            Date 	pase = sdf.parse(newTime);
            return  pase ;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
            return  null ;
        }
    }

    //比较两个日期相隔多少天
    public  static  Integer  apartDate(Date startDate,Date endDate){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate=sdf.parse(sdf.format(startDate));
            endDate=sdf.parse(sdf.format(endDate));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    //比较两个日期相差多少月
    public  static  Integer   apartMonth(Date startDate,Date endDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        Integer y1 = cal.get(Calendar.YEAR);
        Integer m1 =cal.get(Calendar.MONTH);
        cal.setTime(endDate);
        Integer y2 = cal.get(Calendar.YEAR);
        Integer m2 =cal.get(Calendar.MONTH);
        Integer result;
        if(y1==y2){
            result=m1-m2;//两个日期相差几个月，即月份差
        }
        else{
            result=12*(y1-y2)+m1-m2;//两个日期相差几个月，即月份差
        }
        return result;
    }

    //日期转字符串yyyyMMddHHmmss格式
    public  static String dateToStringIn2(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newTime  =  sdf.format(date);
        return  newTime;
    }


    //获得指定日期30天后的日期
    public  static String  addDate(Date date,long day){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 日期格式
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day*24*60*60*1000; // 要加上的天数转换成毫秒数
        time+=day; // 相加得到新的毫秒数
        return	 dateFormat.format(new Date(time));
    }

    /**
     * unix时间戳
     * @param date
     * @return
     */
    public static long getTimeStamp(Date date){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ss= df.format(date);
        try {
            return df.parse(ss).getTime() / 1000;
        }
        catch (ParseException ee){
            return 0;
        }
    }

}
