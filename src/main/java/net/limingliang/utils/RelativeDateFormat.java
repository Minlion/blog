package net.limingliang.utils;

import java.util.Date;

/**
 * 几秒，几分钟，几小时，几天，几月，几年没有留言
 * @author Sunny
 *
 */
public class RelativeDateFormat {
	
    private static final long ONE_MINUTE = 60000L;  
    private static final long ONE_HOUR = 3600000L;  
    private static final long ONE_DAY = 86400000L;  
    private static final long ONE_WEEK = 604800000L;
    private static final long ONE_MONTH = 2592000000L;
    private static final long ONE_YEAR = 31536000000L;
    
    private static final String ONE_SECOND_AGO = "秒";  
    private static final String ONE_MINUTE_AGO = "分钟";  
    private static final String ONE_HOUR_AGO = "小时";  
    private static final String ONE_DAY_AGO = "天";
    private static final String ONE_WEEK_AGO = "周";
    private static final String ONE_MONTH_AGO = "月";  
    private static final String ONE_YEAR_AGO = "年";  
  
    public static String format(Date date) {  
        long delta = new Date().getTime() - date.getTime();  
        if (delta < 1L * ONE_MINUTE) {  
            long seconds = toSeconds(delta);  
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;  
        }  
        if (delta < 1L * ONE_HOUR) {  
            long minutes = toMinutes(delta);  
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;  
        }  
        if (delta < 1L * ONE_DAY) {  
            long hours = toHours(delta);  
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;  
        }  
        if (delta < 1L * ONE_WEEK) {  
        	long days = toDays(delta);
        	return (days <= 0 ? 1 : days) + ONE_DAY_AGO;  
        }
        if(delta < 1L * ONE_MONTH){
        	long weeks = toWeeks(delta);
        	return (weeks <= 0 ? 1 : weeks) + ONE_WEEK_AGO;
        }
        if (delta < 1L * ONE_YEAR) {  
            long months = toMonths(delta); 
            return (months >=12 ? 11 : months <= 0 ? 1 : months) + ONE_MONTH_AGO;  
        }
        long years = toYears(delta);  
        return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;  
    }  
  

	private static long toSeconds(long date) {  
        return date / 1000L;  
    }  
  
    private static long toMinutes(long date) {  
        return toSeconds(date) / 60L;  
    }  
  
    private static long toHours(long date) {  
        return toMinutes(date) / 60L;  
    }  
  
    private static long toDays(long date) {  
        return toHours(date) / 24L;  
    }  
  
    private static long toWeeks(long delta) {
		return toDays(delta) / 7L;
	}

    private static long toMonths(long date) {  
        return toDays(date) / 30L;  
    }  
  
    private static long toYears(long date) {  
        return toDays(date) / 365L;  
    } 
}
