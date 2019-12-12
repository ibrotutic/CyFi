package com.example.cyfi.current_wifi_tab.wifi_info;

import android.net.wifi.WifiInfo;

/**
 * Abstract class that stores a property name and description.
 * Objects that extend this class can get updated by the manager.
 */
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

    /**
     * Sets the property description based on the wifi info implementation.
     * @param wifiInfo
     *  Wifi info to use for refresh.
     */
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

    /**
     * User needs to implement to get the wifi properties from the WifiInfo item.
     * @param wifiInfo
     *  The current wifi info.
     * @return
     *  A string for the value inside the wifi info object.
     */
    public abstract String getWifiItemValue(WifiInfo wifiInfo);
}
