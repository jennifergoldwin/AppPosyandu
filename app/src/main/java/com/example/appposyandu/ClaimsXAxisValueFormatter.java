package com.example.appposyandu;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ClaimsXAxisValueFormatter extends ValueFormatter {

    String[] dates = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Des"};
//    public ClaimsXAxisValueFormatter(List<String> arrayOfDates) {
//        this.datesList = arrayOfDates;
//    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
//        Integer position = Math.round(value);
//
//        if (value > 1 && value < 2) {
//            position = 0;
//        } else if (value > 2 && value < 3) {
//            position = 1;
//        } else if (value > 3 && value < 4) {
//            position = 2;
//        } else if (value > 4 && value <= 5) {
//            position = 3;
//        }
//        if (position < dates.length)
            return dates[(int) value -1];
//        return "";
    }
}
