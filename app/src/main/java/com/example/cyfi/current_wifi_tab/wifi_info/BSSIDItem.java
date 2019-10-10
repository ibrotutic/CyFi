package com.example.cyfi.current_wifi_tab.wifi_info;

import android.net.wifi.WifiInfo;

import com.example.cyfi.constants.NetworkInfoConstants;

public class BSSIDItem extends WifiInfoItem {

    public BSSIDItem(String properyValue) {
        super(NetworkInfoConstants.BSSID, properyValue);
    }

    @Override
    public String getWifiItemValue(WifiInfo wifiInfo) {
        return wifiInfo.getBSSID();
    }
}
