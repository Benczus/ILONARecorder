package com.example.ilona.ilonarecorder.services;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;

import com.example.ilona.ilonarecorder.Constants;
import com.example.ilona.ilonarecorder.filter.StaticTimeWindowFilter;
import com.example.ilona.ilonarecorder.filter.WiFiRSSIFilteringStrategy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;


public class RSSIService extends IntentService {

    // switch for filtering
    final private static boolean FILTER_SWITCH = false;

	final private static int QUEUE_WINDOW_SIZE = 5;
    final private static int MEMSIZE = 5;
    final private static int THRESHOLD = 5;
    final private static int MAX_MEMORY_SIZE = 20;
    private final LinkedList<Map<String, Double>> previousValues;
    private final WiFiRSSIFilteringStrategy filter;


    public RSSIService() {
        super("MyIntentService");
        previousValues = new LinkedList<>();
        filter = new StaticTimeWindowFilter(MEMSIZE, THRESHOLD);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Initiates the WiFi service.
        Map<String, Double> filteredWifiRSSI;
		Queue<HashMap<String, Double>> rssiQueue = new ArrayBlockingQueue<>(QUEUE_WINDOW_SIZE);
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		int interval = 1000;


        try {
            while (true) {
                //Scans for devices and saves them into the results list.
                wifi.startScan();

                List<ScanResult> results = wifi.getScanResults();

                HashMap<String, Double> currentWifiRSSI = new HashMap<>();
                for (ScanResult scanResult : results) {
					currentWifiRSSI.put(scanResult.SSID, (double) scanResult.level);

                }
                //Filtering module
                if (FILTER_SWITCH) {
                    previousValues.push(currentWifiRSSI);
                    filteredWifiRSSI = filter.filteringMethod(previousValues);
                    if (previousValues.size() > MAX_MEMORY_SIZE) {
                        previousValues.pop();
                    }
                } else {
                    filteredWifiRSSI = currentWifiRSSI;
                }
				rssiQueue.poll();
				rssiQueue.add((HashMap<String, Double>) filteredWifiRSSI);

				//

				List<Map<String,Double>> rssiArray = new ArrayList<>();
				Object[] queueValues = rssiQueue.toArray();
				for(int i = 0; i < rssiQueue.size(); i++){
					rssiArray.add((Map<String,Double>)queueValues[i]);
				}
				Map<String,Double> result = new HashMap<>();
				for (int i=0; i<rssiArray.size(); i++) {
					result.putAll(rssiArray.get(i));
				}

					//
				Intent localIntent = new Intent(Constants.BROADCAST_ACTION);
                localIntent.putExtra(Constants.EXTENDED_DATA_STATUS, new HashMap<>(result));
                LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
                // Broadcasting

                SystemClock.sleep(interval);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}