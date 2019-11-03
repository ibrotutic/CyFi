package com.example.cyfi.picture_tab;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.File;

public class ApImageViewModel extends AndroidViewModel {
    private float scalingFactor = 0;

    private MutableLiveData<File> imageFile = new MutableLiveData<>();
    private MutableLiveData<Float> left = new MutableLiveData<>();
    private MutableLiveData<Float> top = new MutableLiveData<>();
    private MutableLiveData<Float> right = new MutableLiveData<>();
    private MutableLiveData<Float> bottom = new MutableLiveData<>();

    public ApImageViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<File> getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageUrl) {
        this.imageFile.setValue(imageUrl);
    }

    public void resetViewModel() {
        imageFile.setValue(null);
    }

    public MutableLiveData<Float> getLeft() {
        return left;
    }

    public MutableLiveData<Float> getTop() {
        return top;
    }

    public MutableLiveData<Float> getRight() {
        return right;
    }

    public MutableLiveData<Float> getBottom() {
        return bottom;
    }

    public void setCoordinates(float top, float bottom, float left, float right) {
        this.left.setValue(left);
        this.right.setValue(right);
        this.top.setValue(top);
        this.bottom.setValue(bottom);
    }

    public float getScalingFactor() {
        return scalingFactor;
    }

    public void setScalingFactor(float scalingFactor) {
        this.scalingFactor = scalingFactor;
    }
}
