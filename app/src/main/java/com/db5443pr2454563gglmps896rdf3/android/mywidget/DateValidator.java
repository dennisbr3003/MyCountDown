package com.db5443pr2454563gglmps896rdf3.android.mywidget;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator {
    private DateTimeFormatter dateFormatter;

    public DateValidator(DateTimeFormatter dateFormatter) {
        super();
        this.dateFormatter = dateFormatter;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public SimpleAnswer isValid(String dateStr) {
        try {
            this.dateFormatter.parse(dateStr);
        } catch (DateTimeParseException e) {
            Log.d("DENNISB", "Fout " + e.getMessage());
            SimpleAnswer simpleAnswer = new SimpleAnswer(false, e.getMessage());
            return simpleAnswer;
        }
        SimpleAnswer simpleAnswer = new SimpleAnswer(true);
        return simpleAnswer;
    }
	
}
