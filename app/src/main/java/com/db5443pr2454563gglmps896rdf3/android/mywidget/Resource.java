package com.db5443pr2454563gglmps896rdf3.android.mywidget;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Resource implements Constants {

    public void saveSharedReferenceTargetDate(String targetIsoDate, Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString(PREF_PREFIX_KEY + appWidgetId + "_" + TARGET_DATE, targetIsoDate);
        prefsEdit.apply(); // apply is background, commit is not
    }

    public String getSharedReferenceTargetDate(Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(PREF_PREFIX_KEY + appWidgetId + "_" + TARGET_DATE, "2100-01-01");
    }

    public void saveSharedReferenceTextColor(int textColor, Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString(PREF_PREFIX_KEY + appWidgetId + "_" + TEXT_COLOR, String.valueOf(textColor));
        prefsEdit.apply(); // apply is background, commit is not
    }

    public int getSharedReferenceTextColor(Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        return Integer.parseInt(prefs.getString(PREF_PREFIX_KEY + appWidgetId + "_" + TEXT_COLOR, String.valueOf(Color.parseColor("#000000"))));
    }

    public void saveSharedReferenceCountDownText(String countDownText, Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString(PREF_PREFIX_KEY + appWidgetId + "_" + COUNTDOWN_TEXT, countDownText);
        prefsEdit.apply(); // apply is background, commit is not
    }

    public String getSharedReferenceCountDownText(Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(PREF_PREFIX_KEY + appWidgetId + "_" + COUNTDOWN_TEXT, "Days until some event");
    }

    public String getSharedReferenceCount(Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(PREF_PREFIX_KEY + appWidgetId + "_" + UPDATE_COUNT, "0");
    }

    public void saveSharedReferenceCount(Context context, int appWidgetId){
        int count = Integer.parseInt(getSharedReferenceCount(context, appWidgetId));
        count++;
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString(PREF_PREFIX_KEY + appWidgetId + "_" + UPDATE_COUNT, String.valueOf(count));
        prefsEdit.apply();
    }

    public void saveSharedReferenceLastUpdateTime(Context context, int appWidgetId){
        Date cdate = new Date();
        String strDateFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString(PREF_PREFIX_KEY + appWidgetId + "_" + LAST_UPDATE, sdf.format(cdate));
        prefsEdit.apply();
    }

    public String getSharedReferenceLastUpdateTime(Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(PREF_PREFIX_KEY + appWidgetId + "_" + LAST_UPDATE, "00:00");
    }

    public void deleteAllWidgetSharedPreferences(Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.remove(PREF_PREFIX_KEY + appWidgetId + "_" + COUNTDOWN_TEXT);
        prefsEdit.remove(PREF_PREFIX_KEY + appWidgetId + "_" + TARGET_DATE);
        prefsEdit.remove(PREF_PREFIX_KEY + appWidgetId + "_" + TEXT_COLOR);
        prefsEdit.remove(PREF_PREFIX_KEY + appWidgetId + "_" + UPDATE_COUNT);
        prefsEdit.remove(PREF_PREFIX_KEY + appWidgetId + "_" + LAST_UPDATE);
        prefsEdit.apply(); // apply is background, commit is not
    }

    public void deleteSingleWidgetSharedPreferences(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.remove(key);
        prefsEdit.apply(); // apply is background, commit is not
    }

}
