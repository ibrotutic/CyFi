package com.example.cyfi.picture_tab;

import android.net.wifi.WifiInfo;

import com.example.cyfi.current_wifi_tab.wifi_info.WifiInfoItem;

public class APInfoItem extends WifiInfoItem {

    public APInfoItem(String propertyName, String propertyValue) {
        super(propertyName, propertyValue);
    }


    @Override
    public String getWifiItemValue(WifiInfo wifiInfo) {
        return super.getPropertyDescription();
    }
}
