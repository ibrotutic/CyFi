package com.example.cyfi.current_wifi_tab.wifi_info;

import android.net.wifi.WifiInfo;

import com.example.cyfi.constants.NetworkInfoConstants;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class MacAddressItem extends WifiInfoItem {

    public MacAddressItem(String properyValue) {
        super(NetworkInfoConstants.MAC_ADDRESS, properyValue);
    }

    @Override
    public String getWifiItemValue(WifiInfo wifiInfo) {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
}
