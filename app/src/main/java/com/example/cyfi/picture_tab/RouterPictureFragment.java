package com.example.cyfi.picture_tab;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.cyfi.R;
import com.example.cyfi.utils.ExifUtil;
import com.example.cyfi.utils.ObjectDistanceUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


/**
 * A fragment that controls the image processing of the AP Info tab..
 */
public class RouterPictureFragment extends Fragment {
    private boolean drawingEnabled = false;
    private FloatingActionButton startCameraButton;
    private DrawImageView apImageView;
    private TextView tabDescription;
    private ApImageViewModel apImageViewModel;
    private File imageLocation;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private View coordinator;
    private Fragment imageAnalysisBottomSheet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apImageViewModel = ViewModelProviders.of(this).get(ApImageViewModel.class);
        apImageViewModel.getImageFile().observe(this, this::fetchedNewImage);
        apImageViewModel.getDrawMode().observe(this, drawMode -> drawingEnabled = drawMode);
        apImageViewModel.getDistanceToObject().observe(this, this::getAPatDistance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ap_picture, container, false);
        apImageView = view.findViewById(R.id.image_preview);

        //Begin drawing event.
        apImageView.setOnTouchListener((v, event) -> {
            DrawImageView drawView = (DrawImageView) v;

            if (drawingEnabled) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    drawView.left = event.getX();
                    drawView.top = event.getY();
                } else {
                    drawView.right = event.getX();
                    drawView.bottom = event.getY();
                }
                // draw
                drawView.invalidate();
                apImageViewModel.setCoordinates(drawView.top, drawView.bottom, drawView.left, drawView.right);
                drawView.drawRect = true;
            }

            return true;
        });

        coordinator = view.findViewById(R.id.coordinator);
        tabDescription = view.findViewById(R.id.image_instructions);
        startCameraButton = view.findViewById(R.id.take_picture_button);
        startCameraButton.setOnClickListener(v -> openCamera());
        return view;
    }

    /**
     * Fetch image file and scale to view size.
     * @param file
     *  A file to get the image from.
     */
    private void fetchedNewImage(File file) {
        if (file != null) {
            startCameraButton.hide();
            tabDescription.setVisibility(View.GONE);
            // Get the dimensions of the View
            int targetW = apImageView.getWidth();
            int targetH = apImageView.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
            apImageViewModel.setScalingFactor(4032/(ObjectDistanceUtil.getPixelsForDp(targetH)));

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;

            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            apImageView.setImageBitmap(ExifUtil.rotateBitmap(file.getAbsolutePath(), bitmap));
            apImageView.setVisibility(View.VISIBLE);
            showBottomSheetDialogFragment();
        } else {
            resetState();
        }
    }

    /**
     * Creates and shows the bottom sheet.
     */
    public void showBottomSheetDialogFragment() {
        imageAnalysisBottomSheet = new ImageAnalysisBottomSheet();
        getChildFragmentManager().beginTransaction().add(R.id.coordinator, imageAnalysisBottomSheet).commit();
        coordinator.setVisibility(View.VISIBLE);
    }

    /**
     * Reset state of fragment.
     */
    private void resetState() {
        getChildFragmentManager().beginTransaction().remove(imageAnalysisBottomSheet).commit();
        startCameraButton.show();
        tabDescription.setVisibility(View.VISIBLE);
        apImageView.setVisibility(View.GONE);
        coordinator.setVisibility(View.GONE);
        drawingEnabled = false;
    }

    /**
     * Opens camera to get picture.
     */
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getActivity(), "Error making image file. Try again. Ensure permissions are given.", Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.cyfi.fileprovider",
                        photoFile);;
                this.imageLocation = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            apImageViewModel.setImageFile(imageLocation, false);
        } else {
            Toast.makeText(getActivity(), "Error processing image file. Try again.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Creates an image file.
     * @return
     *  A file reprenting the image.
     * @throws IOException
     *  If permissions aren't given.
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStorageDirectory();
        return File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );
    }

    /**
     * Creates dialog for the AP after the distance was calculated.
     * @param distance
     */
    private void getAPatDistance(Double distance) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DialogFragment dialogFragment = APInformationFragment.newInstance(distance);
        dialogFragment.show(ft, APInformationFragment.TAG);
    }

}
