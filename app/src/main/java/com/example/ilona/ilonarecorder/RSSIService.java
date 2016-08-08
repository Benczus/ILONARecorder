package com.example.ilona.ilonarecorder;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashMap;
import java.util.List;



public class RSSIService extends IntentService {


    public RSSIService() {
        super("MyIntentService");

    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        while (true) {
            //Initializes a WifiManager object.
            WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

            // Scans for nearby WiFi devices, then saves the results into a variable.
            wifi.startScan();
            List<ScanResult> results = wifi.getScanResults();
            int size = results.size();
            size = size - 1;
            HashMap<String, Double> item = null;
            HashMap<String, Double> hashMap = new HashMap<String, Double>();

            // Iterates through the detected WiFi devies and saves the SSID and the RSSI into an array.
            while (size > 0) {
                hashMap.put(results.get(size).SSID, Double.valueOf(results.get(size).level));
                size--;
            }

            //Sends the array to MainActivity with an intent.
            Intent localIntent = new Intent(Constants.BROADCAST_ACTION);
            localIntent.putExtra(Constants.EXTENDED_DATA_STATUS, hashMap);
            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
            // Broadcasting
            int interval = 12000;
            SystemClock.sleep(interval);
        }

    }
}