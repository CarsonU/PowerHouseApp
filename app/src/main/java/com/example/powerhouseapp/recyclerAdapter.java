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
import java.util.concurrent.ExecutionException;

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
                ArrayList<String> usage = new ArrayList<>();

                //Calls text to get data usage for the outlet
                try {
                    usage = new OutletsPage.outletUsage().execute(outletList.get(position)).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Sends usage data to the usage page and then opens it
                intent.putExtra("usage",usage);
                context.startActivity(intent);
            });

            //Turn the outlet on button
            itemView.findViewById(R.id.btnOn).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    new OutletsPage.outletOn().execute(outletList.get(position));
                }
            });

            //Turn the outlet off button
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
        //Store and set outlet name
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
