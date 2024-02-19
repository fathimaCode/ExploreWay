package com.exploreway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.exploreway.utils.SessionManager;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        String userid = SessionManager.getInstance(this).getUserId();
        String username = SessionManager.getInstance(this).getUsername();
        Log.d("line 19",userid);
        Log.d("line 21",username);



    }
}