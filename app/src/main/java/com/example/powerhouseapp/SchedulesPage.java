package com.example.powerhouseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Schedules page activity
 */
public class SchedulesPage extends AppCompatActivity {
    private static ArrayList<Schedule> scheduleList;
    private RecyclerView scheduleRecycler;
    /**
     * Called when the schedule page activity starts
     * @param savedInstanceState Contains the most recent data if the activity is being re-initialized
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_page);

        scheduleRecycler = findViewById(R.id.schedules);
        scheduleList = new ArrayList<>();

        setScheduleList();
        setScheduleRecycler();

        FloatingActionButton newSchedule = (FloatingActionButton) findViewById(R.id.newSchedule);
        newSchedule.setOnClickListener(new View.OnClickListener(){
            /**
             * Opens the popup window to add the new schedule when the floating action button is clicked
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(SchedulesPage.this, NewSchedule.class), 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Sets recyclerview for schedules
     */
    private void setScheduleRecycler(){
        ScheduleListAdapter adapter = new ScheduleListAdapter(scheduleList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        scheduleRecycler.setLayoutManager(layoutManager);
        scheduleRecycler.addItemDecoration(new DividerItemDecoration(scheduleRecycler.getContext(), DividerItemDecoration.VERTICAL));
        scheduleRecycler.setItemAnimator(new DefaultItemAnimator());
        scheduleRecycler.setAdapter(adapter);
    }

    /**
     * Sets schedule data
     */
    private void setScheduleList(){
        scheduleList.add(new Schedule("Nighttime", "12:00pm"));
        scheduleList.add(new Schedule("Morning","7:00am"));
    }


}
