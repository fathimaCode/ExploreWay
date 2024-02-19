package com.exploreway.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "ExploreWaySession";
    private static final String KEY_SELECTED_LOCATION = "selectedLocation";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USERID = "userid";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static SessionManager instance;

    private SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context.getApplicationContext());
        }
        return instance;
    }

    public void setSelectedLocation(String location) {
        editor.putString(KEY_SELECTED_LOCATION, location);
        editor.apply();
    }

    public String getSelectedLocation() {
        return sharedPreferences.getString(KEY_SELECTED_LOCATION, "");
    }

    public void setUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public void setUserId(String userId) {
        editor.putString(KEY_USERID, userId);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USERID, "");
    }
}
