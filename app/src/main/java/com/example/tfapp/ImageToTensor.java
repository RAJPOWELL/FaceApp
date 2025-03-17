package com.example.tfapp;

import android.graphics.Bitmap;

public class ImageToTensor {
    public static float[][][][] bitmapToTensor(Bitmap bitmap) {
        int width = 112; // Change to 112
        int height = 112; // Change to 112
        float[][][][] inputTensor = new float[1][width][height][3];

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true); // Resize to 112x112

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = resizedBitmap.getPixel(x, y);
                inputTensor[0][x][y][0] = ((pixel >> 16) & 0xFF) / 255.0f;
                inputTensor[0][x][y][1] = ((pixel >> 8) & 0xFF) / 255.0f;
                inputTensor[0][x][y][2] = (pixel & 0xFF) / 255.0f;
            }
        }
        return inputTensor;
    }
}
