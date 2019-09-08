package com.example.cyfi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.MenuItem;

import com.example.cyfi.current_wifi.CurrentWifiFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    final Fragment currentConnectionFragment = new CurrentWifiFragment();
    final Fragment routerFragment = new ScanNetworksFragment();
    final Fragment scanNetworksFragment = new RouterPictureFragment();
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

                case R.id.scan_networks:
                    fm.beginTransaction().hide(displayedFragment).show(scanNetworksFragment).commit();
                    displayedFragment = scanNetworksFragment;
                    toolbar.setTitle(R.string.recommend_a_network);
                    return true;

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
