package com.example.cyfi.current_wifi.wifi_info;

import android.net.wifi.WifiInfo;

import com.example.cyfi.constants.NetworkInfoConstants;

public class MacAddressItem extends WifiInfoItem {

    public MacAddressItem(String properyValue) {
        super(NetworkInfoConstants.MAC_ADDRESS, properyValue);
    }

    @Override
    public String getWifiItemValue(WifiInfo wifiInfo) {
        return wifiInfo.getMacAddress();
    }
}
