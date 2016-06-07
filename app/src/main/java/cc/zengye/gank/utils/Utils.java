package cc.zengye.gank.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zengye on 16/6/7.
 */
public class Utils {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    public static String formatDate(Date date) {
        return sdf.format(date);
    }
}
