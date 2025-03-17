package com.example.tfapp;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FaceNetModel {
    private Interpreter interpreter;

    public FaceNetModel(AssetManager assetManager, String modelPath) throws IOException {
        this.interpreter = new Interpreter(loadModelFile(assetManager, modelPath));
    }

    private MappedByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.getStartOffset(), fileDescriptor.getDeclaredLength());
    }

    public float[] getFaceEmbedding(float[][][][] faceInput) {
        // Validate input dimensions
        if (faceInput.length != 1 || faceInput[0].length != 112 ||
                faceInput[0][0].length != 112 || faceInput[0][0][0].length != 3) {
            throw new IllegalArgumentException(
                    "Input dimensions must be [1, 112, 112, 3], got [" +
                            faceInput.length + ", " + faceInput[0].length + ", " +
                            faceInput[0][0].length + ", " + faceInput[0][0][0].length + "]");
        }

        float[][] outputEmbedding = new float[1][192];
        interpreter.run(faceInput, outputEmbedding);
        return outputEmbedding[0];
    }
}


