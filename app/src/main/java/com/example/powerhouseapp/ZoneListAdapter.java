package com.example.powerhouseapp;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Adapter for the reyclerview displaying the list of zones in the zones page activity
 */
public class ZoneListAdapter extends RecyclerView.Adapter<ZoneListAdapter.MyViewHolder> {
    private ArrayList<Zone> zoneList;

    /**
     * Adapter constructor
     * @param zoneList The list of zones to display
     */
    public ZoneListAdapter(ArrayList<Zone> zoneList) {
        this.zoneList = zoneList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView zoneName;
        public int position;

        public MyViewHolder(final View view) {
            super(view);
            zoneName = view.findViewById(R.id.txtZoneName);

            itemView.findViewById(R.id.btnOn).setOnClickListener(new View.OnClickListener() {
                /**
                 * Turns the zone on when the on button is clicked
                 * @param v The view being clicked
                 */
                @Override
                public void onClick(View v) {
                    new ZonesPage.zoneOn().execute(zoneList.get(position));
                }
            });

            itemView.findViewById(R.id.btnOff).setOnClickListener(new View.OnClickListener() {
                /**
                 * Turns the zone off when the off button is clicked
                 * @param v The view being clicked
                 */
                @Override
                public void onClick(View v) {
                    new ZonesPage.zoneOff().execute(zoneList.get(position));
                }
            });
        }
    }


    /**
     * Called when the recyclerview needs a new ViewHolder
     * @param parent the recyclerview
     * @param viewType the view type
     * @return
     */
    @NonNull
    @Override
    public ZoneListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.zonelist, parent,false);
        return new ZoneListAdapter.MyViewHolder(itemView);
    }

    /**
     * Called by the recyclerview to display data at a specific location
     * @param holder the specific row of the recyclerview
     * @param position the position of the row within the reyclerview
     */
    @Override
    public void onBindViewHolder(@NonNull ZoneListAdapter.MyViewHolder holder, int position) {
        Zone zone = zoneList.get(position);
        Log.v("Zone name", zone.getName());
        holder.zoneName.setText(zone.getName());
        holder.position = position;
    }

    /**
     * Returns the amount of zones, or the amount of rows in the reyclerview
     * @return the amount of zones
     */
    @Override
    public int getItemCount() {
        return zoneList.size();
    }
}
