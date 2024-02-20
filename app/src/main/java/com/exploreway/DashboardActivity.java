package com.exploreway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.exploreway.ui.CalendarFragment;
import com.exploreway.ui.FavouriteFragment;
import com.exploreway.ui.ProfileFragment;
import com.exploreway.ui.SearchFragment;
import com.exploreway.ui.home.HomeFragment;
import com.exploreway.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        String userid = SessionManager.getInstance(this).getUserId();
        String username = SessionManager.getInstance(this).getUsername();
        Log.d("line 19",userid);
        Log.d("line 21",username);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // Load the initial fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SearchFragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if(id == R.id.home){
                selectedFragment = new HomeFragment();

            }
            else if(id == R.id.calender){
                selectedFragment = new CalendarFragment();

            }
            else if(id == R.id.Favourite){
                selectedFragment = new FavouriteFragment();
            }
            else if(id == R.id.Search){
                selectedFragment = new SearchFragment();
            }
            else if(id == R.id.Profile){
                selectedFragment = new ProfileFragment();
            }



            // Replace the current fragment with the selected one
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();
                return true;
            }
            return false;
        }
    };
}