package com.example.powerhouseapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private ArrayList<Outlet> outletList;

    public recyclerAdapter(ArrayList<Outlet> outletList){
        this.outletList = outletList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public String name;
        public int position;
        private TextView outletTxt;
        private final Context context;

        public MyViewHolder(final View view){
            super(view);
            outletTxt = view.findViewById(R.id.textOutlet);
            context = view.getContext();

            itemView.setOnClickListener((v) -> {
                Log.v("View","Attempt to open usage");
                Intent intent = new Intent(context, OutletUsage.class);
                context.startActivity(intent);
            });

            //Turn the outlet on
            itemView.findViewById(R.id.btnOn).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    new OutletsPage.outletOn().execute(outletList.get(position));
                }
            });

            //Turn the outlet off
            itemView.findViewById(R.id.btnOff).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                   new OutletsPage.outletOff().execute(outletList.get(position));
                }
            });
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.outletlist, parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        Outlet outlet = outletList.get(position);
        holder.outletTxt.setText(outlet.getName());
        holder.name = outlet.getName();
        holder.position = position;

    }

    @Override
    public int getItemCount() {
        return outletList.size();
    }
}
