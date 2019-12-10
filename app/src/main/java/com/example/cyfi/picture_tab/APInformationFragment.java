package com.example.cyfi.picture_tab;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cyfi.R;
import com.example.cyfi.current_wifi_tab.WifiInfoAdapter;
import com.example.cyfi.current_wifi_tab.wifi_info.WifiInfoItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class APInformationFragment extends DialogFragment {
    public static String TAG = APInformationFragment.class.getName();
    private Double distanceToAP = 0.0;

    private static String DISTANCE = "distance";
    private Button closeButton;
    private RecyclerView recyclerView;
    private TextView distanceText;
    private WifiInfoAdapter wifiInfoAdapter = new WifiInfoAdapter(new ArrayList<>());

    public static APInformationFragment newInstance(Double distance) {
        APInformationFragment dialog = new APInformationFragment();
        Bundle args = new Bundle();
        args.putDouble("distance", distance);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        distanceToAP = getArguments().getDouble(DISTANCE,0.0) / 10;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // you can use LayoutInflater.from(getContext()).inflate(...) if you have xml layout
        View view = getActivity().getLayoutInflater().inflate(R.layout.ap_information_dialog, null);
        recyclerView = view.findViewById(R.id.wifi_recycler_view_dialog);
        closeButton = view.findViewById(R.id.close_dialog);
        distanceText = view.findViewById(R.id.distance_dialog);

        distanceText.setText(String.format("Distance: %.2f cm", distanceToAP));
        closeButton.setOnClickListener(clicked -> getDialog().dismiss());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(wifiInfoAdapter);
        getWifiInfo();

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
    }

    private void getWifiInfo() {
        final WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> routers = wifiManager.getScanResults();
        List<ScanResult> sortedResults = routers.stream().sorted(byScanResult).collect(Collectors.toList());

        ScanResult closestAP = sortedResults.get(0);

        List<WifiInfoItem> wifiInfoItems = new ArrayList<>();
        wifiInfoItems.add(new APInfoItem("SSID", closestAP.SSID));
        wifiInfoItems.add(new APInfoItem("BSSID", closestAP.BSSID));
        wifiInfoItems.add(new APInfoItem("Frequency", closestAP.frequency + "Hz"));
        wifiInfoItems.add(new APInfoItem("Channel Width", getChannelWidth(closestAP)));
        wifiInfoItems.add(new APInfoItem("Capabilities", closestAP.capabilities));


        if (wifiInfoAdapter == null) {
            wifiInfoAdapter = new WifiInfoAdapter(wifiInfoItems);
            this.recyclerView.setAdapter(wifiInfoAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        } else {
            wifiInfoAdapter.setData(wifiInfoItems);
        }
    }

    private String getChannelWidth(ScanResult closestAP) {
        int width = closestAP.channelWidth;
        switch (width) {
            case 0:
                return "20Mhz";
            case 1:
                return "40Mhz";
            case 2:
                return "80Mhz";
            case 3:
                return "160Mhz";
            case 4:
                return "80 Mhz + 80 Mhz";
            default:
                return "Not Found";
        }
    }

    private double calculateDistance(double signalLevelInDb, double freqInMHz) {
        double exp = (27.55 - (20 * Math.log10(freqInMHz)) + Math.abs(signalLevelInDb)) / 20.0;
        return Math.pow(10.0, exp);
    }

    Comparator<ScanResult> byScanResult = (o1, o2) -> Double.compare(calculateDistance(o1.level, o1.frequency), calculateDistance(o2.level, o2.frequency));
}