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

public class OutletSelectorAdapter extends RecyclerView.Adapter<OutletSelectorAdapter.MyViewHolder> {
    private ArrayList<Outlet> outletList;
    private static ArrayList<Outlet> outletsToAdd = new ArrayList<>();

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

    @NonNull
    @Override
    public OutletSelectorAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.outletselector, parent,false);
        return new OutletSelectorAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OutletSelectorAdapter.MyViewHolder holder, int position) {
        //Store and set outlet name
        Outlet outlet = outletList.get(position);
        Log.v("Outlet name",outlet.getName());
        holder.outletCheck.setText(outlet.getName());
        holder.position = position;

        //Adds or removes outlet from list when its status changes
        holder.outletCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

    public static ArrayList<Outlet> getOutletsToAdd() {
        return outletsToAdd;
    }

    @Override
    public int getItemCount() {
        return outletList.size();
    }
}
