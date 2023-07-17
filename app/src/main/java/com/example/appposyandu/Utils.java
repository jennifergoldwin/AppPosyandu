package com.example.appposyandu;

import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Utils {

//    public static long getDateInMilliSeconds(String givenDateString, String format) {
//        String DATE_TIME_FORMAT = format;
//        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.US);
//        long timeInMilliseconds = 1;
//        try {
//            Date mDate = sdf.parse(givenDateString);
//            timeInMilliseconds = mDate.getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Log.v("UTILS", String.valueOf(timeInMilliseconds));
//        return timeInMilliseconds;
//    }

    public static float getMonthInFloat(String givenString){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
                    .withLocale(Locale.UK);
            LocalDate localDate = LocalDate.parse( givenString , formatter );
            return localDate.getMonthValue();
        }

        String str[] = givenString.split("/");
        return Integer.parseInt(str[1]);
    }
}
