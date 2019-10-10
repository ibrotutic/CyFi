package com.example.cyfi.current_wifi_tab.wifi_info;

import android.net.wifi.WifiInfo;

import com.example.cyfi.constants.NetworkInfoConstants;

public class FrequencyItem extends WifiInfoItem {

    public FrequencyItem(String properyValue) {
        super(NetworkInfoConstants.FREQUENCY, properyValue);
    }

    @Override
    public String getWifiItemValue(WifiInfo wifiInfo) {
        return String.format("%d %s", wifiInfo.getFrequency(), wifiInfo.FREQUENCY_UNITS);
    }
}
