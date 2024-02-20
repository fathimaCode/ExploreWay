package com.exploreway.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exploreway.R;
import com.exploreway.utils.CosineSimilarity;
import com.exploreway.utils.SessionManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        // TODO: Use the ViewModel
        String userId = SessionManager.getInstance(requireContext()).getUserId();
        String username = SessionManager.getInstance(requireContext()).getUsername();
        TextView userNameTextView = requireView().findViewById(R.id.searchHeading);
        userNameTextView.setText("Hi! "+username);
        CosineSimilarity calculateLocation = new CosineSimilarity();
        String userDirectory = System.getProperty("user.dir");
        System.out.println(userDirectory);
        String data = new String(Files.readAllBytes(Paths.get("C:/json.txt")));
        JsonElement jsonElement = JsonParser.parseString(data);
        JsonObject json = jsonElement.getAsJsonObject();
    }

}

