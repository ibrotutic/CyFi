package com.example.cyfi.current_wifi;

public class WifiInfo {
    private String propertyName;
    private String propertyDescription;

    public WifiInfo(String propertyName, String properyValue) {
        this.propertyName = propertyName;
        this.propertyDescription = properyValue;
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
}
