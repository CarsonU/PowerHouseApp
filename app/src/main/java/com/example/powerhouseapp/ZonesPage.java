package com.example.powerhouseapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class ZonesPage extends AppCompatActivity {
    static PrintStream p;
    static BufferedReader reader;
    private ArrayList<Zone> zonelist;
    private RecyclerView zonesRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zone_page);

        Log.v("Zones.java", "onCreate called");

        //Get printstream
        p = MainMenu.getP();
        //Get reader
        reader = MainMenu.getReader();

        //Update list of zones and recyclerview
        zonesRecycler = findViewById(R.id.zones);
        zonelist = new ArrayList<>();
        new syncZones().execute();

        //Floating action button to add new zone
        FloatingActionButton newZone = (FloatingActionButton) findViewById(R.id.fabNewZone);
        newZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open popup window to enter zone information
                startActivityForResult(new Intent(ZonesPage.this, NewZone.class), 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Returns information from new zone window and adds the zone
        if (resultCode == 50) {
            Zone zone = (Zone) data.getSerializableExtra("zone");
            new addZone().execute(zone);
        }
    }

    //Creates and updates zone list recycervliew
    private void setZoneAdapter() {
        ZoneListAdapter adapter = new ZoneListAdapter(zonelist);
        Log.v("Adapter zones", String.valueOf(zonelist));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        zonesRecycler.setLayoutManager(layoutManager);
        zonesRecycler.addItemDecoration(new DividerItemDecoration(zonesRecycler.getContext(), DividerItemDecoration.VERTICAL));
        zonesRecycler.setItemAnimator(new DefaultItemAnimator());
        zonesRecycler.setAdapter(adapter);
    }

    //Creates a zone server side, and then updates the client
    class addZone extends AsyncTask<Zone, Void, Void> {
        @Override
        protected Void doInBackground(Zone... params) {
            //Create the zone server side
            String zoneName = params[0].getName();
            String command = "zone new " + zoneName;
            p.println(command);
            try {
                Log.v("New Zone Message", reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Add each outlet to the zone server side
            ArrayList<Outlet> outlets = params[0].getOutlets();
            for (Outlet outlet : outlets){
                command = "zone add " + zoneName + " " + outlet.getName();
                p.println(command);
                try {
                    Log.v("Added to Zone Message",reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        //After zone is added server side, update client
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new syncZones().execute();
        }
    }

    //Syncs zones on the client with the server
    class syncZones extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Log.v("Sync Zones", "Task Called");

            //Send command to server that returns a list of outlet names and their ip in json
            String command = "zone list";
            p.println(command);
            try {
                //Receive and parse json string
                String input = reader.readLine();
                Log.v("Zone input", input);
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(input);

                //Clear current zones
                zonelist.clear();
                //Iterate through each zone
                for (Object zone : json.keySet()) {
                    //Get zone name
                    String zoneName = (String) zone;
                    //Iterate through each outlet within the current zone
                    ArrayList<Outlet> outlets = new ArrayList<>();
                    JSONObject keyVal = (JSONObject) json.get(zoneName);
                    for (Object key : keyVal.keySet()) {
                        Outlet outlet = new Outlet(key.toString(), keyVal.get(key.toString()).toString());
                        outlets.add(outlet);
                    }
                    //Once all outlets are added, create the zone
                    Zone newZone = new Zone(zoneName, outlets);
                    Log.v("Zone Name",newZone.getName());
                    //Save zone client side
                    zonelist.add(newZone);
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        //Update recyclerview after zones are synced
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setZoneAdapter();
        }
    }

    //Receives zone and turns it on
    public static class zoneOn extends AsyncTask<Zone,Void,Void>{
        @Override
        protected Void doInBackground(Zone... params) {
            Log.v("Task","Button on task called for " + params[0].getName());
            //Create and send command to server to turn on zone
            String command = "zone on " + params[0].getName();
            p.println(command);
            try {
                reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //Receives zone and turns it off
    public static class zoneOff extends AsyncTask<Zone,Void,Void>{
        @Override
        protected Void doInBackground(Zone... params) {
            Log.v("Task","Button on task called for " + params[0].getName());
            //Create and send command to server to turn off zone
            String command = "zone off " + params[0].getName();
            p.println(command);
            try {
                reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
