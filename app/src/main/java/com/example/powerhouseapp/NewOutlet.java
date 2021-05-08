package com.example.powerhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import java.io.Serializable;

/**
 * New Outlet Pop Up Window Activity
 */
public class NewOutlet extends Activity {
    /**
     * Called when the new outlet window activity is started
     * @param savedInstanceState Contains the most recent data if the activity is being re-initialized
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newoutletwindow);

        Log.v("NewOutlet.java","onCreate called");

        //Popup window size is 75% as big as main window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels*.75),(int) (dm.heightPixels*.75));

        //Text fields and button
        Button createOutlet = (Button) findViewById(R.id.btnCreateOutlet);
        EditText editName = (EditText) findViewById(R.id.editTextOutletName);
        EditText editIP = (EditText) findViewById(R.id.editTextOutletIPAddress);

        //When the button is clicked, get data and send it to outlets page
        createOutlet.setOnClickListener(new View.OnClickListener() {
            /**
             * Gets the data from the text fields and sends the data to previous activity when the
             * create outlet button is clicked
             * @param v The view that is being clicked
             */
            @Override
            public void onClick(View v) {
                //Create outlet
                Outlet outlet = new Outlet(editName.getText().toString(),editIP.getText().toString());
                //Send outlet to preivous activity
                Intent intent = new Intent();
                intent.putExtra("outlet", (Serializable) outlet);
                setResult(100, intent);
                finish();
            }
        });


    }
}
