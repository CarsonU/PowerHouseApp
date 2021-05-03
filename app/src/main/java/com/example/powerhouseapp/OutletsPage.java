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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class OutletsPage extends AppCompatActivity {
    static PrintStream p;
    static BufferedReader reader;
    private ArrayList<Outlet> outletList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_page);

        //Get printstream
        p = MainMenu.getP();
        //Get reader
        reader = MainMenu.getReader();

        recyclerView = findViewById(R.id.outlets);
        outletList = new ArrayList<>();


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

    public static class outletUsageToday extends AsyncTask<Outlet, Void, String> {
        @Override
        protected String doInBackground(Outlet... params) {
            Log.v("Task","Button usage today task called for " + params[0].getName());
            String command = "usage today " + params[0].getName();
            p.println(command);
            try {
                return reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
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