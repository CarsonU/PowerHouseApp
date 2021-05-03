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
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class OutletsPage extends AppCompatActivity {
    String serverIP;
    static PrintStream p;
    private ArrayList<outlet> outletList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_page);

        p = MainMenu.getP();

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
        String[] info = data.getStringArrayExtra("info");
        new addOutlet().execute(info);
        outletList.add(new outlet(info[0],info[1]));
        setRecyclerAdapter();
    }




    //Receives string array containing outlet name and ip and sends command to create the outlet
    class addOutlet extends AsyncTask<String[], Void, Void> {
        @Override
        protected Void doInBackground(String[]... params) {
            String outletName = params[0][0];
            System.out.println(outletName);
            String outletIP = params[0][1];
            System.out.println(outletIP);
            String command = "outlets new " + outletName + " " + outletIP;
            p.println(command);
            return null;
        }
    }

    public static class outletOn extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... params) {
            Log.v("Task","Button on task called for " + params[0]);
            String command = "outlets on " + params[0];
            p.println(command);
            return null;
        }
    }

    public static class outletOff extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... params) {
            Log.v("Task","Button off task called for " + params[0]);
            String command = "outlets off " + params[0];
            p.println(command);
            return null;
        }
    }

    //Receives outlet name and toggles the outlet
    class toggle extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... params) {
            String outletname = params[0];
            String command = "outlets toggle " + outletname;
            p.println(command);
            return null;
        }
    }
}