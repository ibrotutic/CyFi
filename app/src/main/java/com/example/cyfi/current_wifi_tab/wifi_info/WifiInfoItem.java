package com.example.cyfi.current_wifi_tab.wifi_info;

import android.net.wifi.WifiInfo;

public abstract class WifiInfoItem {
    private String propertyName;
    private String propertyDescription;

    public WifiInfoItem(String propertyName, String propertyValue) {
        this.propertyName = propertyName;
        this.propertyDescription = propertyValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyDescription() {
        return propertyDescription;
    }

    public void setPropertyDescription(String propertyDescription) {
        this.propertyDescription = propertyDescription;
    }

    public void refresh(WifiInfo wifiInfo) {
        if (wifiInfo != null) {
            String wifiValue = getWifiItemValue(wifiInfo);
            if (wifiValue != null) {
                setPropertyDescription(wifiValue);
            } else {
                setPropertyDescription("");
            }
        }
    }

    abstract String getWifiItemValue(WifiInfo wifiInfo);
}
