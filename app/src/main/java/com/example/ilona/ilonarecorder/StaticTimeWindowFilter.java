package com.example.ilona.ilonarecorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Created by ilona on 2016.08.09..
 */
public class StaticTimeWindowFilter implements FilterInterface {
    private double threshold;
    private int memsize;


    public StaticTimeWindowFilter(int memsize, double threshold) {
        this.threshold = threshold;
        this.memsize = memsize;
    }

    //TODO TESZTELNI
    @Override
    public Map<String, Double> filteringmethod(LinkedList<Map<String, Double>> linkedList) {
        if (linkedList.size() < memsize) {
            return linkedList.getFirst();
        }
        double filteredValue;
        Map<String, Double> result = new HashMap<String, Double>();
        ArrayList<Double> rssiValues = null;
        for (String ssid : getKeys(linkedList)) {
            rssiValues = getWiFiRSSIVector(ssid, linkedList);
            if (rssiValues.size() > 0) {
                filteredValue = rssiValues.get(0);
                if (rssiValues.size() > 1) {
                    double difference = rssiValues.get(0) - rssiValues.get(1);
                    if ((difference > threshold)) {
                        filteredValue = filter(rssiValues);
                    }
                }
                result.put(ssid, filteredValue);
            }

        }
        return result;
    }

    private Set<String> getKeys(LinkedList<Map<String, Double>> linkedList) {
        Set<String> result = new HashSet<String>();
        for (int i = 0; i < linkedList.size(); i++) {
            result.addAll(linkedList.get(i).keySet());
        }
        return result;
    }

    private ArrayList<Double> getWiFiRSSIVector(String ssid, LinkedList<Map<String, Double>> linkedList) {
        ArrayList<Double> result = new ArrayList<>();
        for (int i = 0; i < memsize; i++) {
            if (linkedList.get(i).get(ssid) != null) {
                result.add(linkedList.get(i).get(ssid));
            }
        }
        return result;
    }

    private double filter(ArrayList<Double> m) {
        double sum = 0;
        for (int i = 0; i < m.size(); i++) {
            sum += m.get(i);
        }
        return sum / m.size();
    }
}
