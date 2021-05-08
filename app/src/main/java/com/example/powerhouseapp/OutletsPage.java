package com.example.powerhouseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.google.android.material.snackbar.Snackbar;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Outlets page activity
 */
public class OutletsPage extends AppCompatActivity {
    static PrintStream p;
    static BufferedReader reader;
    private static ArrayList<Outlet> outletList;
    private RecyclerView outletsRecycler;
    static ConstraintLayout mainLayout;

    /**
     * Called when the outlets page activity is started
     * @param savedInstanceState Contains the most recent data if the activity is being re-initialized
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_page);
        mainLayout = findViewById(R.id.mainLayout);

        Log.v("OutletsPage.java","onCreate called");

        //Get printstream
        p = MainMenu.getP();
        //Get reader
        reader = MainMenu.getReader();

        //Update list of outlets and recyclerview
        outletsRecycler = findViewById(R.id.outlets);
        outletList = new ArrayList<>();
        new syncOutlets().execute();

        FloatingActionButton newOutlet = (FloatingActionButton) findViewById(R.id.newOutlet);
        newOutlet.setOnClickListener(new View.OnClickListener() {
            /**
             * Opens the popup window to add the new outlet when the floating action button is clicked
             * @param v The view that was clicked
             */
            @Override
            public void onClick(View v) {
                //Open popup window to enter outlet information
                startActivityForResult(new Intent(OutletsPage.this, NewOutlet.class), 1);
            }
        });
    }

    /**
     * Waits for popup window to finish then adds the outlet
     * @param requestCode Used to identify this activity
     * @param resultCode Used to identify the new outlet activity
     * @param data The outlet data from the popup window
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Result code for newoutlet page = 100
        if (resultCode == 100){
            //Add outlet to client list
            Outlet outlet = (Outlet) data.getSerializableExtra("outlet");
            //Add outlet server side and update recyclerview
            new addOutlet().execute(outlet);
        }
    }

    /**
     * Updates the recyclerview
     */
    private void setRecyclerAdapter() {
        Log.v("outlets list", String.valueOf(outletList));

        OutletListAdapter adapter = new OutletListAdapter(outletList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        outletsRecycler.setLayoutManager(layoutManager);
        outletsRecycler.addItemDecoration(new DividerItemDecoration(outletsRecycler.getContext(), DividerItemDecoration.VERTICAL));
        outletsRecycler.setItemAnimator(new DefaultItemAnimator());
        outletsRecycler.setAdapter(adapter);
    }

    /**
     * Shows a popup snackbar with a message
     * @param message This message to be displayed
     */
    private static void showSnackbar(String message){
        Snackbar.make(mainLayout, message, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Returns the current list of outlets
     * @return the current list of outlets
     */
    public static ArrayList<Outlet> getOutletList() {
        return outletList;
    }


    /**
     * Adds an outlet server side, and then updates the client
     */
    class addOutlet extends AsyncTask<Outlet, Void, Void> {
        /**
         * Adds the given outlet server side
         * @param params The outlet to be added server side
         */
        @Override
        protected Void doInBackground(Outlet... params) {
            String outletName = params[0].getName();
            String outletIP = params[0].getIp();
            Log.v("Outlet",outletName + " " + outletIP);
            String command = "outlets new " + outletName + " " + outletIP;
            p.println(command);
            try {
              showSnackbar(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Updates client side outlets with the server, after the outlet
         * is added
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new syncOutlets().execute();
        }
    }


    /**
     * Syncs the client outlets with the server and then updates the
     * recyclerview
     */
    class syncOutlets extends AsyncTask<Void,Void,Void> {
        /**
         * Syncs client outlets with the server
         */
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

        /**
         * Updates the recyclerview after the outlets are synced
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setRecyclerAdapter();
        }
    }

    /**
     * Turns the given outlet on
     */
    public static class outletOn extends AsyncTask<Outlet,Void,Void>{
        /**
         * Turn on the outlet
         * @param params Outlet to turn on
         */
        @Override
        protected Void doInBackground(Outlet... params) {
            Log.v("Task","Button on task called for " + params[0].getName());
            String command = "outlets on " + params[0].getName();
            p.println(command);

            //Display message from server
            try {
                showSnackbar(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    /**
     * Turns the given outlet off
     */
    public static class outletOff extends AsyncTask<Outlet,Void,Void>{
        /**
         * Turn off the outlet
         * @param params Outlet to turn off
         */
        @Override
        protected Void doInBackground(Outlet... params) {
            Log.v("Task","Button off task called for " + params[0].getName());
            String command = "outlets off " + params[0].getName();
            p.println(command);

            //Display message from server
            try {
                showSnackbar(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Updates and returns the outlet usage for the given outlet
     */
    public static class outletUsage extends AsyncTask<Outlet, Void, ArrayList<String>> {
        /**
         * Receives and outlet and returns the power usage of the outlet for today,
         * daily average and total usage.
         * @param params Find the usage of this outlet
         * @return The power usage of the outlet. Returns a string
         * arraylist {Today, Daily Average, Total}
         */
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

        /**
         * Returns the arraylist containing the outlet's power usage, after it is finished
         * calculating
         * @param s The arraylist {Today, Daily Average, Total} of the outlets power
         *          usage information
         */
        @Override
        protected void onPostExecute(ArrayList<String> s) {
            super.onPostExecute(s);
        }
    }

    //NOT USED
    //Receives outlet and toggles it
    class toggle extends AsyncTask<Outlet,Void,Void>{
        @Override
        protected Void doInBackground(Outlet... params) {
            String command = "outlets toggle " + params[0].getName();
            p.println(command);

            //Display message from server
            try {
                showSnackbar(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}