package com.acukanov.sml.utils;


import android.content.Context;
import android.support.annotation.StringRes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

    public static String formatToDecimal(String text, int decimal) {
        return String.format(text, decimal);
    }

    public static String formatToDecimal(Context context, @StringRes int text, int decimal) {
        return formatToDecimal(context.getResources().getString(text), decimal);
    }

    public static String formatToString(String text, String string) {
        return String.format(text, string);
    }

    public static String formatToString(Context context, @StringRes int text, String string) {
        return formatToString(context.getResources().getString(text), string);
    }

    public static String formatToString(Context context, @StringRes int text1, @StringRes int text2) {
        return formatToString(context.getResources().getString(text1), context.getResources().getString(text2));
    }

    public static String dateToString(String dateString) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
        Date date = null;
        try {
            date = (Date)formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("dd MM yyyy");
        String finalString = newFormat.format(date);
        return finalString;
    }
}
