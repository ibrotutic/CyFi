package com.example.cyfi.current_wifi_tab;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.cyfi.current_wifi_tab.wifi_info.BSSIDItem;
import com.example.cyfi.current_wifi_tab.wifi_info.FrequencyItem;
import com.example.cyfi.current_wifi_tab.wifi_info.IPAddressItem;
import com.example.cyfi.current_wifi_tab.wifi_info.LinkSpeedItem;
import com.example.cyfi.current_wifi_tab.wifi_info.MacAddressItem;
import com.example.cyfi.current_wifi_tab.wifi_info.SSIDItem;
import com.example.cyfi.current_wifi_tab.wifi_info.SignalStrengthItem;
import com.example.cyfi.current_wifi_tab.wifi_info.SupplicantStateItem;
import com.example.cyfi.current_wifi_tab.wifi_info.WifiInfoItem;

import java.util.ArrayList;
import java.util.List;


public class NetworkInfoViewModel extends AndroidViewModel {
    private WifiManager wifiManager;
    private MutableLiveData<List<WifiInfoItem>> wifiInfoData = new MutableLiveData<>();

    public NetworkInfoViewModel(Application application) {
        super(application);
        wifiManager = (WifiManager) application.getSystemService(Context.WIFI_SERVICE);
        initializeCurrentWifiInformation();
    }

    private void initializeCurrentWifiInformation() {
        List<WifiInfoItem> wifiInfoItems = new ArrayList<>();
        wifiInfoItems.add(new SupplicantStateItem(""));
        wifiInfoItems.add(new IPAddressItem(""));
        wifiInfoItems.add(new MacAddressItem(""));
        wifiInfoItems.add(new BSSIDItem(""));
        wifiInfoItems.add(new SSIDItem(""));
        wifiInfoItems.add(new FrequencyItem(""));
        wifiInfoItems.add(new SignalStrengthItem(""));
        wifiInfoItems.add(new LinkSpeedItem(""));
        wifiInfoData.setValue(wifiInfoItems);
    }

    public MutableLiveData<List<WifiInfoItem>> getWifiInfo() {
        return wifiInfoData;
    }

    public void updateWifiInfo() {
        List<WifiInfoItem> wifiInfoItems = wifiInfoData.getValue();
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        for(WifiInfoItem wifiInfoItem : wifiInfoItems) {
            wifiInfoItem.refresh(wifiInfo);
        }
        wifiInfoData.postValue(wifiInfoItems);
    }


}
