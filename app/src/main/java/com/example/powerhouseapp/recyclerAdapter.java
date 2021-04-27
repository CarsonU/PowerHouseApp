package com.example.powerhouseapp;

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
        private TextView outletTxt;

        public MyViewHolder(final View view){
            super(view);
            outletTxt = view.findViewById(R.id.textOutlet);
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
        String name = outletList.get(position).getName();
        holder.outletTxt.setText(name);
    }

    @Override
    public int getItemCount() {
        return outletList.size();
    }
}
