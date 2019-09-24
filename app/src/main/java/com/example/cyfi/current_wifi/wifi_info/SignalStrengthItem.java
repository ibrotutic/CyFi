package com.example.cyfi.current_wifi.wifi_info;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.example.cyfi.constants.NetworkInfoConstants;

public class SignalStrengthItem extends WifiInfoItem {
    public SignalStrengthItem(String properyValue) {
        super(NetworkInfoConstants.SIGNAL_STRENGTH, properyValue);
    }

    @Override
    String getWifiItemValue(WifiInfo wifiInfo) {
        int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 10);
        return String.format("%d / 10", level);
    }
}
