package com.example.cyfi.current_wifi_tab.wifi_info;

import android.net.wifi.WifiInfo;

import com.example.cyfi.constants.NetworkInfoConstants;

public class SSIDItem extends WifiInfoItem {

    public SSIDItem(String properyValue) {
        super(NetworkInfoConstants.SSID, properyValue);
    }

    @Override
    public String getWifiItemValue(WifiInfo wifiInfo) {
        return wifiInfo.getSSID();
    }
}
