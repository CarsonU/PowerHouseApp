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

/**
 * Adapter for the recyclerview displaying the list of outlets in the outlets page activity
 */
public class OutletListAdapter extends RecyclerView.Adapter<OutletListAdapter.MyViewHolder> {
    private ArrayList<Outlet> outletList;

    /**
     * Adapter constructor
     * @param outletList The list of outlets to display
     */
    public OutletListAdapter(ArrayList<Outlet> outletList){
        this.outletList = outletList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        //public String name;
        public int position;
        private TextView outletTxt;
        private final Context context;


        public MyViewHolder(final View view){
            super(view);
            outletTxt = view.findViewById(R.id.textOutlet);
            context = view.getContext();

            //When the outlet text is clicked, not the buttons
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

            itemView.findViewById(R.id.btnOn).setOnClickListener(new View.OnClickListener(){
                /**
                 * Turns the outlet on when the on button is clicked
                 * @param v The view being clicked
                 */
                @Override
                public void onClick(View v) {
                    new OutletsPage.outletOn().execute(outletList.get(position));
                }
            });

            itemView.findViewById(R.id.btnOff).setOnClickListener(new View.OnClickListener(){
                /**
                 * Turns the outlet off when the off button is clicked
                 * @param v The view being clicked
                 */
                @Override
                public void onClick(View v) {
                   new OutletsPage.outletOff().execute(outletList.get(position));
                }
            });
        }
    }

    /**
     * Called when the recyclerview needs a new ViewHolder
     * @param parent the recyclerview
     * @param viewType the type of view
     * @return the new view holder
     */
    @NonNull
    @Override
    public OutletListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.outletlist, parent,false);
        return new MyViewHolder(itemView);
    }

    /**
     * Called by the recyclerview to display data at a specific position
     * @param holder The specific row of the recyclerview being updated
     * @param position The position of the row within the reyclerview
     */
    @Override
    public void onBindViewHolder(@NonNull OutletListAdapter.MyViewHolder holder, int position) {
        //Store and set outlet name
        Outlet outlet = outletList.get(position);
        holder.outletTxt.setText(outlet.getName());
        //holder.name = outlet.getName();
        holder.position = position;

    }

    /**
     * Returns the amount of outlets, or the amount of rows in the recyclerview
     * @return the amount of outlets
     */
    @Override
    public int getItemCount() {
        return outletList.size();
    }
}
