package com.example.cyfi.current_wifi_tab.wifi_info;

import android.net.wifi.WifiInfo;

import com.example.cyfi.constants.NetworkInfoConstants;

public class SupplicantStateItem extends WifiInfoItem {

    public SupplicantStateItem(String propertyValue) {
        super(NetworkInfoConstants.SUPPLICANT_STATE, propertyValue);
    }

    @Override
    public String getWifiItemValue(WifiInfo wifiInfo) {
        return wifiInfo.getSupplicantState().name();
    }
}
