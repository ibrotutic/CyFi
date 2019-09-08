package com.example.cyfi.current_wifi;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cyfi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class CurrentWifiFragment extends Fragment {

    RecyclerView wifiInfo;
    ProgressBar progressBar;
    FloatingActionButton getCurrentWifiInfoButton;
    WifiInfoAdapter wifiInfoAdapter;
    ArrayList<WifiInfo> wifiInfoList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_wifi_fragment, container, false);
        wifiInfo = view.findViewById(R.id.wifi_recycler_view);
        getCurrentWifiInfoButton = view.findViewById(R.id.check_current_settings_button);
        progressBar = view.findViewById(R.id.pBar);
        getCurrentWifiInfoButton.setOnClickListener( (View v) -> {
            wifiInfo.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            getCurrentWifiInfoButton.setEnabled(false);
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                progressBar.setVisibility(View.GONE);
                getCurrentWifiInfo();
                wifiInfo.setVisibility(View.VISIBLE);
                getCurrentWifiInfoButton.setEnabled(true);
            }, 500);
        });
        getCurrentWifiInfo();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getCurrentWifiInfo() {
        //TODO: get current wifi state and information.
        WifiInfo wifiInfo = new WifiInfo("IP", "2341.23.12.3");
        wifiInfoList.add(wifiInfo);
        if (this.wifiInfo.getVisibility() == View.GONE) {
            this.wifiInfo.setVisibility(View.VISIBLE);
        }
        if (wifiInfoAdapter == null) {
            wifiInfoAdapter = new WifiInfoAdapter(wifiInfoList);
            this.wifiInfo.setAdapter(wifiInfoAdapter);
            this.wifiInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            wifiInfoAdapter.notifyItemInserted(this.wifiInfoList.indexOf(wifiInfo));
        }
    }
}
