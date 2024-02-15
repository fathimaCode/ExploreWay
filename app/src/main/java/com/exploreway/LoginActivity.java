package com.exploreway;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.exploreway.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String selectedLocation = SessionManager.getInstance(this).getSelectedLocation();
        Log.d("line 17",selectedLocation);
    }
}