package com.example.tfapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EmbeddingStorage {
    private static final String[] KNOWN_PERSONS = {"person1.jpg", "person2.jpg", "person3.jpg", "person4.jpg", "person5.jpg"};
    private Map<String, float[]> faceEmbeddings = new HashMap<>();

    public EmbeddingStorage(Context context, FaceNetModel faceNetModel) {
        try {
            for (String fileName : KNOWN_PERSONS) {
                // FIX: Pass 'context' instead of 'assetManager'
                Bitmap bitmap = ImageLoader.loadBitmapFromAssets(context, fileName);
                if (bitmap != null) {
                    float[] embedding = faceNetModel.getFaceEmbedding(ImageToTensor.bitmapToTensor(bitmap));
                    faceEmbeddings.put(fileName, embedding);
                    Log.d("EmbeddingStorage", "✅ Loaded embedding for: " + fileName);
                } else {
                    Log.e("EmbeddingStorage", "❌ Failed to load image: " + fileName);
                }
            }
        } catch (IOException e) {
            Log.e("EmbeddingStorage", "❌ Error loading images from assets", e);
        }
    }

    public Map<String, float[]> getEmbeddings() {
        return faceEmbeddings;
    }
}
