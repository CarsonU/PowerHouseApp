package com.example.powerhouseapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * New zone pop up window activity
 */
public class NewZone extends AppCompatActivity {
    private ArrayList<Outlet> outletList;
    private RecyclerView outletsRecycler;

    /**
     * Called when the new zone window activity is started
     * @param savedInstanceState Contains the most recent data if the activity is being re-initialized
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newzonewindow);

        Log.v("NewZone.java","onCreate called");

        //Popup window size is 75% as big as main window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels*.75),(int) (dm.heightPixels*.75));

        //Get outlet list and update recyclerview
        outletsRecycler = findViewById(R.id.outlets);
        outletList = OutletsPage.getOutletList();
        Log.v("outlets list",String.valueOf(outletList));
        setSelectorAdapter();

        //Create textview and button
        EditText zoneName = (EditText) findViewById(R.id.editTextZoneName);
        Button createZone = (Button) findViewById(R.id.btnCreateZone);
        //Create zone button
        createZone.setOnClickListener(new View.OnClickListener(){
            /**
             * Gets the zone name from the text field and the list of checked outlets when the
             * create zone button is clicked, and then sends it to the previous activity
             * @param v The view that is being clicked
             */
            @Override
            public void onClick(View v) {
                //Create zone
                Zone zone = new Zone(zoneName.getText().toString(), OutletSelectorAdapter.getOutletsToAdd());
                //Send it to the previous activity
                Intent intent = new Intent();
                intent.putExtra("zone", (Serializable) zone);
                setResult(50,intent);
                finish();
            }
        });
    }

    /**
     * Updates the recyclerview with the current list of outlets
     */
    private void setSelectorAdapter(){
        OutletSelectorAdapter adapter = new OutletSelectorAdapter(outletList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        outletsRecycler.setLayoutManager(layoutManager);
        outletsRecycler.addItemDecoration(new DividerItemDecoration(outletsRecycler.getContext(), DividerItemDecoration.VERTICAL));
        outletsRecycler.setItemAnimator(new DefaultItemAnimator());
        outletsRecycler.setAdapter(adapter);
    }
}
