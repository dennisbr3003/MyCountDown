package com.db5443pr2454563gglmps896rdf3.android.mywidget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Constants, WidgetViewAdapter.ItemClickListener{

    WidgetViewAdapter adapter;
    Resource  rs = new Resource();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // data to populate the RecyclerView with
        ArrayList<WidgetRowElement> widgetRows = new ArrayList<>();

        SharedPreferences prefs = this.getSharedPreferences(SHAREDPREF_NAME, Context.MODE_PRIVATE);
        Map<String,?> keys = prefs.getAll();

        for(Map.Entry<String,?> entry : keys.entrySet()){

            String[] key_parts = entry.getKey().split("_");

            Log.d("DENNISB",entry.getKey() + ": " + entry.getValue() + ". key parts length = " + key_parts.length);
            if(key_parts.length == 3){
                // good one
                boolean found = false;
                for(WidgetRowElement wre:widgetRows){
                    if(wre.getWidgetId().equals(key_parts[1])){
                        found = true;
                        wre.setSomeValue(key_parts[2], entry.getValue().toString());
                    }
                }
                if(!found){
                    WidgetRowElement wre = new WidgetRowElement(key_parts[1]);
                    wre.setSomeValue(key_parts[2], entry.getValue().toString());
                    widgetRows.add(wre);
                }
            } else {
                // remove this crappy entry
                rs.deleteSingleWidgetSharedPreferences(this, entry.getKey());
                Log.d("DENNISB","Removed shared preference " + entry.getKey() + ": " + entry.getValue());
            }
        }


        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.WidgetView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WidgetViewAdapter(this, widgetRows);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    static class WidgetRowElement {

        private String countDownText;
        private String targetIsoDate;
        private String totalUpdates;
        private String lastUpdateTime;
        private String widgetId;

        public WidgetRowElement() {
        }

        public String getWidgetId() {
            return widgetId;
        }

        public void setWidgetId(String widgetId) {
            this.widgetId = widgetId;
        }

        public WidgetRowElement(String widgetId) {
            this.widgetId = widgetId;
        }

        public String getCountDownText() {
            return countDownText;
        }

        public void setCountDownText(String countDownText) {
            this.countDownText = countDownText;
        }

        public String getTargetIsoDate() {
            return targetIsoDate;
        }

        public void setTargetIsoDate(String targetIsoDate) {
            this.targetIsoDate = targetIsoDate;
        }

        public String getTotalUpdates() {
            return totalUpdates;
        }

        public void setTotalUpdates(String totalUpdates) {
            this.totalUpdates = totalUpdates;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        @Override
        public String toString() {
            return "WidgetRowElement{" +
                    "countDownText='" + countDownText + '\'' +
                    ", targetIsoDate='" + targetIsoDate + '\'' +
                    ", totalUpdates='" + totalUpdates + '\'' +
                    ", lastUpdateTime='" + lastUpdateTime + '\'' +
                    ", widgetId='" + widgetId + '\'' +
                    '}';
        }

        public void setSomeValue(String key, String value){

            Log.d("DENNISB", "SetSomeValue uses " + key + " (= method) " + value);

            switch(key){
                case "countDownText":
                    this.setCountDownText(value);
                    break;
                case "targetIsoDate":
                    this.setTargetIsoDate(value);
                    break;
                case "totalUpdates":
                    this.setTotalUpdates(value);
                    break;
                case "lastUpdateTime":
                    this.setLastUpdateTime(value);
                    break;
            }
        }

    }

}