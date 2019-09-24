package com.example.cyfi.current_wifi.wifi_info;

import android.net.wifi.WifiInfo;

import com.example.cyfi.constants.NetworkInfoConstants;

public class IPAddressItem extends WifiInfoItem {

    public IPAddressItem(String properyValue) {
        super(NetworkInfoConstants.IP_ADDRESS, properyValue);
    }

    @Override
    public String getWifiItemValue(WifiInfo wifiInfo) {
        int ip = wifiInfo.getIpAddress();
        return String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff),
                (ip >> 24 & 0xff));
    }
}
