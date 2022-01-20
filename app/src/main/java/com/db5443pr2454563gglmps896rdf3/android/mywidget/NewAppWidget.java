package com.db5443pr2454563gglmps896rdf3.android.mywidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link NewAppWidgetConfigureActivity NewAppWidgetConfigureActivity}
 */
public class NewAppWidget extends AppWidgetProvider {

    static Resource rs = new Resource();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        try { // update widget text fields
            if (getNumberOfDays(context, appWidgetId) > 0) {
                views.setTextViewText(R.id.txtDaysLeft, String.valueOf(getNumberOfDays(context, appWidgetId) + 1));
                views.setTextViewText(R.id.txtTimeUnit, "day(s) incl. this one");
            }
            else {
                if (getNumberOfHours(context, appWidgetId) > 0) {
                    views.setTextViewText(R.id.txtDaysLeft, String.valueOf(getNumberOfHours(context, appWidgetId)));
                    views.setTextViewText(R.id.txtTimeUnit, "hour(s)");
                } else {
                    if (getNumberOfMinutes(context, appWidgetId) > 0) {
                        views.setTextViewText(R.id.txtDaysLeft, String.valueOf(getNumberOfMinutes(context, appWidgetId)));
                        views.setTextViewText(R.id.txtTimeUnit, "minute(s)");
                    } else {
                        views.setTextViewText(R.id.txtTimeUnit, "");
                    }
                }
            }
            views.setTextViewText(R.id.txtCountDownTo, rs.getSharedReferenceCountDownText(context, appWidgetId));
            //update count and time of update
            rs.saveSharedReferenceLastUpdateTime(context, appWidgetId);
            rs.saveSharedReferenceCount(context, appWidgetId);
        } catch (Exception e) {
            Log.d("DENNISB", "Error " + e.getMessage());
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preferences associated with it.
        for (int appWidgetId : appWidgetIds) {
            rs.deleteAllWidgetSharedPreferences(context, appWidgetId);
        }
    }


    private static int getNumberOfDays(Context context, int appWidgetId){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime x = LocalDateTime.parse(rs.getSharedReferenceTargetDate(context, appWidgetId) + "T00:00:00.000000000");
            if(LocalDateTime.now().isBefore(x)) {
                return Integer.parseInt(String.valueOf(ChronoUnit.DAYS.between(LocalDateTime.now(), x)));
            } else {return 0;}
        }
        return 0;
    }

    private static int getNumberOfHours(Context context, int appWidgetId){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime x = LocalDateTime.parse(rs.getSharedReferenceTargetDate(context, appWidgetId) + "T00:00:00.000000000");
            if(LocalDateTime.now().isBefore(x)) {
                return Integer.parseInt(String.valueOf(ChronoUnit.HOURS.between(LocalDateTime.now(), x)));
            } else {return 0;}
        }
        return 0;
    }

    private static int getNumberOfMinutes(Context context, int appWidgetId){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime x = LocalDateTime.parse(rs.getSharedReferenceTargetDate(context, appWidgetId) + "T00:00:00.000000000");
            if(LocalDateTime.now().isBefore(x)) {
                return Integer.parseInt(String.valueOf(ChronoUnit.MINUTES.between(LocalDateTime.now(), x)));
            } else {return 0;}
        }
        return 0;
    }

}