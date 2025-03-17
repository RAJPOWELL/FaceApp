package com.example.tfapp;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.util.Map;

public class FaceComparator {
    private static final float THRESHOLD = 0.6f; // Adjust for accuracy

    public static String recognizeFace(Context context, float[] newEmbedding) {
        Map<String, float[]> storedEmbeddings;

        try {
            // Load stored embeddings
            EmbeddingStorage embeddingStorage = new EmbeddingStorage(context, new FaceNetModel(context.getAssets(), "facenet.tflite"));
            storedEmbeddings = embeddingStorage.getEmbeddings();
        } catch (IOException e) {
            Log.e("FaceComparator", "❌ Error loading embeddings: " + e.getMessage());
            return "Error: Embeddings Not Loaded";
        }

        String bestMatch = "UNKNOWN";
        float bestDistance = Float.MAX_VALUE;

        for (Map.Entry<String, float[]> entry : storedEmbeddings.entrySet()) {
            float distance = euclideanDistance(newEmbedding, entry.getValue());
            Log.d("FaceComparator", "Distance to " + entry.getKey() + ": " + distance);

            if (distance < bestDistance && distance < THRESHOLD) {
                bestDistance = distance;
                bestMatch = entry.getKey();
            }
        }

        return bestMatch;
    }

    private static float euclideanDistance(float[] embedding1, float[] embedding2) {
        float sum = 0;
        for (int i = 0; i < embedding1.length; i++) {
            float diff = embedding1[i] - embedding2[i];
            sum += diff * diff;
        }
        return (float) Math.sqrt(sum);
    }
}
