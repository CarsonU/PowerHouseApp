package com.example.powerhouseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class OutletsPage extends AppCompatActivity {
    static PrintStream p;
    static BufferedReader reader;
    private ArrayList<Outlet> outletList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_page);
        Log.v("OutletsPage.java","onCreate called");

        //Get printstream
        p = MainMenu.getP();
        //Get reader
        reader = MainMenu.getReader();

        recyclerView = findViewById(R.id.outlets);
        outletList = new ArrayList<>();
        //Update outlets and recyclerview
        new syncOutlets().execute();

        //Floating action button to show new outlet pop up window
        FloatingActionButton newOutlet = (FloatingActionButton) findViewById(R.id.newOutlet);
        newOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open popup window to enter outlet information
                startActivityForResult(new Intent(OutletsPage.this, NewOutlet.class), 1);
            }
        });

    }

    //Gets outlet information from popup window
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Result code for newoutlet page = 1
        if (resultCode == 1){
            //Add outlet to client list
            Outlet outlet = (Outlet) data.getSerializableExtra("outlet");
            //Add outlet server side and update recyclerview
            new addOutlet().execute(outlet);
        }
    }

    //Update recyclerview
    private void setRecyclerAdapter() {
        Log.v("outlets list", String.valueOf(outletList));

        recyclerAdapter adapter = new recyclerAdapter(outletList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    //Creates and outlet server side, and then updates the client
    class addOutlet extends AsyncTask<Outlet, Void, Void> {
        @Override
        protected Void doInBackground(Outlet... params) {
            String outletName = params[0].getName();
            String outletIP = params[0].getIp();
            String command = "outlets new " + outletName + " " + outletIP;
            p.println(command);

            try {
                reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        //After outlet is added server side, update client
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new syncOutlets().execute();
        }
    }

    //Syncs client outlets with server outlets and updates recyclerview
    class syncOutlets extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            Log.v("Update Outlets","Task Called");

            //Send command to server that returns a list of outlet names and their ip in json
            String command = "outlets list";
            p.println(command);
            try {
                //Receive and parse json string
                String input = reader.readLine();
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(input);

                //Clear current outlets
                outletList.clear();
                //Loop through list of outlets, saving their name and ip to the client
                for(Iterator iterator = json.keySet().iterator();iterator.hasNext();){
                    String outletName = (String) iterator.next();
                    String outletIP = json.get(outletName).toString();
                    Outlet outlet = new Outlet(outletName, outletIP);
                    outletList.add(outlet);
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        //After the client outlets are synced with server outlets, update the recyclerview
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setRecyclerAdapter();
        }
    }

    //Receives outlet and turns it on
    public static class outletOn extends AsyncTask<Outlet,Void,Void>{
        @Override
        protected Void doInBackground(Outlet... params) {
            Log.v("Task","Button on task called for " + params[0].getName());
            String command = "outlets on " + params[0].getName();
            p.println(command);

            try {
                reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    //Receives outlet and turns it off
    public static class outletOff extends AsyncTask<Outlet,Void,Void>{
        @Override
        protected Void doInBackground(Outlet... params) {
            Log.v("Task","Button off task called for " + params[0].getName());
            String command = "outlets off " + params[0].getName();
            p.println(command);
            try {
                reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class outletUsage extends AsyncTask<Outlet, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Outlet... params) {
            //Stores usage values in kWh. {Today, Daily Average, Total}
            ArrayList<String> result = new ArrayList<>();
            Log.v("Task","Button usage today task called for " + params[0].getName());

            try {
                //Get usage for today
                String command = "usage today " + params[0].getName();
                p.println(command);
                result.add(reader.readLine());
                //Get daily average usage
                command = "usage avg " + params[0].getName();
                p.println(command);
                result.add(reader.readLine());
                //Get total usage
                command = "usage total " + params[0].getName();
                p.println(command);
                result.add(reader.readLine());
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        //Returns arraylist after it calculates usage
        protected void onPostExecute(ArrayList<String> s) {
            super.onPostExecute(s);
        }
    }

    //Receives outlet and toggles it
    class toggle extends AsyncTask<Outlet,Void,Void>{
        @Override
        protected Void doInBackground(Outlet... params) {
            String command = "outlets toggle " + params[0].getName();
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