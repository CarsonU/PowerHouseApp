package com.example.powerhouseapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter for the recyclerview displaying the list of outlets in the new zone pop up window activity
 */
public class OutletSelectorAdapter extends RecyclerView.Adapter<OutletSelectorAdapter.MyViewHolder> {
    private ArrayList<Outlet> outletList;
    private static ArrayList<Outlet> outletsToAdd = new ArrayList<>();

    /**
     * Adapter constructor
     * @param outletList The list of outlets to display
     */
    public OutletSelectorAdapter(ArrayList<Outlet> outletList) {
        this.outletList = outletList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public int position;
        private CheckBox outletCheck;

        public MyViewHolder(final View view) {
            super(view);
            outletCheck = view.findViewById(R.id.checkOutlet);
        }
    }

    /**
     * Called when the recyclerview needs a new ViewHolder
     * @param parent  the recyclerview
     * @param viewType the type of view
     * @return the new view holder
     */
    @NonNull
    @Override
    public OutletSelectorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.outletselector, parent,false);
        return new OutletSelectorAdapter.MyViewHolder(itemView);
    }

    /**
     * Called by the recyclerview to display data at a specific position
     * @param holder The specific row of the reyclerview being updated
     * @param position The position of the row within the recyclerview
     */
    @Override
    public void onBindViewHolder(@NonNull OutletSelectorAdapter.MyViewHolder holder, int position) {
        //Store and set outlet name
        Outlet outlet = outletList.get(position);
        Log.v("Outlet name",outlet.getName());
        holder.outletCheck.setText(outlet.getName());
        holder.position = position;

        holder.outletCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * Adds or removes and outlet from the list when the state changes
             * @param buttonView the compund buttonview (checkbox) that has changed state
             * @param isChecked the new state of the checkbox
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Adds outlet to list when it is checked
                if (isChecked == true){
                    Log.v("Outlet checked:", outlet.getName());
                    outletsToAdd.add(outlet);
                    Log.v("List of checked outlets", String.valueOf(outletsToAdd));
                //Removes outlet from list when it is unchecked
                } else if (isChecked == false) {
                    Log.v("Outlet unchecked:",outlet.getName());
                    outletsToAdd.remove(outlet);
                    Log.v("List of checked outlets", String.valueOf(outletsToAdd));
                }
            }
        });
    }

    /**
     * Returns the list of checked outlets
     * @return the list of checked outlets
     */
    public static ArrayList<Outlet> getOutletsToAdd() {
        return outletsToAdd;
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
