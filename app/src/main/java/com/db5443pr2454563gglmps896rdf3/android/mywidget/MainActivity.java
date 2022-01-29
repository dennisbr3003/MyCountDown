package com.db5443pr2454563gglmps896rdf3.android.mywidget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Constants, WidgetViewAdapter.ItemClickListener{

    WidgetViewAdapter adapter;
    Resource  rs = new Resource();
    int ghostwidgets = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        supportInvalidateOptionsMenu();

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

        // add some provision for ghost widgets. They don't have target dates associated with them
        // We will add a counter to keep track of the number of ghost widgets (a special wre instance is added)
        Log.d("DENNISB","Scan for ghostwidgets");
        try {
            Iterator<WidgetRowElement> i = widgetRows.iterator();
            while (i.hasNext()){
                WidgetRowElement wre = i.next();
                if (wre.targetIsoDate == "" || wre.targetIsoDate == null || wre.targetIsoDate == "null"){
                    // remove any shared preferences that may exist will remove evidence this app created a ghost widget
                    // rs.deleteAllWidgetSharedPreferences(this, Integer.parseInt(wre.getWidgetId()));
                    Log.d("DENNISB", "Found and removed ghostwidget " + wre.getWidgetId() + " from recyclerview");
                    i.remove();
                    ghostwidgets++;
                }
            }
            Log.d("DENNISB", "Found " + ghostwidgets + " ghostwidgets");

        }catch(Exception e){
            Log.d("DENNISB", "Exception " + e.getMessage());
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

    @Override
    public boolean onPrepareOptionsMenu (Menu menu){

        MenuItem itemGhost = menu.findItem(R.id.ghost);

        switch (ghostwidgets) {
            case 0:
                itemGhost.setIcon(ContextCompat.getDrawable(this, R.mipmap.image29a));
                break;
            case 1:
                itemGhost.setIcon(ContextCompat.getDrawable(this, R.mipmap.image28a));
                break;
            case 2:
                itemGhost.setIcon(ContextCompat.getDrawable(this, R.mipmap.image27a));
                break;
            case 3:
                itemGhost.setIcon(ContextCompat.getDrawable(this, R.mipmap.image26a));
                break;
            case 4:
                itemGhost.setIcon(ContextCompat.getDrawable(this, R.mipmap.image25a));
                break;
            case 5:
                itemGhost.setIcon(ContextCompat.getDrawable(this, R.mipmap.image24a));
                break;
            case 6:
                itemGhost.setIcon(ContextCompat.getDrawable(this, R.mipmap.image23a));
                break;
            case 7:
                itemGhost.setIcon(ContextCompat.getDrawable(this, R.mipmap.image20a));
                break;
            case 8:
                itemGhost.setIcon(ContextCompat.getDrawable(this, R.mipmap.image19a));
                break;
            case 9:
                itemGhost.setIcon(ContextCompat.getDrawable(this, R.mipmap.image21a));
                break;
            default:
                itemGhost.setIcon(ContextCompat.getDrawable(this, R.mipmap.image22a));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.ghost:
                //no action, I use the menu as a placeholder for an icon
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
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