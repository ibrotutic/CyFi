package com.example.cyfi.current_wifi.wifi_info;

import android.net.wifi.WifiInfo;

import com.example.cyfi.constants.NetworkInfoConstants;

public class SupplicantStateItem extends WifiInfoItem {

    public SupplicantStateItem(String propertyValue) {
        super(NetworkInfoConstants.SUPPLICANT_STATE, propertyValue);
    }

    @Override
    String getWifiItemValue(WifiInfo wifiInfo) {
        return wifiInfo.getSupplicantState().name();
    }
}
