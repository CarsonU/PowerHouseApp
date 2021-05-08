package com.example.powerhouseapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter for the recyclerview displaying the list of schedules in the schedule page activity
 */
public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.MyViewHolder> {
    private ArrayList<Schedule> scheduleList;

    /**
     * Adapter constructor
     * @param scheduleList The list of schedules to display
     */
    public ScheduleListAdapter(ArrayList<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public int position;
        private Switch switchScheduleName;
        private TextView txtTime;

        public MyViewHolder(final View view) {
            super(view);
            switchScheduleName = view.findViewById(R.id.switchSchedule);
            txtTime = view.findViewById(R.id.txtTime);
        }
    }

    /**
     * Called when the recyclerview needs a new viewholder
     * @param parent The recyclerview
     * @param viewType The type of view
     * @return the new view holder
     */
    @NonNull
    @Override
    public ScheduleListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedulelist, parent,false);
        return new MyViewHolder(itemView);
    }

    /**
     * Called by the recyclerview to display data at specific position
     * @param holder The specific row of the recyclerview being updated
     * @param position The position of the specific row within the recyclerview
     */
    @Override
    public void onBindViewHolder(@NonNull ScheduleListAdapter.MyViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);
        holder.switchScheduleName.setText(schedule.getName());
        holder.position = position;
        holder.txtTime.setText(schedule.getTime());
    }

    /**
     * Returns the amount of schedules, or the amount of rows in the recyclerview
     * @return the amount of schedules
     */
    @Override
    public int getItemCount() {
        return scheduleList.size();
    }
}
