package com.example.expensetracker.Helpers;

import static java.security.AccessController.getContext;

import android.widget.Button;

import com.example.expensetracker.Data.DataHandler;

import java.util.Calendar;

public class DateUtility {

    public static String getMonthFormatString(int month) {
        String monthString;

        switch (month) {
            case 2:
                monthString = "FEB";
                break;
            case 3:
                monthString = "MAR";
                break;
            case 4:
                monthString = "APR";
                break;
            case 5:
                monthString = "MAY";
                break;
            case 6:
                monthString = "JUN";
                break;
            case 7:
                monthString = "JUL";
                break;
            case 8:
                monthString = "AUG";
                break;
            case 9:
                monthString = "SEPT";
                break;
            case 10:
                monthString = "OCT";
                break;
            case 11:
                monthString = "NOV";
                break;
            case 12:
                monthString = "DEC";
                break;
            default:
                monthString = "JAN";
        }

        return monthString;
    };



    public static String getCurrentDate() {
        Calendar current = Calendar.getInstance();

        int year = current.get(Calendar.YEAR);
        int month = current.get(Calendar.MONTH) + 1;
        int day = current.get(Calendar.DAY_OF_MONTH);

        return createDateString(year, month, day);
    }


    public static String createDateString(int year, int month, int day) {
        return getMonthFormatString(month) + " " + day + " " + year;
    }

}
