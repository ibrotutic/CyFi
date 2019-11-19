package com.example.cyfi.picture_tab;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.cyfi.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;


public class ImageAnalysisBottomSheet extends Fragment {
    private ApImageViewModel apImageViewModel;
    private BottomSheetBehavior sheetBehavior;
    private TextView objectPixelWidth;
    private TextView objectPixelHeight;
    private EditText objectWidth;
    private EditText objectLength;
    private Button computeDistance;
    private TextView distanceText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apImageViewModel = ViewModelProviders.of(getParentFragment()).get(ApImageViewModel.class);
        apImageViewModel.getObjectPixelHeight().observe(this, this::objectPixelHeight);
        apImageViewModel.getObjectPixelWidth().observe(this, this::objectPixelWidth);
        apImageViewModel.getDistanceToObject().observe(this, this::distanceCalculated);
    }

    private void distanceCalculated(Double distance) {
        double distanceInCm = distance/10;
        this.distanceText.setText(distanceInCm + " cm");
    }

    private void objectPixelWidth(Float width) {
        this.objectPixelWidth.setText(String.format("%.2f", width));
    }

    private void objectPixelHeight(Float height) {
        this.objectPixelHeight.setText(String.format("%.2f", height));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_analysis, container, false);
        objectPixelHeight = view.findViewById(R.id.object_pixel_length_value);
        objectPixelWidth = view.findViewById(R.id.object_pixel_width_value);
        objectLength = view.findViewById(R.id.object_length_edit_text);
        objectWidth = view.findViewById(R.id.object_width_edit_text);
        distanceText = view.findViewById(R.id.distance_text);
        computeDistance = view.findViewById(R.id.get_distance);
        computeDistance.setOnClickListener(v -> apImageViewModel.computeDistance());
        ImageButton reload = view.findViewById(R.id.try_again);
        reload.setOnClickListener(v -> {
            apImageViewModel.resetViewModel();
        });
        sheetBehavior = BottomSheetBehavior.from(view);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        apImageViewModel.setDrawMode(false);
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        view.findViewById(R.id.box_around_ap).setOnClickListener(v -> {
            Toast toast= Toast.makeText(getActivity(),
                    "Touch down and drag across the router to make a box around it. Drag the bottom sheet up when you are done", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
            apImageViewModel.setDrawMode(true);
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

        objectWidth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    String text = s.toString();
                    apImageViewModel.setObjectWidth(Float.parseFloat(text));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        objectLength.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    String text = s.toString();
                    apImageViewModel.setObjectHeight(Float.parseFloat(text));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }
}
