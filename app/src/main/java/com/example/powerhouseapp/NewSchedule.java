package com.example.powerhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

/**
 * New schedule pop up window activity
 */
public class NewSchedule extends Activity {
    /**
     * Called when the new outlet window activity is started
     * @param savedInstanceState Contains the most recent data if the activity is being re-initialized
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newschedulewindow);

        //Popup window size is 75% as big as main window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels*.75),(int) (dm.heightPixels*.75));

        //Text fields and buttons
        Button createSchedule = (Button) findViewById(R.id.btnCreateSchedule);
        EditText editName = (EditText) findViewById(R.id.editTextScheduleName);
        EditText editTime = (EditText) findViewById(R.id.editTextTime);

        createSchedule.setOnClickListener(new View.OnClickListener(){
            /**
             * Supposed to get data from text fields and send to previous activity
             * @param v The view being clicked
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(75,intent);
                finish();
            }
        });


    }
}
