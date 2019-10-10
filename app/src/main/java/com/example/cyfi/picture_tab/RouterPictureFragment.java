package com.example.cyfi.picture_tab;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.cyfi.R;
import com.example.cyfi.current_wifi_tab.NetworkInfoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class RouterPictureFragment extends Fragment {
    private FloatingActionButton startCameraButton;
    private ImageView apImageView;
    private TextView tabDescription;
    private ApImageViewModel apImageViewModel;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apImageViewModel = ViewModelProviders.of(this).get(ApImageViewModel.class);
        apImageViewModel.getImage().observe(this, this::fetchedNewImage);
    }

    private void fetchedNewImage(Bitmap bitmap) {
        if (bitmap != null) {
            apImageView.setImageBitmap(bitmap);
            startCameraButton.hide();
            tabDescription.setVisibility(View.GONE);
            apImageView.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getActivity(), "Error loading image file, please try again.",
                    Toast.LENGTH_LONG).show();
            apImageViewModel.resetViewModel();
        }
    }

    private void resetState() {
        startCameraButton.show();
        tabDescription.setVisibility(View.VISIBLE);
        apImageView.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ap_picture, container, false);
        apImageView = view.findViewById(R.id.image_preview);
        tabDescription = view.findViewById(R.id.image_instructions);
        startCameraButton = view.findViewById(R.id.take_picture_button);
        startCameraButton.setOnClickListener(v -> openCamera());
        return view;
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getActivity(), "Error creating image file, please try again.",
                        Toast.LENGTH_LONG).show();
            }
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                apImageViewModel.setImageFile(photoFile);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                apImageViewModel.getBitMapFromImageFile();
            } catch (IOException e) {
                Toast.makeText(getActivity(), "Error loading image file, please try again.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );
    }

}
