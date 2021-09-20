package com.db5443pr2454563gglmps896rdf3.android.mywidget;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

public class Resource implements Constants {

    public void saveSharedReferenceTargetDate(String targetIsoDate, Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString(PREF_PREFIX_KEY + appWidgetId + TARGET_DATE, targetIsoDate);
        prefsEdit.apply(); // apply is background, commit is not
    }

    public String getSharedReferenceTargetDate(Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(PREF_PREFIX_KEY + appWidgetId + TARGET_DATE, "2100-01-01");
    }

    public void saveSharedReferenceTextColor(int textColor, Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString(PREF_PREFIX_KEY + appWidgetId + TEXT_COLOR, String.valueOf(textColor));
        prefsEdit.apply(); // apply is background, commit is not
    }

    public int getSharedReferenceTextColor(Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        return Integer.parseInt(prefs.getString(PREF_PREFIX_KEY + appWidgetId + TEXT_COLOR, String.valueOf(Color.parseColor("#000000"))));
    }

    public void saveSharedReferenceCountDownText(String countDownText, Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.putString(PREF_PREFIX_KEY + appWidgetId + COUNTDOWN_TEXT, countDownText);
        prefsEdit.apply(); // apply is background, commit is not
    }

    public String getSharedReferenceCountDownText(Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(PREF_PREFIX_KEY + appWidgetId + COUNTDOWN_TEXT, "Days until some event");
    }

    public void deleteAllWidgetSharedPreferences(Context context, int appWidgetId){
        SharedPreferences prefs = context.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEdit = prefs.edit();
        prefsEdit.remove(PREF_PREFIX_KEY + appWidgetId + COUNTDOWN_TEXT);
        prefsEdit.remove(PREF_PREFIX_KEY + appWidgetId + TARGET_DATE);
        prefsEdit.remove(PREF_PREFIX_KEY + appWidgetId + TEXT_COLOR);
        prefsEdit.apply(); // apply is background, commit is not
    }

}
