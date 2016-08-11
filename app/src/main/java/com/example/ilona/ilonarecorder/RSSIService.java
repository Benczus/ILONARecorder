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

    final static int MEMSIZE = 5;
    public static int MAX_MEMORY_SIZE = 20;
    LinkedList<Map<String, Double>> previousValues;
    FilterInterface filter;

    public RSSIService() {
        super("MyIntentService");
        previousValues = new LinkedList<Map<String, Double>>();
        filter = new HorusFilter(MEMSIZE);

    }
    @Override
    protected void onHandleIntent(Intent workIntent) {


        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        while (true) {
            wifi.startScan();
            List<ScanResult> results = wifi.getScanResults();
            int size = results.size();
            size = size - 1;
            Map<String, Double> currentWifiRSSI = new HashMap<String, Double>();
            for (ScanResult scanResult : results) {
                currentWifiRSSI.put(scanResult.SSID, Double.valueOf(scanResult.level));
            }
            previousValues.push(currentWifiRSSI);
            Map<String, Double> filteredWifiRSSI = filter.filteringmethod(previousValues);
//            previousValues.remove(previousValues.getFirst());
//            previousValues.push(hashMap);
            if (previousValues.size() > MAX_MEMORY_SIZE) {
                previousValues.pop();
            }
            Intent localIntent = new Intent(Constants.BROADCAST_ACTION);
            localIntent.putExtra(Constants.EXTENDED_DATA_STATUS, new HashMap<String, Double>(filteredWifiRSSI));
            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
            // Broadcasting
            int interval = 12000;
            SystemClock.sleep(interval);
        }
    }
}