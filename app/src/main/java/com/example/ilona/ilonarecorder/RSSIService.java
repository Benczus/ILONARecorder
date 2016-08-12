package com.example.ilona.ilonarecorder;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class RSSIService extends IntentService {

    // switch for filtering
    final static boolean FILTER_SWITCH = false;


    final static int MEMSIZE = 5;
    final static int THRESHOLD = 5;
    public static int MAX_MEMORY_SIZE = 20;
    LinkedList<Map<String, Double>> previousValues;
    FilterInterface filter;
    Map<String, Double> filteredWifiRSSI;

    public RSSIService() {
        super("MyIntentService");
        previousValues = new LinkedList<>();
        filter = new StaticTimeWindowFilter(MEMSIZE, THRESHOLD);
    }
    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Initiates the WiFi service.
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        try {
            while (true) {
                //Scans for devices and saves them into the results list.
                wifi.startScan();

                List<ScanResult> results = wifi.getScanResults();

                Map<String, Double> currentWifiRSSI = new HashMap<>();
                for (ScanResult scanResult : results) {
                    currentWifiRSSI.put(scanResult.SSID, (double) scanResult.level);
                }
                //Filtering module
                if (FILTER_SWITCH) {
                    previousValues.push(currentWifiRSSI);
                    filteredWifiRSSI = filter.filteringmethod(previousValues);
                    if (previousValues.size() > MAX_MEMORY_SIZE) {
                        previousValues.pop();
                    }
                } else {
                    filteredWifiRSSI = currentWifiRSSI;
                }
                Intent localIntent = new Intent(Constants.BROADCAST_ACTION);
                localIntent.putExtra(Constants.EXTENDED_DATA_STATUS, new HashMap<>(filteredWifiRSSI));
                LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
                // Broadcasting
                int interval = 1000;
                SystemClock.sleep(interval);
            }
        } catch (Exception e) {
            e.printStackTrace();
            }
    }
}