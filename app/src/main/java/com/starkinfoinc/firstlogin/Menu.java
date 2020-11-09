package com.starkinfoinc.firstlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    Button btnSettings,btnReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
       //Getting values from Layout Objects
        btnSettings = (Button)findViewById(R.id.btnSettings);
        btnReports = (Button)findViewById(R.id.btnReports);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Menu.this, "Under Progress", Toast.LENGTH_SHORT).show();

            }
        });

        btnReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(Menu.this,Reports.class);
                startActivity(intent);

            }







        });



    }
}