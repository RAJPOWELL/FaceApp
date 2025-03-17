package com.example.tfapp;

import android.content.Context;
import android.graphics.Bitmap;

public class FaceRecognition {
    public static String recognizeFace(Context context, Bitmap faceBitmap) {
        try {
            FaceNetModel faceNetModel = new FaceNetModel(context.getAssets(), "facenet.tflite");

            // Convert image to tensor
            float[][][][] tensor = ImageToTensor.bitmapToTensor(faceBitmap);

            // Get face embedding
            float[] newEmbedding = faceNetModel.getFaceEmbedding(tensor);

            // Compare and return name
            return FaceComparator.recognizeFace(context, newEmbedding);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}

