package com.example.cyfi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.view.MenuItem;

import com.example.cyfi.current_wifi_tab.CurrentWifiFragment;
import com.example.cyfi.current_wifi_tab.NetworkInfoViewModel;
import com.example.cyfi.picture_tab.RouterPictureFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * The main activity. This handles all tool bar interactions and switching tabs.
 */
public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION = 1;
    private static final int WIFI_STATE_PERMISSION = 1;

    /**
     * Set up toolbars and fragments.
     */
    private Toolbar toolbar;
    final Fragment currentConnectionFragment = new CurrentWifiFragment();
    final Fragment routerFragment = new RouterPictureFragment();
    final Fragment scanNetworksFragment = new ScanNetworksFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment displayedFragment = currentConnectionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.current_connection_information);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.main_container, scanNetworksFragment, "scan-networks").hide(scanNetworksFragment).commit();
        fm.beginTransaction().add(R.id.main_container, routerFragment, "router-info").hide(routerFragment).commit();
        fm.beginTransaction().add(R.id.main_container, currentConnectionFragment, "current-connection").commit();
        checkAndRequestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == WIFI_STATE_PERMISSION) {
                NetworkInfoViewModel networkInfoViewModel = ViewModelProviders.of(currentConnectionFragment).get(NetworkInfoViewModel.class);
                networkInfoViewModel.updateWifiInfo();
            }
        }
    }

    /**
     * Check for permissions.
     */
    private void checkAndRequestPermissions() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_WIFI_STATE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.current_connection:
                    fm.beginTransaction().hide(displayedFragment).show(currentConnectionFragment).commit();
                    displayedFragment = currentConnectionFragment;
                    toolbar.setTitle(R.string.current_connection_information);
                    return true;

//                case R.id.scan_networks:
//                    fm.beginTransaction().hide(displayedFragment).show(scanNetworksFragment).commit();
//                    displayedFragment = scanNetworksFragment;
//                    toolbar.setTitle(R.string.recommend_a_network);
//                    return true;

                case R.id.router_info:
                    fm.beginTransaction().hide(displayedFragment).show(routerFragment).commit();
                    displayedFragment = routerFragment;
                    toolbar.setTitle(R.string.ap_information);
                    return true;
            }
            return false;
        }
    };


}
