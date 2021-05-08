package com.example.powerhouseapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Main Menu Activity
 */
public class MainMenu extends AppCompatActivity {
    String serverIP = "10.0.0.28";

    //Used for sending commands to the server
    static PrintStream p;
    static BufferedReader reader;

    /**
     * Called when the main menu page activity is started
     * @param savedInstanceState Contains the most recent data if the activity is being re-initialized
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_main);
        Log.v("MainMenu.java","onCreate called");

        //Connect client to server
        new connect().execute();

        //Button that opens outlet page
        Button gotoOutletPage = (Button) findViewById(R.id.btnOutletspage);
        gotoOutletPage.setOnClickListener(new View.OnClickListener() {
            /** Opens the outlet page when the button is clicked
             * @param v The view that is clicked
             */
            @Override
            public void onClick(View v) {
                //Open outlet page
                Intent intent = new Intent(MainMenu.this, OutletsPage.class);
                startActivity(intent);
            }
            });

        //Button that opens zone page
        Button goToZonePage = (Button) findViewById(R.id.btnZonePage);
        goToZonePage.setOnClickListener(new View.OnClickListener(){
            /**
             * Opens the zone page when the button is clicked
             * @param v The view that is clicked
             */
            @Override
            public void onClick(View v) {
                //Open zone page
                Intent intent = new Intent(MainMenu.this, ZonesPage.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Connects the client to the server
     */
    class connect extends AsyncTask<Void,Void,Void> {
        /**
         * Connects client to the server
         */
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Socket s  = new Socket(serverIP, 4999);
                InputStreamReader sc1  = new InputStreamReader(s.getInputStream());
                reader = new BufferedReader(sc1);
                p = new PrintStream(s.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    /**
     * Returns the printstream, used for sending commands to the server
     * @return The current prinstream
     */
    public static PrintStream getP() {
        return p;
    }


    /**
     * Returns the bufferedreader, used for reading message from the server
     * @return The current bufferedreader
     */
    public static BufferedReader getReader() {
        return reader;
    }

}


