package com.example.cyfi.picture_tab;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.io.IOException;

public class ApImageViewModel extends AndroidViewModel {
    private File imageFile;
    private MutableLiveData<Bitmap> image = new MutableLiveData<>();

    public ApImageViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Bitmap> getImage() {
        return image;
    }

    public void setImageFile(File imageUrl) {
        this.imageFile = imageUrl;
    }

    public void getBitMapFromImageFile() throws IOException {
        image.setValue(MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), Uri.parse(imageFile.getAbsolutePath())));
    }

    public void resetViewModel() {
        imageFile = null;
        image.setValue(null);
    }
}
