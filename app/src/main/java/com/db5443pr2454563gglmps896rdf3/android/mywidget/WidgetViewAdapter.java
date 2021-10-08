package com.db5443pr2454563gglmps896rdf3.android.mywidget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WidgetViewAdapter extends RecyclerView.Adapter<WidgetViewAdapter.ViewHolder>  {
    private ArrayList<MainActivity.WidgetRowElement> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    WidgetViewAdapter(Context context, ArrayList<MainActivity.WidgetRowElement> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.widgetview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MainActivity.WidgetRowElement wre = mData.get(position);

        Log.d("DENNISB", wre.toString());

        holder.mCountDownText.setText(wre.getCountDownText());
        holder.mLastUpdateTime.setText(wre.getLastUpdateTime());
        holder.mWidgetID.setText(wre.getWidgetId());
        holder.mTotalUpdates.setText(wre.getTotalUpdates());
        holder.mTargetIsoDate.setText(wre.getTargetIsoDate());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mWidgetID;
        TextView mCountDownText;
        TextView mTotalUpdates;
        TextView mLastUpdateTime;
        TextView mTargetIsoDate;

        ViewHolder(View itemView) {
            super(itemView);
            mWidgetID = itemView.findViewById(R.id.txtWidgetID);
            mCountDownText = itemView.findViewById(R.id.txtCountDownText);
            mTotalUpdates = itemView.findViewById(R.id.txtTotalUpdatesText);
            mLastUpdateTime = itemView.findViewById(R.id.txtLastUpdates);
            mTargetIsoDate = itemView.findViewById(R.id.txtTargetIsoDateText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    MainActivity.WidgetRowElement getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}


