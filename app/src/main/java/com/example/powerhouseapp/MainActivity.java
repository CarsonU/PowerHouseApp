package com.example.powerhouseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String serverIP = "10.0.0.28";
    PrintStream p;
    private ArrayList<outlet> outletList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect client to server
        new connect().execute();

        recyclerView = findViewById(R.id.outlets);
        outletList = new ArrayList<>();

        setOutletInfo();

        setRecyclerAdapter();

        //Floating action button to show new outlet pop up window
        FloatingActionButton newOutlet = (FloatingActionButton) findViewById(R.id.newOutlet);
        newOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open popup window to enter outlet information
                startActivityForResult(new Intent(MainActivity.this, NewOutlet.class), 1);
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

    private void setOutletInfo() {
        outletList.add(new outlet("Bedroom Lamp"));
        outletList.add(new outlet("Living Room Lamp"));
        outletList.add(new outlet("T.V."));
        outletList.add(new outlet("Air Conditioner"));
    }

    //Gets outlet information from popup window
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String[] info = data.getStringArrayExtra("info");
        new addOutlet().execute(info);
        new toggle().execute(info[0]);
    }

    //Connects client to server
    class connect extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Socket s  = new Socket(serverIP, 4999);
                InputStreamReader sc1  = new InputStreamReader(s.getInputStream());
                BufferedReader read = new BufferedReader(sc1);
                p = new PrintStream(s.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //Receives string array containing outlet name and ip and sends command to create the outlet
    class addOutlet extends AsyncTask<String[], Void, Void> {
        @Override
        protected Void doInBackground(String[]... params) {
            String outletName = params[0][0];
            String outletIP = params[0][1];
            String command = "outlets new " + outletName + " " + outletIP;
            p.println(command);
            return null;
        }
    }

    class outletOn extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            String command = "outlets on lamp";
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