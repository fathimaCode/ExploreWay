package com.exploreway.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CosineSimilarity {

    public static double calculateCosineSimilarity(JSONArray vectorA, JSONArray vectorB) throws JSONException {
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;


        for (int i = 0; i < vectorA.length(); i++) {
            double valueA = vectorA.getDouble(i);
            double valueB = vectorB.getDouble(i);
            dotProduct += valueA * valueB;
            normA += Math.pow(valueA, 2);
            normB += Math.pow(valueB, 2);
        }


        if (normA == 0.0 || normB == 0.0) {
            return 0.0;
        }


        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    public  JsonArray loadJsonArrayFromFile(String filePath) throws Exception {
        // Parse the JSON file
        JsonElement jsonElement = JsonParser.parseReader(new FileReader(filePath));
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        // Get the "locations" object
        JsonObject locations = jsonObject.getAsJsonObject("locations");

        // Create a list to hold all JSON objects
        List<JsonObject> jsonObjectList = new ArrayList<>();

        // Iterate over each location
        for (Map.Entry<String, JsonElement> entry : locations.entrySet()) {
            JsonArray locationArray = entry.getValue().getAsJsonArray();
            // Add each element of the location array to the list
            for (JsonElement element : locationArray) {
                jsonObjectList.add(element.getAsJsonObject());
            }
        }

        // Convert list of JSON objects to JSON array
        JsonArray jsonArray = new JsonArray();
        for (JsonObject obj : jsonObjectList) {
            jsonArray.add(obj);
        }

        return jsonArray;
    }

}
