package com.db5443pr2454563gglmps896rdf3.android.mywidget;

import android.app.Activity;
import android.app.ActivityManager;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.db5443pr2454563gglmps896rdf3.android.mywidget.databinding.NewAppWidgetConfigureBinding;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * The configuration screen for the {@link NewAppWidget NewAppWidget} AppWidget.
 */
public class NewAppWidgetConfigureActivity extends Activity {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    static Resource  rs = new Resource();

    EditText mCountDownText;
    EditText mTargetDateText;

    private NewAppWidgetConfigureBinding binding;

    public NewAppWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onBackPressed() {
        Log.d("DENNISB", "onBackPressed event fired for widget " + mAppWidgetId);
        cancelAndRemoveWidget();
        super.onBackPressed();
    }

    @Override
    protected void onUserLeaveHint() {
        Log.d("DENNISB", "onUserLeaveHint event fired for widget " + mAppWidgetId);
        cancelAndRemoveWidget();
        super.onUserLeaveHint();
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return super.dispatchKeyShortcutEvent(event);
    }

    public void cancelAndRemoveWidget() {

        // when you press back in the configuration activity the widget that is
        // being configured (and is in fact already created) will be removed to
        // avoid creating ghost widgets.

        Intent intent = new Intent();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_CANCELED, intent);
        finish();

        Log.d("DENNISB", "Widget " + mAppWidgetId + " deleted");

    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        binding = NewAppWidgetConfigureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mCountDownText = binding.ptxtCountDownText;
        mTargetDateText = binding.ptxtTargetDate;

        Button addWidget = (Button) findViewById(R.id.add_button);

        addWidget.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Log.d("DENNISB", "OnClickListener addWidget ");

                // check target date
                SimpleAnswer simpleAnswer = checkTargetDate(mTargetDateText.getText().toString());
                if(!simpleAnswer.islAnswer()){
                    Log.d("DENNISB", "SimpleAnswer message " + simpleAnswer.getMessage());
                    // show error dialog that has to be tabbed, then break.
                    return;
                }

                final Context context = NewAppWidgetConfigureActivity.this;

                rs.saveSharedReferenceTargetDate(mTargetDateText.getText().toString(), context, mAppWidgetId);
                rs.saveSharedReferenceCountDownText(mCountDownText.getText().toString(), context, mAppWidgetId);

                // It is the responsibility of the configuration activity to update the app widget
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                NewAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

                // Make sure we pass back the original appWidgetId
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });

        Button selectColorText = (Button) findViewById(R.id.btnTextColor);
        selectColorText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DENNISB", "mOnClickListener selectTextColor");

                final Context context = NewAppWidgetConfigureActivity.this;
                ColorPickerDialogBuilder
                        .with(context)
                        .setTitle("Choose color")
                        .initialColor(rs.getSharedReferenceTextColor(context, mAppWidgetId))
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                Log.d("onColorSelected: 0x", Integer.toHexString(selectedColor));
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                rs.saveSharedReferenceTextColor(selectedColor, context, mAppWidgetId);
                                TextView textColorSample = (TextView) findViewById(R.id.txtTextColor);
                                textColorSample.setTextColor(rs.getSharedReferenceTextColor(context, mAppWidgetId));
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("onColorSelectedCancel", "cancel");
                            }
                        })
                        .build()
                        .show();

            }
        });

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        mCountDownText.setTextColor(rs.getSharedReferenceTextColor(NewAppWidgetConfigureActivity.this, mAppWidgetId));
        mCountDownText.setText(rs.getSharedReferenceCountDownText(NewAppWidgetConfigureActivity.this, mAppWidgetId));
        mTargetDateText.setTextColor(rs.getSharedReferenceTextColor(NewAppWidgetConfigureActivity.this, mAppWidgetId));
        mTargetDateText.setText(rs.getSharedReferenceTargetDate(NewAppWidgetConfigureActivity.this, mAppWidgetId));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private SimpleAnswer checkTargetDate(String targetDate){

        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE
                .withResolverStyle(ResolverStyle.STRICT);

        DateValidator dateValidator = new DateValidator(dateFormatter);
        return dateValidator.isValid(targetDate);

    }

}