package com.exploreway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.exploreway.utils.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Spinner dropdownBtn = findViewById(R.id.dropdownBtn);
        String[] items = new String[]{"Choose your location","Coquitlam", "Burnaby"}; // Dropdown items
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownBtn.setAdapter(adapter);


        dropdownBtn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String)parent.getItemAtPosition(position);
                if(!selectedItem.equals("Choose your location")){
                    SessionManager.getInstance(SplashScreenActivity.this).setSelectedLocation(selectedItem);
                    Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);

                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}