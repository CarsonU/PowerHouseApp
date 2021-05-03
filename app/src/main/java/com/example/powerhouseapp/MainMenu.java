package com.example.powerhouseapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;

public class MainMenu extends AppCompatActivity {
    String serverIP = "10.0.0.28";
    //Used for sending commands to the server
    static PrintStream p;
    static BufferedReader reader;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_main);

        //Connect client to server
        new connect().execute();

        Button gotoOutletPage = (Button) findViewById(R.id.btnOutletspage);
        gotoOutletPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open outlet page
                Intent intent = new Intent(MainMenu.this, OutletsPage.class);
                startActivity(intent);
            }
            });

        Button goToZonePage = (Button) findViewById(R.id.btnZonePage);
        goToZonePage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, Zones.class);
                startActivity(intent);
            }
        });
    }

    //Printstream getter
    public static PrintStream getP() {
        return p;
    }
    //Reader getter
    public static BufferedReader getReader() {
        return reader;
    }

    //Connects client to server
    class connect extends AsyncTask<Void,Void,Void> {
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

}


