package com.example.cyfi.current_wifi_tab.wifi_info;

import android.net.wifi.WifiInfo;

import com.example.cyfi.constants.NetworkInfoConstants;

public class LinkSpeedItem extends WifiInfoItem {
    public LinkSpeedItem(String propertyValue) {
        super(NetworkInfoConstants.LINK_SPEED, propertyValue);
    }

    @Override
    String getWifiItemValue(WifiInfo wifiInfo) {
        return String.format("%d %s", wifiInfo.getLinkSpeed(), wifiInfo.LINK_SPEED_UNITS);
    }
}
