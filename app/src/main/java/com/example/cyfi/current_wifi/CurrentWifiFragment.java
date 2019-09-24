package com.example.cyfi.current_wifi;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cyfi.R;
import com.example.cyfi.current_wifi.wifi_info.WifiInfoItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class CurrentWifiFragment extends Fragment {

    private RecyclerView wifiInfo;
    private ProgressBar progressBar;
    private FloatingActionButton getCurrentWifiInfoButton;
    private WifiInfoAdapter wifiInfoAdapter;
    private NetworkInfoViewModel networkInfoViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_wifi_fragment, container, false);
        wifiInfo = view.findViewById(R.id.wifi_recycler_view);
        getCurrentWifiInfoButton = view.findViewById(R.id.check_current_settings_button);
        progressBar = view.findViewById(R.id.pBar);
        getCurrentWifiInfoButton.setOnClickListener( (View v) -> {
            wifiInfo.setVisibility(View.GONE);
            networkInfoViewModel.updateWifiInfo();
            progressBar.setVisibility(View.VISIBLE);
            getCurrentWifiInfoButton.setEnabled(false);
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkInfoViewModel = ViewModelProviders.of(this).get(NetworkInfoViewModel.class);
        networkInfoViewModel.getWifiInfo().observe(this, this::updateWifiInfoView);
        networkInfoViewModel.updateWifiInfo();
    }

    private void updateWifiInfoView(List<WifiInfoItem> wifiInfoItems) {
        if (wifiInfoItems == null) {
            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
            }
            getCurrentWifiInfoButton.setEnabled(true);
        }
        if (this.wifiInfo.getVisibility() == View.GONE) {
            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
            }
            this.wifiInfo.setVisibility(View.VISIBLE);
        }
        if (wifiInfoAdapter == null) {
            wifiInfoAdapter = new WifiInfoAdapter(null);
            this.wifiInfo.setAdapter(wifiInfoAdapter);
            this.wifiInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            wifiInfoAdapter.setData(wifiInfoItems);
        }
    }
}
