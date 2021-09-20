package com.db5443pr2454563gglmps896rdf3.android.mywidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.db5443pr2454563gglmps896rdf3.android.mywidget.databinding.NewAppWidgetConfigureBinding;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

/**
 * The configuration screen for the {@link NewAppWidget NewAppWidget} AppWidget.
 */
public class NewAppWidgetConfigureActivity extends Activity {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    static Resource  rs = new Resource();

    EditText mCountDownText;
    EditText mTargetDateText;
    /*
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            Log.d("DENNISB", "mOnClickListener " + v.toString());

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
    };
     */
    private NewAppWidgetConfigureBinding binding;

    public NewAppWidgetConfigureActivity() {

        super();

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
        //binding.addButton.setOnClickListener(mOnClickListener);
        //binding.addButton.setOnClickListener(mOnClickListenerColor);

        Button addWidget = (Button) findViewById(R.id.add_button);
        addWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DENNISB", "OnClickListener addWidget ");

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
}