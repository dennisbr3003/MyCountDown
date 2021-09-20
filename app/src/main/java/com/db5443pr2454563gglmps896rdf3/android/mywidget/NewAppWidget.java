package com.db5443pr2454563gglmps896rdf3.android.mywidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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
            views.setTextViewText(R.id.txtDaysLeft, String.valueOf(getNumberOfDays(context, appWidgetId)));
            views.setTextViewText(R.id.txtCountDownTo, rs.getSharedReferenceCountDownText(context, appWidgetId));
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

}