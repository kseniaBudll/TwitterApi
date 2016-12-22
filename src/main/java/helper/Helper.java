package helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by KseniaB on 12/22/2016.
 */
public class Helper {

    /**
     * Method parses dates and returns their difference in seconds.
     */
    public static int getDifferenceBetween(String date1, String date2) throws ParseException {
        DateFormat d1 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.US);
        DateFormat d2 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.US);
        Date startDate = d1.parse(date1);
        Date endDate = d2.parse(date2);
        long duration = endDate.getTime() - startDate.getTime();
        return (int) TimeUnit.MILLISECONDS.toSeconds(duration);
    }
}
