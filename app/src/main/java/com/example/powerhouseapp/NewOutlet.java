package com.example.powerhouseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class NewOutlet extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);

        //Popup window size is 75% as big as main window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout((int) (dm.widthPixels*.75),(int) (dm.heightPixels*.75));

        //Text fields and button
        Button createOutlet = (Button) findViewById(R.id.btnCreateOutlet);
        EditText editName = (EditText) findViewById(R.id.editTextOutletName);
        EditText editIP = (EditText) findViewById(R.id.editTextOutletIPAddress);

        //When the button is clicked, get data and send it to main activity
        createOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] data = {editName.getText().toString(), editIP.getText().toString()};
                Intent intent = new Intent();
                intent.putExtra("info",data);
                setResult(100, intent);
                finish();
            }
        });


    }
}
