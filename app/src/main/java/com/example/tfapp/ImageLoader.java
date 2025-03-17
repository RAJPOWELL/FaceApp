package com.example.tfapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;

public class ImageLoader {
    public static Bitmap loadBitmapFromAssets(Context context, String filename) throws IOException {
        InputStream inputStream = context.getAssets().open(filename);
        return BitmapFactory.decodeStream(inputStream);
    }
}

