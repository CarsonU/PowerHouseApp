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

public class ZoneListAdapter extends RecyclerView.Adapter<ZoneListAdapter.MyViewHolder> {
    private ArrayList<Zone> zoneList;

    public ZoneListAdapter(ArrayList<Zone> zoneList) {
        this.zoneList = zoneList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView zoneName;
        public int position;

        public MyViewHolder(final View view) {
            super(view);
            zoneName = view.findViewById(R.id.txtZoneName);

            //Turn the zone on button
            itemView.findViewById(R.id.btnOn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ZonesPage.zoneOn().execute(zoneList.get(position));
                }
            });

            //Turn the zone on button
            itemView.findViewById(R.id.btnOff).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ZonesPage.zoneOff().execute(zoneList.get(position));
                }
            });
        }
    }


    @NonNull
    @Override
    public ZoneListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.zonelist, parent,false);
        return new ZoneListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ZoneListAdapter.MyViewHolder holder, int position) {
        Zone zone = zoneList.get(position);
        Log.v("Zone name", zone.getName());
        holder.zoneName.setText(zone.getName());
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return zoneList.size();
    }
}
