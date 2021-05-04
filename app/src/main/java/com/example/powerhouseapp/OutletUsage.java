package com.example.powerhouseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OutletUsage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_usage);

        Intent intent = getIntent();
        ArrayList<String> usage = new ArrayList<>();
        usage = intent.getStringArrayListExtra("usage");

        TextView todaysUsage = (TextView) findViewById(R.id.txtTodayUsage);
        todaysUsage.setText(usage.get(0) + "kWh");
        TextView avgUsage = (TextView) findViewById(R.id.txtAvgUsage);
        avgUsage.setText(usage.get(1) + "kWh");
        TextView totalUsage = (TextView) findViewById(R.id.txtTotalUsage);
        totalUsage.setText(usage.get(2) + "kWh");
    }
}
