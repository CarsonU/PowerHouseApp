package com.example.powerhouseapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private ArrayList<outlet> outletList;

    public recyclerAdapter(ArrayList<outlet> outletList){
        this.outletList = outletList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public String name;
        public int position;
        private TextView outletTxt;

        public MyViewHolder(final View view){
            super(view);
            outletTxt = view.findViewById(R.id.textOutlet);

            itemView.findViewById(R.id.btnOn).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.v("Button","Button On Clicked for " + outletList.get(position).getName());
                    new OutletsPage.outletOn().execute(outletList.get(position).getName());
                }
            });

            itemView.findViewById(R.id.btnOff).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.v("Button","Button Off Clicked for " + outletList.get(position).getName());
                   new OutletsPage.outletOff().execute(outletList.get(position).getName());
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
        outlet outlet = outletList.get(position);
        holder.outletTxt.setText(outlet.getName());
        holder.name = outlet.getName();
        holder.position = position;

    }

    @Override
    public int getItemCount() {
        return outletList.size();
    }
}
