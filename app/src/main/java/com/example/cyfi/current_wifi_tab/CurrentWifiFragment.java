package com.example.cyfi.current_wifi_tab;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cyfi.R;
import com.example.cyfi.current_wifi_tab.wifi_info.WifiInfoItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Show information about the current wifi connection if it exists.
 */
public class CurrentWifiFragment extends Fragment {

    private RecyclerView wifiInfo;
    private ProgressBar progressBar;
    private FloatingActionButton getCurrentWifiInfoButton;
    private WifiInfoAdapter wifiInfoAdapter;
    private NetworkInfoViewModel networkInfoViewModel;
    private TextView descriptionText;
    private boolean isInitialized = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_wifi_fragment, container, false);
        wifiInfo = view.findViewById(R.id.wifi_recycler_view);
        getCurrentWifiInfoButton = view.findViewById(R.id.check_current_settings_button);
        progressBar = view.findViewById(R.id.pBar);
        descriptionText = view.findViewById(R.id.list_instructions);
        getCurrentWifiInfoButton.setOnClickListener( (View v) -> {
            wifiInfo.setVisibility(View.GONE);
            networkInfoViewModel.updateWifiInfo();
            progressBar.setVisibility(View.VISIBLE);
        });
        isInitialized = true;
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkInfoViewModel = ViewModelProviders.of(this).get(NetworkInfoViewModel.class);
        networkInfoViewModel.getWifiInfo().observe(this, this::updateWifiInfoView);
        networkInfoViewModel.updateWifiInfo();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isInitialized) {
            showList(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        wifiInfo.setVisibility(View.GONE);
    }

    /**
     * Refresh the wifi information.
     * @param wifiInfoItems
     *  New wifi info items to show.
     */
    private void updateWifiInfoView(List<WifiInfoItem> wifiInfoItems) {
        if (isWifiConnected()) {
            if (wifiInfoAdapter == null) {
                wifiInfoAdapter = new WifiInfoAdapter(new ArrayList<>());
                this.wifiInfo.setAdapter(wifiInfoAdapter);
                this.wifiInfo.setLayoutManager(new LinearLayoutManager(getContext()));
            } else {
                wifiInfoAdapter.setData(wifiInfoItems);
            }
            showList(false);
        } else {
            descriptionText.setVisibility(View.VISIBLE);
            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Shows the list of AP information if there is an active wifi connection.
     */
    private void showList(boolean shouldUpdate) {
        if (isWifiConnected()) {
            if (shouldUpdate) {
                networkInfoViewModel.updateWifiInfo();
            }
            wifiInfo.setVisibility(View.VISIBLE);
            descriptionText.setVisibility(View.GONE);
            getCurrentWifiInfoButton.show();
            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.GONE);
            }
        } else {
            descriptionText.setVisibility(View.VISIBLE);
            wifiInfo.setVisibility(View.GONE);
            Toast.makeText(getActivity().getApplicationContext(), "Connect to wifi network", Toast.LENGTH_SHORT);
        }
    }

    /**
     * Check if wifi is connected.
     * @return
     *  True if it is.
     */
    private boolean isWifiConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) Objects.requireNonNull(getContext()).getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMgr == null) {
            return false;
        }
        Network network = connMgr.getActiveNetwork();
        if (network == null) return false;
        NetworkCapabilities capabilities = connMgr.getNetworkCapabilities(network);
        return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
    }
}
