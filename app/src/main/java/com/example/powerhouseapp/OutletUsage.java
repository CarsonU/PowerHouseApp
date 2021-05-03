package com.example.powerhouseapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class OutletUsage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outlet_usage);
        Intent intent = getIntent();
        String usage = intent.getStringExtra("todayUsage");
        TextView todaysUsage = (TextView) findViewById(R.id.txtTodayUsage);
        todaysUsage.setText(usage + "kWh");
    }
}
