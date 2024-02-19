package com.exploreway.utils;

import org.json.JSONArray;
import org.json.JSONException;

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
}
