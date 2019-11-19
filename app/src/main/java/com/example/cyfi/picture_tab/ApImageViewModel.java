package com.example.cyfi.picture_tab;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cyfi.utils.ObjectDistanceUtil;

import java.io.File;

public class ApImageViewModel extends AndroidViewModel {
    private float scalingFactor = 0;

    private MutableLiveData<File> imageFile = new MutableLiveData<>();
    private MutableLiveData<Float> objectPixelWidth = new MutableLiveData<>(0f);
    private MutableLiveData<Float> objectPixelHeight = new MutableLiveData<>(0f);
    private MutableLiveData<Boolean> drawMode = new MutableLiveData<>(false);
    private MutableLiveData<Double> distanceToObject = new MutableLiveData<>();
    private float objectWidth = 0;
    private float objectHeight = 0;

    public ApImageViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<File> getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageUrl, boolean isError) {
        if (isError) {
            resetViewModel();
        } else {
            this.imageFile.setValue(imageUrl);
        }
    }

    public void resetViewModel() {
        imageFile.setValue(null);
        objectPixelWidth.setValue(0f);
        objectPixelHeight.setValue(0f);
        objectWidth = 0f;
        objectHeight = 0f;
    }

    public void setCoordinates(float top, float bottom, float left, float right) {
        float objectHeight = ObjectDistanceUtil.getPixelsForDp(Math.max(bottom, top) - Math.min(bottom,top));
        this.objectPixelHeight.setValue(objectHeight);
        this.objectPixelWidth.setValue(ObjectDistanceUtil.getPixelsForDp(Math.max(left, right) - Math.min(left,right)));
    }

    public MutableLiveData<Float> getObjectPixelWidth() {
        return objectPixelWidth;
    }

    public MutableLiveData<Float> getObjectPixelHeight() {
        return objectPixelHeight;
    }

    public MutableLiveData<Double> getDistanceToObject() {
        return distanceToObject;
    }

    public float getScalingFactor() {
        return scalingFactor;
    }

    public void setScalingFactor(float scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    public void setObjectWidth(float objectWidth) {
        this.objectWidth = objectWidth;
    }

    public void setObjectHeight(float objectHeight) {
        this.objectHeight = objectHeight;
    }

    public void setDrawMode(boolean enabled) {
        this.drawMode.setValue(enabled);
    }

    public void computeDistance() {
        double distance = ObjectDistanceUtil.objectDistanceInMillimeters(this.objectHeight, 4032, scalingFactor * objectPixelHeight.getValue());
        distanceToObject.setValue(distance);
    }

    public MutableLiveData<Boolean> getDrawMode() {
        return drawMode;
    }
}
