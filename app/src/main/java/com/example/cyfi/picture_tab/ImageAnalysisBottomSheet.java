package com.example.cyfi.picture_tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.cyfi.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ImageAnalysisBottomSheet extends Fragment {
    private ApImageViewModel apImageViewModel;
    private BottomSheetBehavior sheetBehavior;
    private TextView top;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apImageViewModel = ViewModelProviders.of(getParentFragment()).get(ApImageViewModel.class);
        apImageViewModel.getBottom().observe(this, this::bottomEdge);
        apImageViewModel.getTop().observe(this, this::topEdge);
        apImageViewModel.getLeft().observe(this, this::leftEdge);
        apImageViewModel.getRight().observe(this, this::rightEdge);
    }

    private void rightEdge(Float right) {
    }

    private void leftEdge(Float left) {
    }

    private void topEdge(Float top) {
        this.top.setText(top.toString());
    }

    private void bottomEdge(Float bottom) {
        //do something with it
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_analysis, container, false);
        top = view.findViewById(R.id.top);
        sheetBehavior = BottomSheetBehavior.from(view);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

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
        return view;
    }
}
