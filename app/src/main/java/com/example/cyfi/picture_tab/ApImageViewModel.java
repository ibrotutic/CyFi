package com.example.cyfi.picture_tab;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.File;

public class ApImageViewModel extends AndroidViewModel {
    private MutableLiveData<File> imageFile = new MutableLiveData<>();

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
}
