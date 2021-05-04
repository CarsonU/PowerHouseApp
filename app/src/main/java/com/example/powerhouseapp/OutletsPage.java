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

        Log.v("Outlets Page","onCreate called");

        //Get printstream
        p = MainMenu.getP();
        //Get reader
        reader = MainMenu.getReader();

        recyclerView = findViewById(R.id.outlets);
        outletList = new ArrayList<>();
        new updateOutlets().execute();
        setRecyclerAdapter();

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

    //Update recyclerview
    private void setRecyclerAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(outletList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    //Gets outlet information from popup window
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Add outlet to client list
        Outlet outlet = (Outlet) data.getSerializableExtra("outlet");
        outletList.add(outlet);
        //Add outlet server side
        new addOutlet().execute(outlet);
        //Update recyclerview
        setRecyclerAdapter();
    }


    //Receives outlet and sends it to the server to be created
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
    }

    //Connects client to server
    class updateOutlets extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String command = "outlets list";
            p.println(command);
            try {
                String input = reader.readLine();
                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(input);

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
    }

    //Receives outlet and turns it on
    public static class outletOn extends AsyncTask<Outlet,Void,Void>{
        @Override
        protected Void doInBackground(Outlet... params) {
            Log.v("Task","Button on task called for " + params[0].getName());
            String command = "outlets on " + params[0].getName();
            p.println(command);
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
            return null;
        }
    }

    public static class outletUsage extends AsyncTask<Outlet, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Outlet... params) {
            ArrayList<String> result = new ArrayList<>();
            Log.v("Task","Button usage today task called for " + params[0].getName());
            try {
                String command = "usage today " + params[0].getName();
                p.println(command);
                result.add(reader.readLine());
                command = "usage avg " + params[0].getName();
                p.println(command);
                result.add(reader.readLine());
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
            return null;
        }
    }
}