package cn.book.bus.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    /**
     * 获取mysql时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static Timestamp getTimestamp() {
        Date date = new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        Timestamp ts = Timestamp.valueOf(dateStr); //2017-05-06 15:54:21.0
        return ts;
    }
    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(new Date());
        return dateString;
    }
}
