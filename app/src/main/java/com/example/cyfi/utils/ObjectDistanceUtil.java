package com.example.cyfi.utils;

import android.content.res.Resources;

public class ObjectDistanceUtil {
    private static float focalLength = 27; //in mm
    private static double sensorHeight = 4.1; //in mm

    public static double objectDistanceInMillimeters(float realObjectHeight, float imageHeight, float objectHeight) {
        return (focalLength * realObjectHeight * imageHeight) / (objectHeight * sensorHeight * 7.81);
    }

    public static float getPixelsForDp(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }
}
